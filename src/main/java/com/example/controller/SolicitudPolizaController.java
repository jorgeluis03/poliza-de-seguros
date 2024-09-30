package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.SolicitudDTO;
import com.example.exceptions.ClienteNoEncontradoException;
import com.example.service.PolizaSolicitudService;

@RestController
@RequestMapping("v1/solictud-polizas")
public class SolicitudPolizaController {
	
	@Autowired
	PolizaSolicitudService polizaSolicitudService;
	
	@PostMapping
	public ResponseEntity<?> crearSolitudPoliza(@RequestBody SolicitudDTO solicitudDTO ) throws ClienteNoEncontradoException{	    
		polizaSolicitudService.registrarSolicitudPoliza(solicitudDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("Solicitud registrada");
	}
}
