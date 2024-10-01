package com.example.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.exceptions.ClienteNoEncontradoException;
import com.example.exceptions.UsuarioNoEncontradoException;
@RestControllerAdvice
public class HandlerExceptionController {
	
	@ExceptionHandler(UsuarioNoEncontradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> usuarioNoEncontradoException(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ClienteNoEncontradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> clienteNoEncontradoException(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> IllegalArgumentException(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
}
