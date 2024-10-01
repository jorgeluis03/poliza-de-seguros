package com.example.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.PolizaSolicitud;
import com.example.enume.EstadoSolicitud;
import com.example.exceptions.SolicitudPolizaNoEncontradoException;
import com.example.repository.PolizaSolicitudRepository;

@Service
public class PolizaService {
	
	@Autowired
	PolizaSolicitudRepository polizaSolicitudRepository;
	
	
	@Transactional
	public Map<String, Object> modificarSolicitudPoliza(int id, String estado) {
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		PolizaSolicitud polizaSolicitud = polizaSolicitudRepository.findById(id)
					.orElseThrow(()-> new SolicitudPolizaNoEncontradoException("No se encontró la SolicitudPoliza con el Id: "+id));
		
		System.out.println(estado);
		
		if(estado.equalsIgnoreCase("APROBADO")) {
			polizaSolicitud.setEstado(EstadoSolicitud.APROBADO);
			
			// falta agregar lcgica cuando se aprueba la poliza como agregar a la tabla polizas
			
		}else if (estado.equalsIgnoreCase("RECHAZADO")) {
			polizaSolicitud.setEstado(EstadoSolicitud.RECHAZADO);
		}else {
			throw new IllegalArgumentException("Estado inválido: " + estado);
		}
		
		responseMap.put("id", polizaSolicitud.getIdSolicitud());
		responseMap.put("estado", polizaSolicitud.getEstado());
		
		return responseMap;
	}
}
