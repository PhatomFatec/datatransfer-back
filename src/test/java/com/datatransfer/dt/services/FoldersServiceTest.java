package com.datatransfer.dt.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.datatransfer.dt2.services.FolderService;

@ContextConfiguration
public class FoldersServiceTest {

	@Autowired
	private FolderService service;

	@Test
	public void buscarHistoryPorId() {
		assertThrows(NullPointerException.class, () -> {
			service.findById(89L);
		});
	}

	@Test
	public void NovoHistoryNoOk() {
		assertThrows(NullPointerException.class, () -> {
			service.save(null);
		});
	}
}
