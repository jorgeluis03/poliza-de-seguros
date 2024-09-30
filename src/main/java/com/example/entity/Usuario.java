package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nom_usuario", nullable = false, unique = true) // nombre de usuario debe ser unico
    private String nombreUsuario;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false, unique = true) 
    private String correo;
    
    private int estado;
    
    public Usuario() {};

	public Usuario(String nombreUsuario, String contrasena, String correo) {
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.correo = correo;
	}
	
	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
    
    
}
