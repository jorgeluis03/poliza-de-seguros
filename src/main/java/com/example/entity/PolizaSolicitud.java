package com.example.entity;

import java.sql.Date;

import com.example.enume.EstadoSolicitud;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "polizasolicitud")
public class PolizaSolicitud {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_solicitud")
	private Integer idSolicitud;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@Column(name = "id_tipo_poliza")
	private int idTipoPoliza;
	
	@Column(name = "fecha_solicitud")
	@Temporal(TemporalType.DATE)
	private Date fechaSolicitud;
	
	@Column(name = "detalles", columnDefinition = "json")
	private String detalles;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado", columnDefinition = "enum('Pendiente','Aprobado','Rechazado')")
	private EstadoSolicitud estado;

	public PolizaSolicitud() {};
	
	public Integer getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getIdTipoPoliza() {
		return idTipoPoliza;
	}

	public void setIdTipoPoliza(int idTipoPoliza) {
		this.idTipoPoliza = idTipoPoliza;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public EstadoSolicitud getEstado() {
		return estado;
	}

	public void setEstado(EstadoSolicitud estado) {
		this.estado = estado;
	}
	
	
}
