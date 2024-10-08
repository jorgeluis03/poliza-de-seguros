package com.example.entity;
import java.time.LocalDate;
import com.example.enume.EstadoSolicitud;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
@Data
@Entity
@Table(name = "polizasolicitud")
public class PolizaSolicitud {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_solicitud")
	private Integer idSolicitud;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@NotNull(message = "{campo.requerido}")
	@Positive(message = "{valor.invalido}")
	@Column(name = "id_tipo_poliza")
	private Integer idTipoPoliza;
	
	@Column(name = "fecha_solicitud")
	private LocalDate fechaSolicitud;
	
	@Column(name = "detalles", columnDefinition = "json")
	private String detalles;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado", columnDefinition = "enum('Pendiente','Aprobado','Rechazado')")
	private EstadoSolicitud estado;

}
