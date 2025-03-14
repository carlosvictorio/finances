package com.victorio.finances.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorio.finances.dto.UserDto;
import com.victorio.finances.services.AuthService;

@RestController
@RequestMapping("/user/auth")
public class AuthController {
	
	@Autowired
	private AuthService service;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDto user) {
		String token = service.login(user);
		return ResponseEntity.status(200).body("Token: " + token);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserDto user) {
		service.register(user);
		return ResponseEntity.status(201).body("User registered successfully!");
	}

}
