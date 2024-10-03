package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
@Data
@Entity
@Table(name = "inmuebles")
public class Inmueble {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_inmueble")
	private Integer idInmueble;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@NotBlank(message = "{campo.requerido}")
	private String direccion;
	
	@NotBlank(message = "{campo.requerido}")
	@Column(name = "tipo_inmueble")
	private String tipoInmueble;
	
	@NotNull(message = "{campo.requerido}")
	@Positive(message = "{valor.invalido}")
	private Double valor;

	
}
