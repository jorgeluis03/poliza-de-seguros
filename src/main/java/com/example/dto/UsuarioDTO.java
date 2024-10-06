package com.example.dto;
import lombok.Data;

@Data
public class UsuarioDTO {
	
	private int idUsuario;
	private String nomUsuario;
	private String correo;
	private String nombre;
	private String apellido;
	private String dni;
	private String telefono;
	private String direccion;
	private Integer idRol;

}
