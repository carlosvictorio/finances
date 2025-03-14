package com.victorio.finances.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyExistsException() {
		super("User already registered!");
	}
}
