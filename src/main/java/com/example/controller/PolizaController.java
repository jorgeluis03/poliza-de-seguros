package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.PolizaDto;
import com.example.service.PolizaService;

@RestController
@RequestMapping("/v1/polizas")
public class PolizaController {
	
	@Autowired
	PolizaService polizaService;
	
	//devolverá una lista de pólizas de	seguro existentes
	@GetMapping
	public ResponseEntity<?> getPolizas(){
		List<PolizaDto> list_PolizaDtos = polizaService.getPolizas();
		return ResponseEntity.ok(list_PolizaDtos);
	}
	
	@PostMapping
	public ResponseEntity<?> createPoliza(){
		return ResponseEntity.status(HttpStatus.CREATED).body("Objeto");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePoliza(){
		return ResponseEntity.status(HttpStatus.OK).body("Objeto actualizado");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePoliza(){
		return ResponseEntity.noContent().build();
	}
}
