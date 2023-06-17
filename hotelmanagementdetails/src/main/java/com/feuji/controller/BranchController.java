package com.feuji.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.dto.Branch;
import com.feuji.service.BranchService;

@RestController
@RequestMapping("/branch")
public class BranchController {

	@Autowired
	private BranchService branchService;

	@PostMapping(value = "/save/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Branch> saveBranch(@RequestBody Branch branch, @PathVariable("id") int hotelId) {
		Branch updatedBranch = branchService.saveBranch(branch, hotelId);
		return ResponseEntity.ok().body(updatedBranch);
	}

	@GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Branch> getBranch(@PathVariable("id") String id) {
		Optional<Branch> optionalBranch = branchService.getBranchById(Integer.parseInt(id));
		Branch branch=null;
		if(optionalBranch.isPresent()) {
			branch=optionalBranch.get();
		}
		return ResponseEntity.ok().body(branch);
	}

	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Branch>> getBranches() {
		return ResponseEntity.ok().body(branchService.getBranchDetails());
	}

	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteBranch(@PathVariable("id") int id) {
		branchService.deleteBranch(id);
		return ResponseEntity.ok().body("branch deleted successfully");
	}
}
