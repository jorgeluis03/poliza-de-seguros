package com.example.policy.dto;
import java.time.LocalDate;
import com.example.policy.enums.EstadoPoliza;
import com.example.user.dto.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolizaDTO {
	
	private Integer idPoliza;
	private String usuario;
	private UsuarioDTO usuarioDTO;
	private String numeroPoliza;
	private String tipoPoliza;
	private Double montoAsegurado;
	private LocalDate fechaInicio;
	private LocalDate fechaVencimiento;
	// Campos de Auto
	private String marcaAuto;
	private String modeloAuto;
	private Integer anioAuto;
	private String numeroPlaca;
	// Campos de Inmueble
	private String direccionInmueble;
	private String tipoInmueble;
	// Campos de Celular
	private String marcaCelular;
	private String modeloCelular;
	@Enumerated(EnumType.STRING)
	private EstadoPoliza estado;

}
