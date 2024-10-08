package com.example.service;
import java.time.LocalDate;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dto.PolizaSolicitudDTO;
import com.example.entity.PolizaSolicitud;
import com.example.entity.Usuario;
import com.example.enume.EstadoSolicitud;
import com.example.exceptions.UsuarioNoEncontradoException;
import com.example.repository.PolizaSolicitudRepository;
import com.example.repository.UsuarioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PolizaSolicitudService {
	
	
	@Autowired
	PolizaSolicitudRepository polizaSolicitudRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Transactional
	public HashMap<String, Object> crearSolicitudPoliza(PolizaSolicitudDTO polizaSolicitudDTO) throws JsonProcessingException {
		
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		
		Usuario usuario = usuarioRepository.findById(polizaSolicitudDTO.getIdUsuario())
							.orElseThrow(() -> new UsuarioNoEncontradoException("No se encontro el usuario con el ID: "+polizaSolicitudDTO.getIdUsuario()));
		
		PolizaSolicitud pSolicitud = convertirDTOAEntity(polizaSolicitudDTO);
		pSolicitud.setUsuario(usuario);
		polizaSolicitudRepository.save(pSolicitud);
		
		
		responseMap.put("id", pSolicitud.getIdSolicitud());
		responseMap.put("mensaje", "Poliza solicitada correctamente");
		
		return responseMap;
	
	}
	
	@Transactional
	public Page<PolizaSolicitudDTO> obtenerSolicitudPoliza( Pageable pageable) throws JsonProcessingException{
		
		Page<PolizaSolicitud> polizasolicitudPage =  polizaSolicitudRepository.findAll(pageable);
		
		return polizasolicitudPage.map((psolicitud) -> convertirEntityADTO(psolicitud));
	}
	
	
	
	public PolizaSolicitud convertirDTOAEntity(PolizaSolicitudDTO pSolicitudDTO) throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		PolizaSolicitud pSolicitud = new PolizaSolicitud();
		pSolicitud.setIdTipoPoliza(pSolicitudDTO.getIdTipoPoliza());
		pSolicitud.setDetalles(mapper.writeValueAsString(pSolicitudDTO.getDetalles()));
		pSolicitud.setFechaSolicitud(LocalDate.now());
		pSolicitud.setEstado(EstadoSolicitud.PENDIENTE);
		
		return pSolicitud;
	}
	
	public PolizaSolicitudDTO convertirEntityADTO(PolizaSolicitud pSolicitud) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		PolizaSolicitudDTO pSolicitudDTO = new PolizaSolicitudDTO();
		pSolicitudDTO.setIdPolizaSolicitud(pSolicitud.getIdSolicitud());
		pSolicitudDTO.setIdUsuario(pSolicitud.getUsuario().getIdUsuario());
		pSolicitudDTO.setIdTipoPoliza(pSolicitud.getIdTipoPoliza());
		pSolicitudDTO.setFechaSolicitud(pSolicitud.getFechaSolicitud());
		pSolicitudDTO.setEstado(pSolicitud.getEstado());
		
		try {
	        pSolicitudDTO.setDetalles(mapper.readTree(pSolicitud.getDetalles()));
	    } catch (JsonProcessingException e) {
	        throw new RuntimeException("Error al leer detalles del JSON", e);
	    }
	    
	    pSolicitudDTO.setEstado(pSolicitud.getEstado());
	    
	    return pSolicitudDTO;
	}

	
}
