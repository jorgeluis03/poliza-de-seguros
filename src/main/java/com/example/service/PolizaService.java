package com.example.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.DetallesAutoDTO;
import com.example.dto.PolizaDTO;
import com.example.dto.PolizaInmuebleDTO;
import com.example.entity.Poliza;
import com.example.entity.PolizaAuto;
import com.example.entity.PolizaInmueble;
import com.example.entity.Usuario;
import com.example.enume.EstadoPoliza;
import com.example.exceptions.PolizaNoEncontradaException;
import com.example.exceptions.UsuarioNoEncontradoException;
import com.example.repository.PolizaAutoRepository;
import com.example.repository.PolizaInmuebleRepository;
import com.example.repository.PolizaRepository;
import com.example.repository.UsuarioRepository;
import com.example.utils.ApiResult;

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
	
	@Transactional
	public ApiResult<?> crearPoliza(PolizaDTO polizaDTO){
		
		Usuario usuario = usuarioRepository.findById(polizaDTO.getIdUsuario())
							.orElseThrow(() -> new UsuarioNoEncontradoException("No se encontró el usuario con ID: "+polizaDTO.getIdUsuario()));

		Poliza poliza = convertirDtoAEntidad(polizaDTO);
		poliza.setUsuario(usuario);
		poliza.setNumeroPoliza(generarNumeroPoliza());
		
		polizaRepository.save(poliza);
		
		return new ApiResult<>("Poliza creada correctamente", convertirEntidadADto(poliza));
	}
	
	
	@Transactional
	public ApiResult<?> editarPoliza(Integer idPoliza, PolizaDTO polizaDTO) {
	    // Buscar la poliza por ID
	    Poliza poliza = polizaRepository.findById(idPoliza)
	                        .orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));

	    // Actualizar los campos de la poliza con los datos del DTO
	    poliza.setTipoPoliza(polizaDTO.getTipoPoliza());
	    poliza.setDetalles(polizaDTO.getDetalles());
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
	public ApiResult<?> crearPolizaInmueble(int idPoliza, PolizaInmuebleDTO polizaInmuebleDTO) {
        PolizaInmueble polizaInmueble = new PolizaInmueble();
        
        Poliza poliza = polizaRepository.findById(idPoliza)
        		.orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));

        polizaInmueble.setPoliza(poliza);
        
        polizaInmueble.setDireccion(polizaInmuebleDTO.getDireccion());
        polizaInmueble.setTipoInmueble(polizaInmuebleDTO.getTipoInmueble());
        
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
    public ApiResult<?> editarDetallesInmueble(int idPoliza, PolizaInmuebleDTO PolizaInmuebleDTO) {
    	PolizaInmueble polizaInmueble = polizaInmuebleRepository.findByPolizaId(idPoliza)
					.orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));

    	polizaInmueble.setTipoInmueble(PolizaInmuebleDTO.getTipoInmueble());
    	polizaInmueble.setDireccion(PolizaInmuebleDTO.getDireccion());

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
	    poliza.setDetalles(polizaDTO.getDetalles());
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
        polizaDTO.setDetalles(poliza.getDetalles());
        polizaDTO.setFechaInicio(poliza.getFechaInicio());
        polizaDTO.setFechaVencimiento(poliza.getFechaVencimiento());
        polizaDTO.setMontoAsegurado(poliza.getMontoAsegurado());
        polizaDTO.setEstado(poliza.getEstado());

        return polizaDTO;
	}
	
	public String generarNumeroPoliza() {
        Random random = new Random();
        int numeroPoliza = 1000000 + random.nextInt(9000000);
        return "POL" + numeroPoliza;
    }

}
