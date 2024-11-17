package com.example.common.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.policy.exception.PolizaNoEncontradaException;
import com.example.role.exception.RolNoEncontradoException;
import com.example.user.exception.UsuarioNoEncontradoException;
import com.example.common.response.ApiResult;
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
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResult<String>> IllegalArgumentException(Exception e) {
		ApiResult<String> apiResponse = new ApiResult<>(e.getMessage(), null);
		return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PolizaNoEncontradaException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ApiResult<String>> PolizaNoEncontradaException(Exception e) {
		ApiResult<String> apiResponse = new ApiResult<>(e.getMessage(), null);
		return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
}
