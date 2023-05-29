package com.datatransfer.dt2.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datatransfer.dt2.services.AWSS3Service;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

@CrossOrigin
@RestController
@RequestMapping(value = "/files")
public class FileDownloadController {

	@Autowired
	private AWSS3Service serviceAws;

	@GetMapping("/{folderId}/{fileId}")
	public ResponseEntity<?> getFile(@PathVariable String folderId, @PathVariable String fileId) throws IOException {
		GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
				.createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
		HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

		// Build a new authorized API client service.
		Drive service = new Drive.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(), requestInitializer)
				.setApplicationName("Drive samples").build();

		Permission newOwner = new Permission().setType("user").setRole("owner")
				.setEmailAddress("googledrive@infra-ratio-365813.iam.gserviceaccount.com");

		service.permissions().create("1LFzz6RB4d-ePzRmyzVUC8zebcrYHzDTF", newOwner).setTransferOwnership(true)
				.execute();

		File file = service.files().get(fileId).execute();
		InputStream content = service.files().get(fileId).executeMediaAsInputStream();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = content.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		byte[] fileBytes = outputStream.toByteArray();
		ByteArrayResource resource = new ByteArrayResource(fileBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");

		List<String> list = new ArrayList<>();
		list.add(folderId);
		File fileMetadata = new File();
		fileMetadata.setParents(list);
		fileMetadata.setName(file.getName());
		String filePathd = new java.io.File(".").getCanonicalPath() + file.getName();

		java.io.File filePath = new java.io.File(filePathd);
		FileContent mediaContent = new FileContent("multipart/form-data", filePath);
		service.files().create(fileMetadata, mediaContent).setFields("id").execute();
		return ResponseEntity.ok()
				// .headers(headers)
				// .contentType(MediaType.parseMediaType(file.getMimeType()))
				.body(resource);
	}

}
