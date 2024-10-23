package com.example.user.dto;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // ignora los campos que son null en la respuesta
public class UsuarioDTO {
	
	private Integer idUsuario;
	private String nombreUsuario;
	private String correo;
	private String nombre;
	private String apellido;
	private String dni;
	private String telefono;
	private String direccion;
	private Integer idRol;
	private Integer estado;
	private String contrasena;
	
}
