package com.example.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UsuarioDTO;
import com.example.entity.Usuario;
import com.example.exceptions.RolNoEncontradoException;
import com.example.exceptions.UsuarioNoEncontradoException;
import com.example.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/api/usuarios")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Operation(summary = "Crear un nuevo usuario")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "Usuario creado con éxito",
	        content = {@Content(mediaType = "application/json",
	            schema = @Schema(
	                description = "Ejemplo de creación de usuario",
	                example = "{\"nombreUsuario\": \"miguel123\", \"contrasena\": \"miguel12345\", \"correo\":\"miguel@example.com\"}"
	            ))}),
	    @ApiResponse(responseCode = "400", description = "Error en la solicitud: Parámetros inválidos o mal formateados",
	        content = @Content),
	    @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
	        content = @Content)
	})
	@PostMapping //Crear un nuevo usuario
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
			@Valid @RequestBody Usuario usuario, BindingResult bindingResult){
		
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
		}
		
		Map<String, Object> crearUsuario = usuarioService.crearUsuario(usuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(crearUsuario);
	}
	
	
	
	@Operation(summary = "Listar todos los usuarios")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Operación exitosa: Retorna la lista de usuarios según la paginación", 
	        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))}),
	    @ApiResponse(responseCode = "404", description = "Usuarios no encontrados: No se encontró ningún usuario en la página solicitada", 
	        content = @Content),
	    @ApiResponse(responseCode = "400", description = "Error en la solicitud: Parámetros inválidos o mal formateados", 
	        content = @Content)
	})
	@GetMapping //Listar todos los usuarios
	public ResponseEntity<?> obtenerUsuarios(@RequestParam(defaultValue = "0") int page,
											@RequestParam(defaultValue = "10") int size){
		Pageable pageable = PageRequest.of(page, size, Sort.by("idUsuario"));
		Page<UsuarioDTO> obtenerUsuarios = usuarioService.obtenerUsuarios(pageable);
		
		return ResponseEntity.ok(obtenerUsuarios);
		
	}
	
	
	
	@Operation(summary = "Obtener detalles de un usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",description = "Operación exitosa: Detalles del usuario obtenidos",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))}),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado: El ID de usuario no existe",
					content = {@Content(mediaType = "application/json", 
										schema = @Schema(implementation = ErrorResponse.class),
										examples = {@ExampleObject(value = """
									                                        {
								                                            "status": 404,
								                                            "error": "Not Found",
								                                            "message": "El usuario con el ID 123 no existe"
								                                        	}
								                                        """)}
										)
								}),
		    @ApiResponse(responseCode = "400", description = "Error en la solicitud: Parámetros inválidos o mal formateados",
		    		content = @Content)
	})
	@GetMapping("/{id}") //Obtener detalles de un usuario
	public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable int id) throws UsuarioNoEncontradoException{
		UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorId(id);
		return ResponseEntity.ok(usuarioDTO);
	}
	
	
	
	
	@Operation(summary = "Actualizar un usuario")
	@ApiResponses(value = {
			@ApiResponse(	responseCode = "200",description = "Operación exitosa: Información del usuario actualizados",
							content = {@Content(mediaType = "application/json", schema = @Schema(example = "{\"nomUsuario\": \"miguel123\", "
																											+ "\"correo\": \"miguel@example.com\", \"nombre\": \"Miguel\", \"apellido\": \"Pérez\", "
																											+ "\"dni\": \"12345678\", \"telefono\": \"654321123\", \"direccion\": \"Calle Falsa 123\", "
																											+ "\"idRol\": 0}"))}),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado: El ID de usuario no existe",
					content = {@Content(mediaType = "application/json", 
										schema = @Schema(implementation = ErrorResponse.class),
										examples = {@ExampleObject(value = """
									                                        {
								                                            "status": 404,
								                                            "error": "Not Found",
								                                            "message": "El usuario con el ID 123 no existe"
								                                        	}
								                                        """)}
										)
								}),
		    @ApiResponse(responseCode = "400", description = "Error en la solicitud: Parámetros inválidos o mal formateados",
		    		content = @Content)
	})
	@PutMapping("/{id}") //Actualizar un usuario
	public ResponseEntity<?> editarUsuarioPorId(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Cuerpo de la solicitud para editar un usuario",
			        required = true,
			        content = @Content(	mediaType = "application/json",
	        	            			schema = @Schema(example = "{\"nomUsuario\": \"miguel123\", \"correo\": \"miguel@example.com\", \"nombre\": \"Miguel\", \"apellido\": \"Pérez\", \"dni\": \"12345678\", \"telefono\": \"654321123\", \"direccion\": \"Calle Falsa 123\", \"idRol\": 0}")
			        					)
					)
			
			@PathVariable int id, @RequestBody UsuarioDTO usuarioDTO) throws UsuarioNoEncontradoException, RolNoEncontradoException{
		Map<String, Object> response = usuarioService.editarUsuarioPorId(id, usuarioDTO);
		return ResponseEntity.ok(response); 
	}
	
	@DeleteMapping("/{id}") //Eliminar un usuario
	public ResponseEntity<?> eliminarUsuarioPorId(@PathVariable int id) throws UsuarioNoEncontradoException{
		usuarioService.eliminarUsuario(id);
		return ResponseEntity.noContent().build();
	}
	
}
