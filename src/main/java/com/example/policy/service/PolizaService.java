package com.example.policy.service;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.example.email.service.EmailServiceImpl;
import com.example.policy.dto.*;
import com.example.policy.enums.EstadoPoliza;
import com.example.policy.model.*;
import com.example.policy.repository.PolizaCelularRepository;
import com.example.policy.strategy.ContextStrategy;
import com.example.policy.strategy.PolicyStrategy;
import com.example.user.service.UsuarioService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	PolizaCelularRepository polizaCelularRepository;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	ContextStrategy contextStrategy;
	@Autowired
	EmailServiceImpl emailService;

	@Transactional
	public ApiResult<?> crearPoliza(PolizaDTO polizaDTO) throws MessagingException, IOException {
		
		Usuario usuario = usuarioRepository.findByNombreUsuario(polizaDTO.getUsuario())
							.orElseThrow(() -> new UsuarioNoEncontradoException("No se encontró el usuario con username: "+polizaDTO.getUsuario()));

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
		emailService.sendMessageUsingThymeleafTemplate("a20200643@pucp.edu.pe","Solicitud de Poliza SegurAI", poliza);
		return new ApiResult<>("Póliza solicitada correctamente", new HashMap<String, Object>() {{
			put("id", poliza.getIdPoliza());
		}});
	}

	@Transactional
	public Page<PolizaDTO> obtenerPolizaPorUsuario(String username, Pageable pageable){
		Usuario usuario = usuarioRepository.findByNombreUsuario(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: "+username));

		Page<Poliza> polizasByUsuario = polizaRepository.findByUsuario(usuario, pageable);
		return polizasByUsuario.map(this::convertirEntidadADto);
	}
	
	@Transactional
	public ApiResult<?> editarPoliza(Integer idPoliza, PolizaDTO polizaDTO) {
		Usuario usuario = usuarioRepository.findByNombreUsuario(polizaDTO.getUsuario())
				.orElseThrow(() -> new UsuarioNoEncontradoException("No se encontró el usuario con username: "+polizaDTO.getUsuario()));
		Poliza poliza = polizaRepository.findById(idPoliza)
						.orElseThrow(() -> new PolizaNoEncontradaException("No se encontró la poliza: "+idPoliza));

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

		return new ApiResult<>("Solicitud de edicion enviada", new HashMap<String, Object>() {{
			put("id", poliza.getIdPoliza());
		}});
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
	    polizaRepository.delete(poliza);
	}

	@Transactional
	public ApiResult<?> modificarEstadoPoliza(int id, Map<String, Object> body){
		Poliza poliza = polizaRepository.findById(id)
				.orElseThrow(() -> new PolizaNoEncontradaException("Solicitud con id " + id + " no encontrada"));

		poliza.setEstado(EstadoPoliza.valueOf(body.get("estado").toString()));
		polizaRepository.save(poliza);

		return new ApiResult<>("Póliza modificada correctamente", new HashMap<String, Object>() {{
			put("id", poliza.getIdPoliza());
		}});
	}

	@Transactional
	public List<PolizaDTO> buscarPoliza(String numPoliza, String tipoPoliza){
		List<Poliza> polizas = polizaRepository.searchPoliza(numPoliza, tipoPoliza);
		return  polizas.stream().map(this::convertirEntidadADto).collect(Collectors.toList());
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Transactional(readOnly = true)
	public ApiResult<?> obtenerDetallesAuto(int idPoliza) {
		PolizaAuto auto = detallesAutoRepository.findByPolizaId(idPoliza)
									.orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));
		DetallesAutoDTO autoDTO = new DetallesAutoDTO();
		autoDTO.setIdAuto(auto.getIdPolizaAuto());
		autoDTO.setIdPoliza(auto.getPoliza().getIdPoliza());
	   	autoDTO.setMarca(auto.getMarca());
	   	autoDTO.setModelo(auto.getModelo());
	   	autoDTO.setAnio(auto.getAnio());
	   	autoDTO.setNumeroPlaca(auto.getNumeroPlaca());
	   	return new ApiResult<>("Poliza Auto encontrada",autoDTO);
    }

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Transactional(readOnly = true)
	public ApiResult<?> obtenerDetallesInmueble(int idPoliza) {
        PolizaInmueble inmueble = polizaInmuebleRepository.findByPolizaId(idPoliza)
        							.orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));
		DetallesInmuebleDTO inmuebleDTO = new DetallesInmuebleDTO();
		inmuebleDTO.setIdPoliza(inmueble.getPoliza().getIdPoliza());
		inmuebleDTO.setIdInmueble(inmueble.getIdPolizaInmueble());
		inmuebleDTO.setDireccion(inmueble.getDireccion());
		inmuebleDTO.setTipoInmueble(inmueble.getTipoInmueble());
        return new ApiResult<>("Poliza Inmbueble encontrada",inmuebleDTO);
    }

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Transactional(readOnly = true)
	public ApiResult<?> obtenerDetallesCelular(int idPoliza) {
		PolizaCelular celular = polizaCelularRepository.findByPolizaId(idPoliza)
				.orElseThrow(() -> new PolizaNoEncontradaException("Poliza no encontrada con ID: " + idPoliza));
		DetallesCelularDTO celularDTO = new DetallesCelularDTO();
		celularDTO.setIdCelular(celular.getIdCelular());
		celularDTO.setIdPoliza(celular.getPoliza().getIdPoliza());
		celularDTO.setMarca(celular.getMarca());
		celularDTO.setModelo(celular.getModelo());
		return new ApiResult<>("Poliza Celular encontrada",celularDTO);
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
        polizaDTO.setUsuario(poliza.getUsuario().getNombreUsuario());
        polizaDTO.setNumeroPoliza(poliza.getNumeroPoliza());
        polizaDTO.setTipoPoliza(poliza.getTipoPoliza());
        polizaDTO.setFechaInicio(poliza.getFechaInicio());
        polizaDTO.setFechaVencimiento(poliza.getFechaVencimiento());
        polizaDTO.setMontoAsegurado(poliza.getMontoAsegurado());
		polizaDTO.setUsuarioDTO(usuarioService.convertirEntityADTO(poliza.getUsuario()));
        polizaDTO.setEstado(poliza.getEstado());

        return polizaDTO;
	}
	
	private String generarNumeroPoliza() {
        Random random = new Random();
        int numeroPoliza = 1000000 + random.nextInt(9000000);
        return "POL" + numeroPoliza;
    }

}
