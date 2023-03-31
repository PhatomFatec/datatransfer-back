package com.datatransfer.dt2.controller;
import java.io.FileWriter;
import java.io.IOException;
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

import com.datatransfer.dt2.models.Credentials;
import com.datatransfer.dt2.services.CredentialService;
import com.google.gson.Gson;

@RestController
@RequestMapping(value = "/credentials")
public class CredentialsController {
	
	@Autowired
	private CredentialService service; 
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Credentials>> findAll() {
		List<Credentials> credentials = service.findAll();
		Gson gson = new Gson();
        String json = gson.toJson(credentials);
        saveToFile(json);

		return ResponseEntity.ok().body(credentials);
	}

	private void saveToFile(String json) {
        try {
            FileWriter fileWriter = new FileWriter("credentials.json");
            fileWriter.write(json);
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
	public ResponseEntity<Credentials> saveFolder(@RequestBody Credentials obj) {
		Credentials cred = service.Credentials(obj);
		obj = service.save(obj);
		return ResponseEntity.ok().body(cred);
	}

}
