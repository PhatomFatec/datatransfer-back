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
import org.springframework.web.bind.annotation.RestController;

import com.datatransfer.dt2.models.History;
import com.datatransfer.dt2.services.HistoryService;

@CrossOrigin
@RestController
@RequestMapping(value = "/history")
public class HistoryController {

	@Autowired
	private HistoryService service;


	@GetMapping
	public ResponseEntity<List<History>> findAll() {
		List<History> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<History> findById(@PathVariable Long id) {
		History folder = service.findById(id);
		return ResponseEntity.ok().body(folder);
	}

	@PostMapping
	public ResponseEntity<History> saveHistory(@RequestBody History obj) {
		History folder = service.History(obj);
		obj = service.save(obj);
		return ResponseEntity.ok().body(folder);
	}

}
