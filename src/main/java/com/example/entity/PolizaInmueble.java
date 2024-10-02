package com.example.entity;

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
	private Date fechaInicio;
	
	@Column(name = "fecha_fin")
	private Date fechaFin;
	
	@Column(name = "monto_asegurado")
	private double montoAsegurado;
	
	@Column(name = "estado")
	private int estado;

	public PolizaInmueble() {}

	public Integer getIdPlizaInmueble() {
		return idPlizaInmueble;
	}

	public void setIdPlizaInmueble(Integer idPlizaInmueble) {
		this.idPlizaInmueble = idPlizaInmueble;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Inmueble getInmueble() {
		return inmueble;
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}

	public String getNumeroPoliza() {
		return numeroPoliza;
	}

	public void setNumeroPoliza(String numeroPoliza) {
		this.numeroPoliza = numeroPoliza;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public double getMontoAsegurado() {
		return montoAsegurado;
	}

	public void setMontoAsegurado(double montoAsegurado) {
		this.montoAsegurado = montoAsegurado;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	
	
}
