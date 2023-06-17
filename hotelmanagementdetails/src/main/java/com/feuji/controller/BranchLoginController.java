package com.feuji.controller;

import java.util.List;

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
import com.feuji.dto.BranchLogin;
import com.feuji.service.BranchLoginService;

@RestController
@RequestMapping("/branchlogin")
public class BranchLoginController {

	@Autowired
	private BranchLoginService branchLoginService;

	@GetMapping(value = "/verify/{username}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Branch> verifybranchLogin(@PathVariable("username") String username,
			@PathVariable("password") String password) {
		// Invoke the verifyBranch method from branchLoginService and capture the result
		Branch branch = branchLoginService.verifyBranch(username, password);

		return ResponseEntity.ok().body(branch);
	}

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> savebranchLogin(@RequestBody BranchLogin branchLogin) {
		branchLoginService.saveBranchLogin(branchLogin);
		return ResponseEntity.ok().body("branchLogin details saved successfully");
	}

	@GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BranchLogin> getbranchLogin(@PathVariable("id") int id) {
		return ResponseEntity.ok().body(branchLoginService.getBranchLoginById(id));
	}

	@GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BranchLogin> getbranchLogines() {
		return branchLoginService.getBranchLoginDetails();

	}

	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deletebranchLogin(@PathVariable("id") int id) {
		branchLoginService.deleteBranchLogin(id);
		return ResponseEntity.ok().body("branchLogin deleted successfully");
	}
}

