package com.example.dto;

import com.example.enume.EstadoSolicitud;

public class PolizaSolicitudDTO {
	private int idPolizaSolicitud;
	private int idCliente;
    private int idTipoPoliza;
    private String fechaSolicitud;
    private String detalles;
    private EstadoSolicitud estado;
    
    public PolizaSolicitudDTO() {};
    
	public PolizaSolicitudDTO(int idCliente, int idTipoPoliza, String detalles) {
		this.idCliente = idCliente;
		this.idTipoPoliza = idTipoPoliza;
		this.detalles = detalles;
	}
	
	
	
	public PolizaSolicitudDTO(int idPolizaSolicitud, int idCliente, int idTipoPoliza, String fechaSolicitud,
			String detalles, EstadoSolicitud estado) {
		this.idPolizaSolicitud = idPolizaSolicitud;
		this.idCliente = idCliente;
		this.idTipoPoliza = idTipoPoliza;
		this.fechaSolicitud = fechaSolicitud;
		this.detalles = detalles;
		this.estado = estado;
	}

	public int getIdPolizaSolicitud() {
		return idPolizaSolicitud;
	}

	public void setIdPolizaSolicitud(int idPolizaSolicitud) {
		this.idPolizaSolicitud = idPolizaSolicitud;
	}

	public EstadoSolicitud getEstado() {
		return estado;
	}

	public void setEstado(EstadoSolicitud estado) {
		this.estado = estado;
	}

	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public int getIdTipoPoliza() {
		return idTipoPoliza;
	}
	public void setIdTipoPoliza(int idTipoPoliza) {
		this.idTipoPoliza = idTipoPoliza;
	}
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public String getDetalles() {
		return detalles;
	}
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
    
    
}
