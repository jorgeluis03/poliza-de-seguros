package com.example.dto;

import java.time.LocalDate;

import com.example.enume.EstadoPoliza;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolizaDTO {
	
	private Integer idPoliza;
	private Integer idUsuario;
	private String numeroPoliza;
	private Integer tipoPoliza;
	private String detalles;
	private LocalDate fechaInicio;
	private LocalDate fechaVencimiento;
	private Double montoAsegurado;
	@Enumerated(EnumType.STRING)
	private EstadoPoliza estado;

}
