package com.example.policy.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.example.policy.enums.EstadoPoliza;
import com.example.policy.strategy.ContextStrategy;
import com.example.policy.strategy.PolicyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.policy.dto.DetallesAutoDTO;
import com.example.policy.dto.PolizaDTO;
import com.example.policy.dto.DetallesInmuebleDTO;
import com.example.policy.model.Poliza;
import com.example.policy.model.PolizaAuto;
import com.example.policy.model.PolizaInmueble;
import com.example.user.model.Usuario;
import com.example.policy.exception.PolizaNoEncontradaException;
import com.example.user.exception.UsuarioNoEncontradoException;
import com.example.policy.repository.PolizaAutoRepository;
import com.example.policy.repository.PolizaInmuebleRepository;
import com.example.policy.repository.PolizaRepository;
import com.example.user.repository.UsuarioRepository;
import com.example.common.response.ApiResult;

@Service
public class PolizaService {
	@Autowired
	PolizaRepository polizaRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	PolizaAutoRepository detallesAutoRepository;
	@Autowired
	PolizaInmuebleRepository polizaInmuebleRepository;

	@Autowired
	ContextStrategy contextStrategy;
	
	@Transactional
	public ApiResult<?> crearPoliza(PolizaDTO polizaDTO){
		
		Usuario usuario = usuarioRepository.findById(polizaDTO.getIdUsuario())
							.orElseThrow(() -> new UsuarioNoEncontradoException("No se encontró el usuario con ID: "+polizaDTO.getIdUsuario()));

		Poliza poliza = new Poliza();
		poliza.setUsuario(usuario);
		poliza.setTipoPoliza(polizaDTO.getTipoPoliza());
		poliza.setFechaInicio(polizaDTO.getFechaInicio());
		poliza.setFechaVencimiento(polizaDTO.getFechaVencimiento());
		poliza.setMontoAsegurado(polizaDTO.getMontoAsegurado());
		poliza.setNumeroPoliza(generarNumeroPoliza());
		poliza.setEstado(EstadoPoliza.PENDIENTE);
		polizaRepository.save(poliza);

		PolicyStrategy strategy = contextStrategy.getPolicyStrategy(polizaDTO.getTipoPoliza());
		if(strategy!=null){
			strategy.savePolicy(polizaDTO,poliza);
		}else {
			throw new IllegalArgumentException("Tipo de póliza no soportado: "+polizaDTO.getTipoPoliza());
		}

		return new ApiResult<>("Póliza solicitada correctamente", new HashMap<String, Object>() {{
			put("id", poliza.getIdPoliza());
		}});
	}
	
	
	@Transactional
	public ApiResult<?> editarPoliza(Integer idPoliza, PolizaDTO polizaDTO) {
	    // Buscar la poliza por ID
	    Poliza poliza = polizaRepository.findById(idPoliza)
	                        .orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));

	    // Actualizar los campos de la poliza con los datos del DTO
	    poliza.setFechaInicio(polizaDTO.getFechaInicio());
	    poliza.setFechaVencimiento(polizaDTO.getFechaVencimiento());
	    poliza.setMontoAsegurado(polizaDTO.getMontoAsegurado());
	    poliza.setEstado(polizaDTO.getEstado());

	    polizaRepository.save(poliza);

	    return new ApiResult<>("Poliza actualizada correctamente", convertirEntidadADto(poliza));
	}
	
	@Transactional(readOnly = true)
	public ApiResult<?> obtenerPolizaPorId(Integer idPoliza) {
	    Poliza poliza = polizaRepository.findById(idPoliza)
	                        .orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));

	    return new ApiResult<>("Poliza encontrada", convertirEntidadADto(poliza));
	}
	
	@Transactional(readOnly = true)
	public Page<PolizaDTO> obtenerPolizas(Pageable pageable) {
	    
	    Page<Poliza> polizasPage = polizaRepository.findAll(pageable);
	
	    return polizasPage.map(this::convertirEntidadADto);
	}

	@Transactional
	public void borrarPoliza(Integer idPoliza) {
	    Poliza poliza = polizaRepository.findById(idPoliza)
	                        .orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));

	    polizaRepository.deleteById(idPoliza);
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	@Transactional
	public ApiResult<?> crearDetallesAuto(int idPoliza, DetallesAutoDTO detallesAutoDTO) {
        PolizaAuto polizaAuto = new PolizaAuto();
        Poliza poliza = polizaRepository.findById(idPoliza)
                		.orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));
        
        polizaAuto.setPoliza(poliza);
        polizaAuto.setMarca(detallesAutoDTO.getMarca());
        polizaAuto.setModelo(detallesAutoDTO.getModelo());
        polizaAuto.setAnio(detallesAutoDTO.getAnio());
        polizaAuto.setNumeroPlaca(detallesAutoDTO.getNumeroPlaca());

        detallesAutoRepository.save(polizaAuto);
        return new ApiResult<>("Detalles de poliza Auto creada correctamente",polizaAuto);
    }

	@Transactional(readOnly = true)
	public ApiResult<?> obtenerDetallesAuto(int idPoliza) {
        PolizaAuto polizaAuto = detallesAutoRepository.findByPolizaId(idPoliza)
        							.orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));
       
        return new ApiResult<>("Poliza Auto encontrada",polizaAuto);
    }

	@Transactional
    public ApiResult<?> editarDetallesAuto(int idPoliza, DetallesAutoDTO detallesAutoDTO) {
    	PolizaAuto detallesAuto = detallesAutoRepository.findByPolizaId(idPoliza)
					.orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));

        detallesAuto.setMarca(detallesAutoDTO.getMarca());
        detallesAuto.setModelo(detallesAutoDTO.getModelo());
        detallesAuto.setAnio(detallesAutoDTO.getAnio());
        detallesAuto.setNumeroPlaca(detallesAutoDTO.getNumeroPlaca());

        detallesAutoRepository.save(detallesAuto);
        return new ApiResult<>("Detalles de Poliza Auto editada correctamente",detallesAuto);
    }

	@Transactional
    public void eliminarDetallesAuto(int idPoliza) {
    	PolizaAuto detallesAuto = detallesAutoRepository.findByPolizaId(idPoliza)
				.orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));

        detallesAutoRepository.delete(detallesAuto);
    }
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	@Transactional
	public ApiResult<?> crearPolizaInmueble(int idPoliza, DetallesInmuebleDTO detallesInmuebleDTO) {
        PolizaInmueble polizaInmueble = new PolizaInmueble();
        
        Poliza poliza = polizaRepository.findById(idPoliza)
        		.orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));

        polizaInmueble.setPoliza(poliza);
        
        polizaInmueble.setDireccion(detallesInmuebleDTO.getDireccion());
        polizaInmueble.setTipoInmueble(detallesInmuebleDTO.getTipoInmueble());
        
        polizaInmuebleRepository.save(polizaInmueble);
        return new ApiResult<>("Detalles de poliza Inmueble creada correctamente",polizaInmueble);
    }
	
	@Transactional(readOnly = true)
	public ApiResult<?> obtenerDetallesInmueble(int idPoliza) {
        PolizaInmueble polizaInmueble = polizaInmuebleRepository.findByPolizaId(idPoliza)
        							.orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));
       
        return new ApiResult<>("Poliza Inmbueble encontrada",polizaInmueble);
    }
	
	@Transactional
    public ApiResult<?> editarDetallesInmueble(int idPoliza, DetallesInmuebleDTO DetallesInmuebleDTO) {
    	PolizaInmueble polizaInmueble = polizaInmuebleRepository.findByPolizaId(idPoliza)
					.orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));

    	polizaInmueble.setTipoInmueble(DetallesInmuebleDTO.getTipoInmueble());
    	polizaInmueble.setDireccion(DetallesInmuebleDTO.getDireccion());

        polizaInmuebleRepository.save(polizaInmueble);
        return new ApiResult<>("Detalles de Poliza Inmueble editada correctamente",polizaInmueble);
    }

	@Transactional
    public void eliminarDetallesInmueble(int idPoliza) {
		PolizaInmueble polizaInmueble = polizaInmuebleRepository.findByPolizaId(idPoliza)
				.orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));

        polizaInmuebleRepository.delete(polizaInmueble);
    }
	
	
	public Poliza convertirDtoAEntidad(PolizaDTO polizaDTO) {
	    Poliza poliza = new Poliza();
	    poliza.setTipoPoliza(polizaDTO.getTipoPoliza());
	    poliza.setFechaInicio(polizaDTO.getFechaInicio());
	    poliza.setFechaVencimiento(polizaDTO.getFechaVencimiento());
	    poliza.setMontoAsegurado(polizaDTO.getMontoAsegurado());
	    poliza.setEstado(polizaDTO.getEstado());
	    return poliza;
	}
	
	public PolizaDTO convertirEntidadADto(Poliza poliza) {
		
		PolizaDTO polizaDTO = new PolizaDTO();
        polizaDTO.setIdPoliza(poliza.getIdPoliza());
        polizaDTO.setIdUsuario(poliza.getUsuario().getIdUsuario());
        polizaDTO.setNumeroPoliza(poliza.getNumeroPoliza());
        polizaDTO.setTipoPoliza(poliza.getTipoPoliza());
        polizaDTO.setFechaInicio(poliza.getFechaInicio());
        polizaDTO.setFechaVencimiento(poliza.getFechaVencimiento());
        polizaDTO.setMontoAsegurado(poliza.getMontoAsegurado());
        polizaDTO.setEstado(poliza.getEstado());

        return polizaDTO;
	}
	
	private String generarNumeroPoliza() {
        Random random = new Random();
        int numeroPoliza = 1000000 + random.nextInt(9000000);
        return "POL" + numeroPoliza;
    }

}
