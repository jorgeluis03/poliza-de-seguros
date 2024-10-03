package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
    private Integer idUsuario;

	@NotBlank(message = "{campo.requerido}")
    @Column(name = "nom_usuario", nullable = false, unique = true) // nombre de usuario debe ser unico
    private String nombreUsuario;
    
    @NotBlank(message = "{campo.requerido}")
    @Size(min = 5, message = "{campo.size.contrasena}")
    @Column(nullable = false)
    private String contrasena;
    
    @NotBlank(message = "{campo.requerido}")
    @Email(message = "{campo.email}")
    @Column(nullable = false, unique = true) 
    private String correo;
    
    private Integer estado;
    
    
}
