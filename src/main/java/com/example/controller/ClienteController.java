package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ClienteDTO;
import com.example.service.ClienteService;

@RestController
@RequestMapping("v1/clientes")
public class ClienteController {
	
	@Autowired
	ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<?> getAllClientsEntity (){
		return ResponseEntity.ok(clienteService.getAllClients());
	}
	
	@PostMapping
	public ResponseEntity<?> saveClientEntity (@RequestBody ClienteDTO clienteDTO){
		clienteService.saveClient(clienteDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado");
	}
}
