package com.victorio.finances.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String> HandleBadCredentialsException(BadCredentialsException e) {
		return ResponseEntity.status(401).body("Credenciais inválida:" + e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> HandleUnexpectedException(Exception e) {
		return ResponseEntity.status(500).body("Unexpected error: " + e.getLocalizedMessage());
	}
}
