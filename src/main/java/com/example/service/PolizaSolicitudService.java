package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.SolicitudDTO;
import com.example.entity.Persona;
import com.example.entity.PolizaSolicitud;
import com.example.exceptions.ClienteNoEncontradoException;
import com.example.repository.PersonaRepository;
import com.example.repository.PolizaSolicitudRepository;

@Service
public class PolizaSolicitudService {
	
	@Autowired
	PolizaSolicitudRepository polizaSolicitudRepository;
	@Autowired
	PersonaRepository personaRepository;
	
	
	@Transactional
	public void registrarSolicitudPoliza(SolicitudDTO solicitudDTO) {
		Persona persona = personaRepository.findById(solicitudDTO.getIdCliente())
							.orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado con ID: " + solicitudDTO.getIdCliente()));
		
		PolizaSolicitud newPolizaSolicitud = new PolizaSolicitud(
												persona, 
												solicitudDTO.getTipoPoliza(),
												solicitudDTO.getMontoAsegurado(),
												solicitudDTO.getFechaSolicitud(),
												solicitudDTO.getEstadoSolicitud());
		
		polizaSolicitudRepository.save(newPolizaSolicitud);
	}
}
