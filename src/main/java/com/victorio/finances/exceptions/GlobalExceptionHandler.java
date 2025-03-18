package com.victorio.finances.exceptions;

import java.util.HashMap;
import java.util.Map;

// Importar http, validation, bind, context e servlet
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

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
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	            HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
	        
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField(); 
			String message = error.getDefaultMessage(); 
	        errors.put(fieldName, message); 
	    	});

	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
