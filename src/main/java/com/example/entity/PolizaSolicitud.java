package com.example.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "solicitud")
public class PolizaSolicitud {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_solicitud")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Persona persona;
	
	private int tipo_poliza;
	private double monto_asegurado;
	@Temporal(TemporalType.DATE)
	private Date fecha_solicitud;
	private String estado_solicitud;
	
	public PolizaSolicitud() {}
	
	public PolizaSolicitud(Persona persona, int tipo_poliza, double monto_asegurado, Date fecha_solicitud, String estado_solicitud) {
		this.persona = persona;
		this.tipo_poliza = tipo_poliza;
		this.monto_asegurado = monto_asegurado;
		this.fecha_solicitud = fecha_solicitud;
		this.estado_solicitud = estado_solicitud;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public int getTipo_poliza() {
		return tipo_poliza;
	}
	public void setTipo_poliza(int tipo_poliza) {
		this.tipo_poliza = tipo_poliza;
	}
	public double getMonto_asegurado() {
		return monto_asegurado;
	}
	public void setMonto_asegurado(double monto_asegurado) {
		this.monto_asegurado = monto_asegurado;
	}
	public Date getFecha_solicitud() {
		return fecha_solicitud;
	}
	public void setFecha_solicitud(Date fecha_solicitud) {
		this.fecha_solicitud = fecha_solicitud;
	}
	public String getEstado_solicitud() {
		return estado_solicitud;
	}
	public void setEstado_solicitud(String estado_solicitud) {
		this.estado_solicitud = estado_solicitud;
	}
	
	
}
