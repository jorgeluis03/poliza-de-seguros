package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @OneToOne
    @JoinColumn(name = "id_usuario", unique = true)
    private Usuario usuario;

    @NotBlank(message = "{campo.requerido}")
    @Size(min = 8, max = 8, message = "El DNI debe tener exactamente 8 digitos")
    @Column(nullable = false, unique = true)
    private String dni;

    @NotBlank(message = "{campo.requerido}")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "Este campo es requerido")
    @Column(nullable = false)
    private String apellido;

    @NotBlank(message = "{campo.requerido}")
    @Size(min = 9, message = "El telefono debe tener minimo 9 digitos")
    @Column(nullable = false)
    private String telefono;
    
    @NotBlank(message = "{campo.requerido}")
    @Column(nullable = false)
    private String direccion;
    
    @Column
    private Integer estado;
    
}
    
    
