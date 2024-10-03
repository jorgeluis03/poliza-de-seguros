package com.example.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.PolizaSolicitudDTO;
import com.example.entity.Auto;
import com.example.entity.Cliente;
import com.example.entity.PolizaSolicitud;
import com.example.enume.EstadoSolicitud;
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
	public Page<PolizaSolicitudDTO> obtenerTotalSolicitudPoliza(Pageable pageable) {
		
		Page<PolizaSolicitud> listaPage = polizaSolicitudRepository.findAll(pageable);
		
		return convertirEntityADTO(listaPage);
	
	}
	
	public PolizaSolicitud convertirDTOAEntity(PolizaSolicitudDTO pSolicitudDTO) {
		
		Cliente cliente = clienteRepository.findById(pSolicitudDTO.getIdCliente())
							.orElseThrow(()-> new ClienteNoEncontradoException("No se encontró el cliente con ID: "+pSolicitudDTO.getIdCliente()));
		
		PolizaSolicitud pSolicitud = new PolizaSolicitud();
		pSolicitud.setCliente(cliente);
		pSolicitud.setIdTipoPoliza(pSolicitudDTO.getIdTipoPoliza());
		pSolicitud.setDetalles(pSolicitudDTO.getDetalles());
		pSolicitud.setFechaSolicitud(LocalDate.now());
		pSolicitud.setEstado(EstadoSolicitud.PENDIENTE);
		
		return pSolicitud;
	}
	
	public Page<PolizaSolicitudDTO> convertirEntityADTO(Page<PolizaSolicitud> listaPage) {
	    return listaPage.map(polizaSolicitud -> 
	        new PolizaSolicitudDTO(
	            polizaSolicitud.getIdSolicitud(),
	            polizaSolicitud.getCliente().getIdCliente(),
	            polizaSolicitud.getIdTipoPoliza(),
	            String.valueOf(polizaSolicitud.getFechaSolicitud()),  
	            polizaSolicitud.getDetalles(),
	            polizaSolicitud.getEstado()
	        )
	    );
	  
	}
	
}
