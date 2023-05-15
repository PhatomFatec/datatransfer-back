package com.datatransfer.dt.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.datatransfer.dt2.services.GoogleServices;

@ContextConfiguration
public class GoogleServiceTest {
	
	@Autowired
	private GoogleServices service;

	@Test
	public void buscarHistoryPorId() {
		assertThrows(NullPointerException.class, () -> {
			service.downloadFile(null, null);
		});
	}

}
