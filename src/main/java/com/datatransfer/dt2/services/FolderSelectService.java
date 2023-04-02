package com.datatransfer.dt2.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datatransfer.dt2.models.FoldersSelect;
import com.datatransfer.dt2.repositories.FoldersSelectRepository;
@Service
public class FolderSelectService {

	@Autowired
	private FoldersSelectRepository repository;

	public FoldersSelect findById(Long id) {
		Optional<FoldersSelect> obj = repository.findById(id);
		return obj.get();
	}

	public FoldersSelect save(FoldersSelect obj) {
		return repository.save(obj);
	}

}
