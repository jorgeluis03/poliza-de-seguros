package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ClienteDTO;
import com.example.exceptions.UsuarioNoEncontradoException;
import com.example.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/v1/api/clientes")
public class ClienteController {
	
	 @Autowired
	 ClienteService clienteService;
	
	@PostMapping //Crear un nuevo usuario.
	public ResponseEntity<?> crearCliente(@RequestBody ClienteDTO clienteDTO) throws UsuarioNoEncontradoException {
		clienteService.crearCliente(clienteDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado");
	}
	
	
	@Operation(summary = "Listado de clientes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna la lista de clientes",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "404", description = "Clientes no encontrados",
					content = @Content),
			@ApiResponse(responseCode = "400", description = "Error en la solicitud",
			content = @Content)
	})
	@GetMapping //Obtener una lista de todos los usuarios
	public ResponseEntity<Page<ClienteDTO>> obtenerClientes(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
			){
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("idCliente"));
		
		Page<ClienteDTO> totalClientes = clienteService.obtenerTotalClientes(pageable);
		return ResponseEntity.ok(totalClientes);
	}
	
	
	@GetMapping("/{id}") //Obtener detalles de un cliente específico
	public ResponseEntity<?> obtenerClientePorId(@PathVariable int id){
		return ResponseEntity.ok("");
	}
	
	
	@PutMapping("/{id}") //Actualizar información de un cliente específico
	public ResponseEntity<?> editarClientePorId(@PathVariable int id){
		return ResponseEntity.ok("");
	}
	
	
	@DeleteMapping("/{id}") //Eliminar un cliente específico.
	public ResponseEntity<?> eliminarClientePorId(@PathVariable int id){
		return ResponseEntity.ok("");
	}
	
}
