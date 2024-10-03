package com.example.service;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.clases.PolizaStrategyFactory;
import com.example.entity.PolizaSolicitud;
import com.example.enume.EstadoSolicitud;
import com.example.exceptions.SolicitudPolizaNoEncontradoException;
import com.example.interfaces.PolizaStrategy;
import com.example.repository.PolizaSolicitudRepository;

@Service
public class PolizaService {
	
	@Autowired
	PolizaSolicitudRepository polizaSolicitudRepository;
	
	@Autowired
	PolizaStrategyFactory polizaStrategyFactory;
	
	
	@Transactional
	public Map<String, Object> modificarSolicitudPoliza(int id, String estado) {
		
	    Map<String, Object> responseMap = new HashMap<>();
	    
	    try {
	        PolizaSolicitud polizaSolicitud = polizaSolicitudRepository.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada: " + id));
	        
	        if (estado.equalsIgnoreCase("APROBADO")) {
	        	
	            polizaSolicitud.setEstado(EstadoSolicitud.APROBADO);
	            polizaSolicitudRepository.save(polizaSolicitud);
	            
	            // Se aplica el patron Strategy para realizar el guardado en las respectivas tablas en la bd segun el tipo de poliza
	            PolizaStrategy strategy = polizaStrategyFactory.getStrategy(polizaSolicitud.getIdTipoPoliza());
	            strategy.crearPoliza(polizaSolicitud);
	            
	        } else if (estado.equalsIgnoreCase("RECHAZADO")) {
	        	
	            polizaSolicitud.setEstado(EstadoSolicitud.RECHAZADO);
	            polizaSolicitudRepository.save(polizaSolicitud);
	            
	        } else {
	            throw new IllegalArgumentException("Estado inválido: " + estado);
	        }

	        responseMap.put("id", polizaSolicitud.getIdSolicitud());
	        responseMap.put("estado", polizaSolicitud.getEstado());
	        return responseMap;

	    } catch (Exception e) {
	        
	        throw new RuntimeException("No se pudo modificar la solicitud");
	    }
	}

}
