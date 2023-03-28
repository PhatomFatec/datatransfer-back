package com.datatransfer.dt2.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

@RestController
public class FileUploadController {

	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadBasic(@RequestParam("file") MultipartFile file)
			throws IOException {
		// Load pre-authorized user credentials from the environment.
		// TODO(developer) - See https://developers.google.com/identity for
		// guides on implementing OAuth2 for your application.
		GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
				.createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
		HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

		// Build a new authorized API client service.
		Drive service = new Drive.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(), requestInitializer)
				.setApplicationName("Drive samples").build();
		
		List<String> list = new ArrayList<>();
		list.add("1LFzz6RB4d-ePzRmyzVUC8zebcrYHzDTF");
		File fileMetadata = new File();
		fileMetadata.setParents(list);
		fileMetadata.setName(file.getOriginalFilename());
		String filePathd = new java.io.File(".").getCanonicalPath() + file.getOriginalFilename();
		file.transferTo(new java.io.File(filePathd));
		
		java.io.File filePath = new java.io.File(filePathd);
		System.out.println(filePath.getCanonicalPath());
		FileContent mediaContent = new FileContent("multipart/form-data", filePath); 
		File files = service.files().create(fileMetadata, mediaContent).setFields("id").execute();
		System.out.println("File ID: " + files.getId());
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}
}