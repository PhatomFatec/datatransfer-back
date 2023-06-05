package com.datatransfer.dt2.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.datatransfer.dt2.dtos.CredentialsDTO;
import com.datatransfer.dt2.models.Credentials;
import com.datatransfer.dt2.models.CredentialsAWS;
import com.datatransfer.dt2.repositories.CredentialsAWSRepository;
import com.datatransfer.dt2.services.CredentialService;
import com.google.gson.Gson;

@CrossOrigin
@RestController
@RequestMapping(value = "/credentials")
public class CredentialsController {

	@Autowired
	private CredentialService service;

	@Autowired
	private CredentialsAWSRepository credAwsRepo;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Credentials>> findAll() {
		List<Credentials> credentials = service.findAll();
		return ResponseEntity.ok().body(credentials);
	}

	private void saveToFile(String json) {
		try {
			FileWriter fileWriter = new FileWriter("src/main/resources/credenciais.json");
			fileWriter.write("{\"web\":" + json + "}");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Credentials> findById(@PathVariable Long id) {
		Credentials cred = service.findById(id);
		return ResponseEntity.ok().body(cred);
	}

	@PostMapping
	public ResponseEntity<CredentialsDTO> saveCred(@RequestBody CredentialsDTO obj) {
		Credentials cred = service.FromDTO(obj);
		cred = service.save(cred);
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		saveToFile(json);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping(value = "/aws")
	public ResponseEntity<CredentialsAWS> saveCredAws(@RequestBody CredentialsAWS obj) {
		CredentialsAWS cred = credAwsRepo.save(obj);
		return ResponseEntity.ok().body(cred);
	}

}
