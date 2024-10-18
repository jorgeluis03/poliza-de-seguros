package com.example.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "polizasinmueble")
public class PolizaInmueble {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_poliza_inmueble")
	private Integer idPolizaInmueble;
	
	@OneToOne
	@JoinColumn(name = "id_poliza")
	private Poliza poliza;
	
	private String direccion;
	
	@Column(name = "tipo_inmueble")
	private String tipoInmueble;
	
}
