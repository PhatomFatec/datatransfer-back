package com.datatransfer.dt2;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication
public class DtApplication {

	public static void main(String... args) throws IOException, GeneralSecurityException {
		// Build a new authorized API client service.

		SpringApplication.run(DtApplication.class, args);

	}

}
