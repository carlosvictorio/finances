package com.victorio.finances.exceptions;

public class EntityNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EntityNotFoundException(String entity) {
		super(entity + " not found!");
	}

}
