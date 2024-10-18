package com.example.service;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dto.PolizaSolicitudDTO;
import com.example.dto.UsuarioDTO;
import com.example.entity.PolizaSolicitud;
import com.example.entity.Usuario;
import com.example.enume.EstadoSolicitud;
import com.example.exceptions.SolicitudPolizaNoEncontradoException;
import com.example.exceptions.UsuarioNoEncontradoException;
import com.example.repository.PolizaSolicitudRepository;
import com.example.repository.UsuarioRepository;
import com.example.utils.ApiResult;

@Service
public class PolizaSolicitudService {
	
	
	@Autowired
	PolizaSolicitudRepository polizaSolicitudRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Transactional
	public ApiResult<PolizaSolicitudDTO> crearSolicitudPoliza(PolizaSolicitudDTO polizaSolicitudDTO){
	    Usuario usuario = usuarioRepository.findById(polizaSolicitudDTO.getIdUsuario())
	        .orElseThrow(() -> new UsuarioNoEncontradoException("No se encontró el usuario con el ID: " + polizaSolicitudDTO.getIdUsuario()));

	    PolizaSolicitud pSolicitud = convertirDTOAEntity(polizaSolicitudDTO);
	    pSolicitud.setUsuario(usuario);
	    polizaSolicitudRepository.save(pSolicitud);

	    return new ApiResult<>("Póliza solicitada correctamente", convertirEntityADTO(pSolicitud));
	}

	
	@Transactional(readOnly = true)
	public Page<PolizaSolicitudDTO> obtenerSolicitudPoliza( Pageable pageable){
		Page<PolizaSolicitud> polizasolicitudPage =  polizaSolicitudRepository.findAll(pageable);
		
		return polizasolicitudPage.map(this::convertirEntityADTO);
	}
	
	@Transactional
	public ApiResult<PolizaSolicitudDTO> modificarSolicitudPoliza(int id, PolizaSolicitudDTO polizaSolicitudDTO){
		PolizaSolicitud solicitud = polizaSolicitudRepository.findById(id)
	            .orElseThrow(() -> new SolicitudPolizaNoEncontradoException("Solicitud con id " + id + " no encontrada"));
		
		solicitud.setEstado(polizaSolicitudDTO.getEstado());
		polizaSolicitudRepository.save(solicitud);
		
		return new ApiResult<>("Solicitud modificada", convertirEntityADTO(solicitud));
	}
	
	@Transactional(readOnly = true)
	public ApiResult<PolizaSolicitudDTO> obtenerSolicitudPolizaPorId(int id){
		PolizaSolicitud pSolicitud = polizaSolicitudRepository.findById(id)
									.orElseThrow(() -> new SolicitudPolizaNoEncontradoException("No se enceontro la solicitud de póliza con el ID: "+id));
		
		return new ApiResult<>("Solicitud encontrada", convertirEntityADTO(pSolicitud));
		
	}
	
	public PolizaSolicitud convertirDTOAEntity(PolizaSolicitudDTO pSolicitudDTO) {
        PolizaSolicitud pSolicitud = new PolizaSolicitud();
        pSolicitud.setIdTipoPoliza(pSolicitudDTO.getIdTipoPoliza());
        pSolicitud.setFechaSolicitud(LocalDate.now());
        pSolicitud.setEstado(EstadoSolicitud.PENDIENTE);
        return pSolicitud;
    }
	
	public PolizaSolicitudDTO convertirEntityADTO(PolizaSolicitud pSolicitud) {
	    PolizaSolicitudDTO pSolicitudDTO = new PolizaSolicitudDTO();

	    UsuarioDTO usuarioDTO = new UsuarioDTO();
	    usuarioDTO.setIdUsuario(pSolicitud.getUsuario().getIdUsuario());
	    usuarioDTO.setCorreo(pSolicitud.getUsuario().getCorreo());
	    usuarioDTO.setNombre(pSolicitud.getUsuario().getNombre());
	    usuarioDTO.setApellido(pSolicitud.getUsuario().getApellido());
	    usuarioDTO.setDni(pSolicitud.getUsuario().getDni());

	    pSolicitudDTO.setIdPolizaSolicitud(pSolicitud.getIdSolicitud());
	    pSolicitudDTO.setIdTipoPoliza(pSolicitud.getIdTipoPoliza());
	    pSolicitudDTO.setFechaSolicitud(pSolicitud.getFechaSolicitud());
	    pSolicitudDTO.setEstado(pSolicitud.getEstado());
	    pSolicitudDTO.setUsuarioDTO(usuarioDTO);

	    return pSolicitudDTO;
	}


	
}
