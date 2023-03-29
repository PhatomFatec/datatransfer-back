package com.datatransfer.dt2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datatransfer.dt2.models.Credentials;
import com.datatransfer.dt2.repositories.CredentialsRepository;


@Service
public class CredentialService {

	@Autowired
	private CredentialsRepository repository;

	public List<Credentials> findAll() {
		return repository.findAll();
	}

	public Credentials findById(Long id) {
		Optional<Credentials> obj = repository.findById(id);
		return obj.get();
	}

	public Credentials save(Credentials obj) {
		return repository.save(obj);
	}

	public Credentials Credentials(Credentials obj) {
		return new Credentials(obj.getId(), obj.getClient_id(), obj.getClient_secret(), obj.getProject_id(),
				obj.getRedirect_uris());
	}
}

