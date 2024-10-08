package com.example.dto;
import java.time.LocalDate;

import com.example.enume.EstadoSolicitud;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;
@Data
public class PolizaSolicitudDTO {
	
	private int idPolizaSolicitud;
	private int idUsuario;
    private int idTipoPoliza;
    private LocalDate fechaSolicitud;
    private JsonNode detalles;
    private EstadoSolicitud estado;
  
}
