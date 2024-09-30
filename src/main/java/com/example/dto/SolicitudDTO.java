package com.example.dto;

import java.sql.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

public class SolicitudDTO {
	private int idCliente;
    private int tipoPoliza;
    private double montoAsegurado;
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitud;
    private String estadoSolicitud;
    
	public SolicitudDTO(int idCliente, int tipoPoliza, double montoAsegurado, Date fechaSolicitud,
			String estadoSolicitud) {
		this.idCliente = idCliente;
		this.tipoPoliza = tipoPoliza;
		this.montoAsegurado = montoAsegurado;
		this.fechaSolicitud = fechaSolicitud;
		this.estadoSolicitud = estadoSolicitud;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getTipoPoliza() {
		return tipoPoliza;
	}

	public void setTipoPoliza(int tipoPoliza) {
		this.tipoPoliza = tipoPoliza;
	}

	public double getMontoAsegurado() {
		return montoAsegurado;
	}

	public void setMontoAsegurado(double montoAsegurado) {
		this.montoAsegurado = montoAsegurado;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}
    
    
}
