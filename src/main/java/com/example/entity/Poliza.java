package com.example.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.Data;
@Data
@Entity
@Table(name = "")
public class Poliza {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_poliza")
	private Integer idPoliza;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@Column(name = "numero_poliza", length = 45)
    private String numeroPoliza;
	
	@Column(name = "tipo_poliza", length = 50)
    private String tipoPoliza;
	
	private String detalles;
	
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

	@Positive(message = "{campo.invalido}")
    @Column(name = "monto_asegurado")
    private Double montoAsegurado;

    @Column(name = "estado")
    private Integer estado;
    
    // mappedy con polizaAutos y PolizaInmueble
}
