package com.example.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.Data;
@Data
@Entity
@Table(name = "polizasinmuebles")
public class PolizaInmueble {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_poliza_inmueble")
	private Integer idPlizaInmueble;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@OneToOne
	@JoinColumn(name = "id_inmueble")
	private Inmueble inmueble;
	
	@Column(name = "numero_poliza")
	private String numeroPoliza;
	
	@Column(name = "fecha_inicio")
	private LocalDate fechaInicio;
	
	@Column(name = "fecha_fin")
	private LocalDate fechaFin;
	
	@Positive(message = "{campo.invalido}")
    @Column(name = "monto_asegurado")
	private Double montoAsegurado;
	
	@Column(name = "estado")
	private Integer estado;
	
}
