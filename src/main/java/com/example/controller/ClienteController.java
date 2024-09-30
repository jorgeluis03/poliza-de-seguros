package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ClienteDTO;
import com.example.exceptions.UsuarioNoEncontradoException;
import com.example.service.ClienteService;

@RestController
@RequestMapping("/v1/clientes")
public class ClienteController {
	
	 @Autowired
	 ClienteService clienteService;
	
	@PostMapping
	public ResponseEntity<?> crearCliente(@RequestBody ClienteDTO clienteDTO) throws UsuarioNoEncontradoException {
		clienteService.crearCliente(clienteDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado");
	}
	
	@GetMapping
	public ResponseEntity<?> obtenerTotalCliente(){
		List<ClienteDTO> clienteDTOs = clienteService.obtenerTotalClientes();
		return ResponseEntity.ok(clienteDTOs);
	}
}
