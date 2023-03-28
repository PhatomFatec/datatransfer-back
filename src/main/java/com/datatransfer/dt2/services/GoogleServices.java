package com.datatransfer.dt2.serives;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.stereotype.Service;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Value;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.oauth2.GoogleCredentials;

@Service
public class GoogleServices {

    private static final String APPLICATION_NAME = "My Application";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Value("${google.credentials.file.path}")
    private String credentialsFilePath;

    public void downloadFile(String fileId, String filePath) throws IOException, GeneralSecurityException {

        // Build the credentials and create the Drive client
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsFilePath))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        Drive driveService = new Drive.Builder(new NetHttpTransport(), JSON_FACTORY, (HttpRequestInitializer) credentials)
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Download the file content
        OutputStream outputStream = new FileOutputStream(filePath);
        driveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);
    }
}






