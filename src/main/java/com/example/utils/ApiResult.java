package com.example.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResult<T> {
	private String message;
	private T body;
	
	public ApiResult(String message, T body) {
		this.message = message;
		this.body = body;
	}
	
}
