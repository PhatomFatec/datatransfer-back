package com.datatransfer.dt2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datatransfer.dt2.models.History;
import com.datatransfer.dt2.repositories.HistoryRepository;

@Service
public class HistoryService {

	@Autowired
	private HistoryRepository repository;

	public History save(History obj) {
		obj.setStatus("Enviado");

		return repository.save(obj);
	}

	public List<History> findAll() {
		return repository.findAll();
	}

	public History findById(Long id) {
		Optional<History> obj = repository.findById(id);
		return obj.get();
	}

	public History History(History obj) {
		return new History(obj.getHistory_id(), obj.getFile_id(), obj.getNome_arquivo(), obj.getTamanho(),
				obj.getTempo(), obj.getData_envio(), obj.getStatus());
	}

}
