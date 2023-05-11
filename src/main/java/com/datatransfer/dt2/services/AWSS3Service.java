package com.datatransfer.dt2.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;


@Service
public class AWSS3Service {

	@Autowired
	private AmazonS3 amazonS3Client;


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

}