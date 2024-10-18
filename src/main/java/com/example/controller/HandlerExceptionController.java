package com.example.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.exceptions.ClienteNoEncontradoException;
import com.example.exceptions.PolizaNoEncontradaException;
import com.example.exceptions.RolNoEncontradoException;
import com.example.exceptions.SolicitudPolizaNoEncontradoException;
import com.example.exceptions.UsuarioNoEncontradoException;
import com.example.utils.ApiResult;
@RestControllerAdvice
public class HandlerExceptionController {
	
	@ExceptionHandler(UsuarioNoEncontradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ApiResult<String>> usuarioNoEncontradoException(Exception e) {
		ApiResult<String> apiResponse = new ApiResult<>(e.getMessage(), null);
		return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RolNoEncontradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ApiResult<String>> rolNoEncontradoException(Exception e) {
		ApiResult<String> apiResponse = new ApiResult<>(e.getMessage(), null);
		return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ClienteNoEncontradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ApiResult<String>> clienteNoEncontradoException(Exception e) {
		ApiResult<String> apiResponse = new ApiResult<>(e.getMessage(), null);
		return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResult<String>> IllegalArgumentException(Exception e) {
		ApiResult<String> apiResponse = new ApiResult<>(e.getMessage(), null);
		return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SolicitudPolizaNoEncontradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ApiResult<String>> solicitudPolizaNoEncontradoException(Exception e) {
		ApiResult<String> apiResponse = new ApiResult<>(e.getMessage(), null);
		return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PolizaNoEncontradaException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ApiResult<String>> PolizaNoEncontradaException(Exception e) {
		ApiResult<String> apiResponse = new ApiResult<>(e.getMessage(), null);
		return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
}
