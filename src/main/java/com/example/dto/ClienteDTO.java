package com.example.dto;

public class ClienteDTO {
	
    private int idUsuario;
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private int estado;
    
    // Este contructor se usa al momento de guardar un cliente
	public ClienteDTO(int idUsuario, String dni, String nombre, String apellido, String telefono, String direccion) {
		this.idUsuario = idUsuario;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	
	// Este contructor se usa al momento de listar los clientes
		public ClienteDTO(int idUsuario, String dni, String nombre, String apellido, String telefono, String direccion, int estado) {
			this.idUsuario = idUsuario;
			this.dni = dni;
			this.nombre = nombre;
			this.apellido = apellido;
			this.telefono = telefono;
			this.direccion = direccion;
			this.estado = estado;
		}
	
	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
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
    
    
    
}
