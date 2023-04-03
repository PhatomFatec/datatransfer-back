package com.datatransfer.dt2.controller;

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

import com.datatransfer.dt2.models.Folders;
import com.datatransfer.dt2.services.FolderService;

@CrossOrigin
@RestController
@RequestMapping(value = "/folders")
public class FoldersController {

	@Autowired
	private FolderService service;


	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Folders>> findAll() {
		List<Folders> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Folders> findById(@PathVariable Long id) {
		Folders folder = service.findById(id);
		return ResponseEntity.ok().body(folder);
	}

	@PostMapping
	public ResponseEntity<Folders> saveFolder(@RequestBody Folders obj) {
		Folders folder = service.Folders(obj);
		obj = service.save(obj);
		return ResponseEntity.ok().body(folder);
	}

}
