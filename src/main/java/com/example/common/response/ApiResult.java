package com.example.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResult<T> {
	private String message;
	private T payload;
	
	public ApiResult(String message, T payload) {
		this.message = message;
		this.payload = payload;
	}
	
}
