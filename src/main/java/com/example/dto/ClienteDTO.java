package com.example.dto;

import lombok.Data;

@Data
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
    
	public ClienteDTO() {};
}
