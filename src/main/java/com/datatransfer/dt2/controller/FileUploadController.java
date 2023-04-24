package com.datatransfer.dt2.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.datatransfer.dt2.models.Folders;
import com.datatransfer.dt2.models.History;
import com.datatransfer.dt2.services.HistoryService;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

@CrossOrigin
@RestController
public class FileUploadController {

	@Autowired
	private UploadFileAwsController awsService;
	
	@Autowired
	private FileDownloadController fileCont;

	@Autowired
	private HistoryService historyService;

	List<Folders> lost = new ArrayList<>();

	@PostMapping("/upload")
	public ResponseEntity<?> uploadBasic(@RequestParam("file") MultipartFile file) throws IOException {

		// Load pre-authorized user credentials from the environment.
		// TODO(developer) - See https://developers.google.com/identity for
		// guides on implementing OAuth2 for your application.
		GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
				.createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
		HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

		// Build a new authorized API client service.
		Drive service = new Drive.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(), requestInitializer)
				.setApplicationName("Drive samples").build();

		//awsService.uploadFile(file);

		Instant inicio = Instant.now();

		List<String> list = new ArrayList<>();
		list.add("1LFzz6RB4d-ePzRmyzVUC8zebcrYHzDTF");
		File fileMetadata = new File();
		fileMetadata.setParents(list);
		fileMetadata.setName(file.getOriginalFilename());
		String filePathd = new java.io.File(".").getCanonicalPath() + file.getOriginalFilename();
		file.transferTo(new java.io.File(filePathd));

		java.io.File filePath = new java.io.File(filePathd);
		FileContent mediaContent = new FileContent("multipart/form-data", filePath);

		File files = service.files().create(fileMetadata, mediaContent).setFields("id").execute();
		fileCont.getFile("1_JYJNQ2bN8Ev_mcvy82r_wNxTQkPMgrD", files.getId());

		History history = new History();
		history.setNome_arquivo(file.getOriginalFilename());
		history.setFile_id(files.getId());
		history.setTamanho(file.getSize());
		history.setData_envio(LocalDate.now());

		Instant fim = Instant.now();
		Long duracao = Duration.between(inicio, fim).getSeconds();
		history.setTempo(duracao);
		historyService.save(history);
		// service.files().delete(files.getId()).execute();
		return ResponseEntity.status(HttpStatus.OK).body(files);

	}

	@GetMapping("/files/{id}/metadata")
	public ResponseEntity<File> getFileMetadata(@PathVariable("id") String fileId) throws IOException {
		GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
				.createScoped(Arrays.asList(DriveScopes.DRIVE_METADATA));
		HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
		Drive service = new Drive.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(), requestInitializer)
				.setApplicationName("Drive samples").build();

		File filemetadata = service.files().get(fileId).execute();

		return ResponseEntity.ok(filemetadata);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<String> handleIOException(IOException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Erro ao recuperar metadados do arquivo: " + e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Erro interno do servidor: " + e.getMessage());
	}
}