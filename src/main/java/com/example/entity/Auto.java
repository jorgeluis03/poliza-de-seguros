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
@Table(name = "auto")
public class Auto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_auto")
	private Integer idAuto;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@NotBlank(message = "{campo.requerido}")
	private String marca;
	
	@NotBlank(message = "{campo.requerido}")
	private String modelo;
	
	@NotNull(message = "{campo.requerido}")
	@Positive(message = "Debe ingresar un numero positivo")
	private Integer anio;
	
	@NotNull(message = "{campo.requerido}")
	@Positive(message = "{valor.invalido}")
	private Double valor;
	
	
}
