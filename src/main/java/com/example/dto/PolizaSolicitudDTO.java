package com.example.dto;
import java.time.LocalDate;

import com.example.enume.EstadoSolicitud;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolizaSolicitudDTO {
	
	private Integer idPolizaSolicitud;
	private Integer idUsuario;
    private Integer idTipoPoliza;
    private LocalDate fechaSolicitud;
    private EstadoSolicitud estado;
    private UsuarioDTO usuarioDTO;
      
}
