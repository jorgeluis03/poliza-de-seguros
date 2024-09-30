package com.example.dto;

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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomUsuario() {
		return nomUsuario;
	}
	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	
	
}
