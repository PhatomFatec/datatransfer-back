package com.datatransfer.dt2.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dt.fatec.models.Credentials;
import com.dt.fatec.services.CredentialService;

@RestController
@RequestMapping(value = "/credentials")
public class CredentialsController {
	
	@Autowired
	private CredentialService service; 
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Credentials>> findAll() {
		List<Credentials> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Credentials> findById(@PathVariable Long id) {
		Credentials cred = service.findById(id);
		return ResponseEntity.ok().body(cred);
	}

	@PostMapping
	public ResponseEntity<Credentials> saveFolder(@RequestBody Credentials obj) {
		Credentials cred = service.Credentials(obj);
		obj = service.save(obj);
		return ResponseEntity.ok().body(cred);
	}

}
