package com.example.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
	private int id;
	private String nomUsuario;
	private String correo;
	private int estado;
	
	public UsuarioDTO(int id, String nomUsuario, String correo, int estado) {
		this.id = id;
		this.nomUsuario = nomUsuario;
		this.correo = correo;
		this.estado = estado;
	}
}
