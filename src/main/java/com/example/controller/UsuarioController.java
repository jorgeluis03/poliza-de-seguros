package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UsuarioDTO;
import com.example.entity.Usuario;
import com.example.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("v1/usuarios")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Operation(summary = "Crear un nuevo usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario creado con éxito",
					content = {@Content(mediaType = "application/json",
					schema = @Schema(
								description = "Ejemplo de creacion de usuario",
								example = "{\"nombreUsuario\": \"miguel123\", \"contrasena\": \"miguel12345\", \"correo\":\"miguel@example@com\"}"
									))}),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado",
					content = @Content)
	})
	@PostMapping
	public ResponseEntity<?> crearUsuario(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Cuerpo de la solicitud para crear un usuario",
			        required = true,
			        content = @Content(
	        	            mediaType = "application/json",
	        	            schema = @Schema(
	        	                example = "{\"nombreUsuario\": \"miguel123\", \"contrasena\": \"miguel12345\", \"correo\":\"miguel@example@com\"}"
	        	            )
		        		)
					)
			@RequestBody Usuario usuario){
		usuarioService.crearUsuario(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body("Usuario Creado");
	}
	
	
	
	
	
	@Operation(summary = "Listado de usuarios")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna la lista de usuarios segun la paginación",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "404", description = "Usuarios no encontrados",
					content = @Content),
			@ApiResponse(responseCode = "400", description = "Error en la solicitud",
			content = @Content)
	})
	@GetMapping
	public ResponseEntity<Page<UsuarioDTO>> obtenerUsuarios(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
			){
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("idUsuario"));
		Page<UsuarioDTO> listaUsuariosDTOPage = usuarioService.obtenerUsuarios(pageable);
		
		return ResponseEntity.ok(listaUsuariosDTOPage);
	}
	
	
}
