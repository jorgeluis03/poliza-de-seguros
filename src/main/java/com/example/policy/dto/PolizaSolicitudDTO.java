package com.example.policy.dto;
import java.time.LocalDate;

import com.example.policy.enums.EstadoSolicitud;
import com.example.user.dto.UsuarioDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

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
