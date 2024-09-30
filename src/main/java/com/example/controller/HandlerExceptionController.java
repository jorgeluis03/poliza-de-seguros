package com.example.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.exceptions.UsuarioNoEncontradoException;
@RestControllerAdvice
public class HandlerExceptionController {
	
	@ExceptionHandler(UsuarioNoEncontradoException.class)
	public ResponseEntity<?> crearClienteException(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
}
