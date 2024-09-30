package com.example.dto;

import java.io.Serializable;

public class PolizaDto implements Serializable {
	private Integer idSeguro;
	private String ciudad;
	
	public PolizaDto(Integer idSeguro, String ciudad) {
		this.idSeguro = idSeguro;
		this.ciudad = ciudad;
	}

	public Integer getIdSeguro() {
		return idSeguro;
	}

	public void setIdSeguro(Integer idSeguro) {
		this.idSeguro = idSeguro;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	
}
