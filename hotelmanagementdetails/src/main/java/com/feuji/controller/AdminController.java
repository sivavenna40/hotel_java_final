package com.feuji.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feuji.dto.AdminLogin;
import com.feuji.dto.Branch;
import com.feuji.service.AdminLoginService;

@RestController
@RequestMapping("/adminlogin")
public class AdminController {

	@Autowired
	private AdminLoginService adminLoginService;

	@GetMapping(value = "/verify/{username}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Branch>> verifyAdminLogin(@PathVariable("username") String username,
			@PathVariable("password") String password) {

		return ResponseEntity.ok(adminLoginService.verifyAdmin(username, password));
	}

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdminLogin> saveAdmin(@RequestBody AdminLogin adminLogin) {
		adminLoginService.saveadminLogin(adminLogin);
		return ResponseEntity.ok().body(adminLogin);
	}

}











