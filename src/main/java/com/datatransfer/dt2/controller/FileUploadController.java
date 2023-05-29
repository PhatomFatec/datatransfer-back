package com.datatransfer.dt2.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
import com.datatransfer.dt2.services.AWSS3Service;
import com.datatransfer.dt2.services.GoogleServices;
import com.datatransfer.dt2.services.HistoryService;
import com.datatransfer.dt2.services.ScheduleConfigService;
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
	private AWSS3Service aws;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private GoogleServices googleService;

	@Autowired
	private ScheduleConfigService scheduleConfigService;

	private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
	List<Folders> lost = new ArrayList<>();

	@PostMapping("/upload")
	public ResponseEntity<?> uploadBasic(@RequestParam("file") MultipartFile file)
			throws IOException, GeneralSecurityException {

		aws.uploadFileToS3AndGetUrl(file, file.getOriginalFilename());
		googleService.uploadFileToDrive(file, "1LFzz6RB4d-ePzRmyzVUC8zebcrYHzDTF");

		return ResponseEntity.status(HttpStatus.OK).body(googleService.uploadFileToDrive(file, "1LFzz6RB4d-ePzRmyzVUC8zebcrYHzDTF"));

	}

	@PostMapping("/upload/aws")
	public ResponseEntity<?> uploadBasicAws(@RequestParam("file") MultipartFile file)
			throws IOException, GeneralSecurityException {

		GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
				.createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
		HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

		Drive service2 = new Drive.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(), requestInitializer)
				.setApplicationName(APPLICATION_NAME).build();

		String folderId = "1FjskLDiE83qQLFIckmk4YffdyyvOJgWY";

		awsService.uploadFile(file);
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aws.excluirObjetoS3("datatransfer-dt-bucket", file.getOriginalFilename());
		Instant inicio = Instant.now();

		List<String> list = new ArrayList<>();
		list.add(folderId);
		File fileMetadata = new File();
		fileMetadata.setParents(list);
		fileMetadata.setName(file.getOriginalFilename());
		String filePathd = new java.io.File(".").getCanonicalPath() + file.getOriginalFilename();
		file.transferTo(new java.io.File(filePathd));

		java.io.File filePath = new java.io.File(filePathd);
		FileContent mediaContent = new FileContent("multipart/form-data", filePath);

		File files = service2.files().create(fileMetadata, mediaContent).setFields("id").execute();

		History history = new History();
		history.setNome_arquivo(file.getOriginalFilename());
		history.setFile_id(files.getId());
		history.setTamanho(file.getSize());
		history.setData_envio(LocalDate.now());

		Instant fim = Instant.now();
		Long duracao = Duration.between(inicio, fim).getSeconds();
		history.setTempo(duracao);
		historyService.save(history);

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

	public void excluirArquivoS3(MultipartFile arquivo, String nomeBucket) throws IOException {
		String nomeArquivo = arquivo.getOriginalFilename();
		aws.excluirObjetoS3(nomeBucket, nomeArquivo);
	}

	@PostMapping("/upload/google")
	public ResponseEntity<?> uploadFileGoogleDrive(@RequestParam("file") MultipartFile file)
			throws IOException, GeneralSecurityException {
		Instant inicio = Instant.now();
		File files = googleService.uploadFileToDrive(file, "1LFzz6RB4d-ePzRmyzVUC8zebcrYHzDTF");
		History history = new History();
		history.setNome_arquivo(file.getOriginalFilename());
		history.setFile_id(files.getId());
		history.setTamanho(file.getSize());
		history.setData_envio(LocalDate.now());
		Instant fim = Instant.now();
		Long duracao = Duration.between(inicio, fim).getSeconds();
		history.setTempo(duracao);
		historyService.save(history);
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body(files);

	}

	@GetMapping("/aws/{bucketName}")
	public void downloadAllFilesFromS3(@PathVariable String bucketName) throws GeneralSecurityException {
		aws.downloadAllFilesFromS3(bucketName);
	}

	@Scheduled(fixedDelayString = "#{scheduleConfigService.getFixedRateFromDatabase()}") // 1 hora = 3.600.000																					// milissegundos
	public void scheduleApiCall() throws GeneralSecurityException {
		downloadAllFilesFromS3("datatransfer-dt-bucket");
	}
}