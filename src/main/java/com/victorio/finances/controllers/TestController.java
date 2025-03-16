package com.victorio.finances.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class TestController {

	@GetMapping
	public ResponseEntity<String> homePage() {
		return ResponseEntity.ok().body("Acesso permitido");
	}
}
