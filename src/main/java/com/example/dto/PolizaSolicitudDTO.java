package com.example.dto;

import com.example.enume.EstadoSolicitud;

import lombok.Data;
@Data
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
    
}
