package com.datatransfer.dt2.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datatransfer.dt2.models.FoldersSelect;
import com.datatransfer.dt2.services.FolderSelectService;

@CrossOrigin
@RestController
@RequestMapping
public class FoldersSelectController {

	@Autowired
	private FolderSelectService selectService;
	
	@PostMapping("/folders/select")
	public ResponseEntity<FoldersSelect> saveFolderSelect(@RequestBody FoldersSelect obj) {
		FoldersSelect fol2 = selectService.save(obj);
		return ResponseEntity.ok().body(fol2);
	}
	

}
