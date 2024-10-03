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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
@Data
@Entity
@Table(name = "polizasauto")
public class PolizaAuto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_poliza_auto", nullable = false)
    private Integer idPolizaAuto;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "id_auto")
    private Auto auto;

    @Column(name = "numero_poliza", length = 50, nullable = false)
    private String numeroPoliza;

    @Column(name = "fecha_inicio",nullable = true)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = true)
    private LocalDate fechaFin;

	@Positive(message = "{campo.invalido}")
    @Column(name = "monto_asegurado")
    private Double montoAsegurado;

    @Column(name = "estado")
    private Integer estado;
	
 
    
}
