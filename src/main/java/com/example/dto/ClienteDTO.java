package com.example.dto;

public class ClienteDTO {
	private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String password;
    private int idRol;
    
	public ClienteDTO(String nombre, String email, String telefono, String direccion, String password, int idRol) {
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.direccion = direccion;
		this.password = password;
		this.idRol = idRol;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIdRol() {
		return idRol;
	}
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}
    
    
}
