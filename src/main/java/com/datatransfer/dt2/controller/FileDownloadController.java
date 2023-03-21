package com.datatransfer.dt2.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

@RestController
public class FileDownloadController {

	@GetMapping("/files/{realFileId}")
	public static ByteArrayOutputStream downloadFile(@PathVariable String realFileId) throws IOException {
		/*
		 * Load pre-authorized user credentials from the environment. TODO(developer) -
		 * See https://developers.google.com/identity for guides on implementing OAuth2
		 * for your application.
		 */
		GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
				.createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
		HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

		// Build a new authorized API client service.
		Drive service = new Drive.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(), requestInitializer)
				.setApplicationName("Drive samples").build();

		try {
			OutputStream outputStream = new ByteArrayOutputStream();

			service.files().get(realFileId).executeMediaAndDownloadTo(outputStream);

			return (ByteArrayOutputStream) outputStream;
		} catch (GoogleJsonResponseException e) {
			// TODO(developer) - handle error appropriately
			System.err.println("Unable to move file: " + e.getDetails());
			throw e;
		}
	}

}
