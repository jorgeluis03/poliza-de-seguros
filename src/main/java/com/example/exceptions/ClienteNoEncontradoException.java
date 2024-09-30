package com.example.exceptions;

public class ClienteNoEncontradoException extends RuntimeException{

	public ClienteNoEncontradoException(String message) {
		super(message);
	}
	
}
