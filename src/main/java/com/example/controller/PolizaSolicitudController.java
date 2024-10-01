package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.PolizaSolicitudDTO;
import com.example.exceptions.ClienteNoEncontradoException;
import com.example.service.PolizaSolicitudService;

@RestController
@RequestMapping("/v1/poliza-solicitud")
public class PolizaSolicitudController {
	
	@Autowired
	PolizaSolicitudService polizaSolicitudService;
	
	@PostMapping
	public ResponseEntity<?> crearPolizaSolicitud(@RequestBody PolizaSolicitudDTO polizaSolicitudDTO) throws ClienteNoEncontradoException{
		
		int idPolizaSolicitud = polizaSolicitudService.crearSolicitudPoliza(polizaSolicitudDTO);
		
		Map<String, Integer> responseBody = new HashMap<>();
	    responseBody.put("id", idPolizaSolicitud);
	    
		return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
	}
	
	@GetMapping
	public ResponseEntity<?> obtenerSolicitudPolizas(){
		
		List<PolizaSolicitudDTO> listaDtos = polizaSolicitudService.obtenerTotalSolicitudPoliza();
		
		return ResponseEntity.ok(listaDtos);
	}
}
