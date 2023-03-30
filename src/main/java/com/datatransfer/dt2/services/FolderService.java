package com.datatransfer.dt2.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datatransfer.dt2.models.Folders;
import com.datatransfer.dt2.repositories.FoldersRepository;
@Service
public class FolderService {

	@Autowired
	private FoldersRepository repository;

	public List<Folders> findAll() {
		return repository.findAll();
	}

	public Folders findById(Long id) {
		Optional<Folders> obj = repository.findById(id);
		return obj.get();
	}

	public Folders save(Folders obj) {
		return repository.save(obj);
	}

	public Folders Folders(Folders obj) {
		return new Folders(obj.getId(), obj.getCodigo(), obj.getNome());
	}

}
