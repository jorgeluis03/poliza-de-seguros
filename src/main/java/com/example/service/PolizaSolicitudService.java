package com.example.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.PolizaSolicitudDTO;
import com.example.entity.Cliente;
import com.example.entity.PolizaSolicitud;
import com.example.exceptions.ClienteNoEncontradoException;
import com.example.repository.ClienteRepository;
import com.example.repository.PolizaSolicitudRepository;

@Service
public class PolizaSolicitudService {
	
	
	@Autowired
	PolizaSolicitudRepository polizaSolicitudRepository;
	@Autowired
	ClienteRepository clienteRepository;
	
	@Transactional
	public int crearSolicitudPoliza(PolizaSolicitudDTO polizaSolicitudDTO) {
		
		PolizaSolicitud pSolicitud = convertirDTOAEntity(polizaSolicitudDTO);
		polizaSolicitudRepository.save(pSolicitud);
		
		return pSolicitud.getIdSolicitud();
	
	}
	
	@Transactional
	public List<PolizaSolicitudDTO> obtenerTotalSolicitudPoliza() {
		System.out.println("Hola mundo2");
		List<PolizaSolicitud> totalPolizaSolicitud = polizaSolicitudRepository.findAll();
		System.out.println("Hola mundo22");
		System.out.println(totalPolizaSolicitud);
		return convertirEntityADTO(totalPolizaSolicitud);
	
	}
	
	public PolizaSolicitud convertirDTOAEntity(PolizaSolicitudDTO pSolicitudDTO) {
		
		Cliente cliente = clienteRepository.findById(pSolicitudDTO.getIdCliente())
							.orElseThrow(()-> new ClienteNoEncontradoException("No se encontró el cliente con ID: "+pSolicitudDTO.getIdCliente()));
		
		PolizaSolicitud pSolicitud = new PolizaSolicitud();
		pSolicitud.setCliente(cliente);
		pSolicitud.setIdTipoPoliza(pSolicitudDTO.getIdTipoPoliza());
		pSolicitud.setDetalles(pSolicitudDTO.getDetalles());
		pSolicitud.setFechaSolicitud(Date.valueOf(LocalDate.now()));
		
		return pSolicitud;
	}
	
	public List<PolizaSolicitudDTO> convertirEntityADTO(List<PolizaSolicitud> totalPolizaSolicitud) {
		System.out.println("Hola mundo 1");
		List<PolizaSolicitudDTO> listaDtos = new ArrayList<PolizaSolicitudDTO>();
		
		for (PolizaSolicitud polizaSolicitud : totalPolizaSolicitud) {
			
			PolizaSolicitudDTO solicitudDTO = new PolizaSolicitudDTO();
			solicitudDTO.setFechaSolicitud(String.valueOf(polizaSolicitud.getFechaSolicitud()));
			solicitudDTO.setIdCliente(polizaSolicitud.getCliente().getIdCliente());
			solicitudDTO.setIdTipoPoliza(polizaSolicitud.getIdTipoPoliza());
			solicitudDTO.setDetalles(polizaSolicitud.getDetalles());
			solicitudDTO.setEstado(polizaSolicitud.getEstado());
			solicitudDTO.setIdPolizaSolicitud(polizaSolicitud.getIdSolicitud());
			
			listaDtos.add(solicitudDTO);
		}
		
		return listaDtos;
	}
	
}
