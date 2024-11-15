package com.example.user.model;
import java.util.Set;

import com.example.policy.model.Poliza;
import com.example.policy.model.PolizaSolicitud;
import com.example.role.model.Rol;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name = "nom_usuario", unique = true)
    private String nombreUsuario;

    @NotBlank(message = "{campo.requerido}")
    @Size(min = 5, message = "{campo.size.contrasena}")
    @Column(name = "contrasena")
    private String contrasena;
    
    @NotBlank(message = "{campo.requerido}")
    @Email(message = "{campo.email}")
    @Column(unique = true) 
    private String correo;
    
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

    private Integer estado;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PolizaSolicitud> polizaSolicitudes;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Poliza> polizas;
    
    
}
