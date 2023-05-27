package com.datatransfer.dt2.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.datatransfer.dt2.configs.CustomMultipartFile;

@Service
public class AWSS3Service {

	@Autowired
	private AmazonS3 amazonS3Client;
	
	@Autowired
	private GoogleServices googleService;

	public String uploadFileToS3AndGetUrl(MultipartFile file, String fileName) throws IOException {
		InputStream inputStream = file.getInputStream();
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		metadata.setContentType(file.getContentType());

		// Faz o upload do arquivo para o S3 usando o cliente do Amazon S3
		amazonS3Client.putObject("datatransfer-dt-bucket", fileName, inputStream, metadata);

		// Gera a URL do arquivo carregado com expiração de 1 hora
		Date expiration = new Date(System.currentTimeMillis() + 3600000); // 1 hora
		URL url = amazonS3Client.generatePresignedUrl("datatransfer-dt-bucket", fileName, expiration);

		// Retorna a URL como uma string
		return url.toString();
	}

	public void excluirObjetoS3(String nomeBucket, String nomeObjeto) {
		amazonS3Client.deleteObject(nomeBucket, nomeObjeto);
	}

	public void downloadAllFilesFromS3(String bucketName) throws GeneralSecurityException {
		ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request().withBucketName(bucketName);
		ListObjectsV2Result listObjectsResult;

		do {
			listObjectsResult = amazonS3Client.listObjectsV2(listObjectsRequest);
			for (S3ObjectSummary objectSummary : listObjectsResult.getObjectSummaries()) {
				String key = objectSummary.getKey();
				String destinationDirectory = "/dt/src/main/resources/temp_files";
				String destinationFilePath = destinationDirectory + key;

				java.io.File destinationFile = new java.io.File(destinationFilePath);

				try {
					amazonS3Client.getObject(new GetObjectRequest(bucketName, key), destinationFile);

					try (FileInputStream fileInputStream = new FileInputStream(destinationFile)) {
						MultipartFile fileN = new CustomMultipartFile(destinationFile);
						googleService.uploadFileToDrive(fileN);
						String[] name = fileN.getOriginalFilename().split("temp_files");
						excluirObjetoS3(bucketName, name[1]);
					}
					Files.deleteIfExists(destinationFile.toPath());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			listObjectsRequest.setContinuationToken(listObjectsResult.getNextContinuationToken());
		} while (listObjectsResult.isTruncated());
	}

}