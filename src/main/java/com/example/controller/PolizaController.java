package com.example.controller;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.dto.PolizaSolicitudDTO;
import com.example.exceptions.ClienteNoEncontradoException;
import com.example.exceptions.SolicitudPolizaNoEncontradoException;
import com.example.exceptions.UsuarioNoEncontradoException;
import com.example.service.PolizaSolicitudService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("v1/api/polizas")
public class PolizaController {
	
	@Autowired
	PolizaSolicitudService polizaSolicitudService;
	
	
	//Pólizas
	
	@PostMapping //Crear una nueva póliza
	public ResponseEntity<?> crearPoliza (){
		return ResponseEntity.ok("");
	}
	
	@GetMapping //Obtener una lista de todas las pólizas
	public ResponseEntity<?> obtenerPolizas(@RequestParam (defaultValue = "0") int page,
											@RequestParam(defaultValue = "10") int size){
		return ResponseEntity.ok("");
	}
	
	@GetMapping("/{id}") //Obtener detalles de una póliza específica
	public ResponseEntity<?> obtenerPolizasPorId(@PathVariable int id){
		return ResponseEntity.ok("");
	}
	
	@PutMapping("/{id}") //Actualizar información de una póliza específica
	public ResponseEntity<?> editarPolizasPorId(@PathVariable int id){
		return ResponseEntity.ok("");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarPolizasPorId(@PathVariable int id){
		return ResponseEntity.ok("");
	}
	
	// ===================================================================================
	// Detalles de Pólizas de Autos
	
	@PostMapping("/{idPoliza}/detalles-auto") //Crear detalles específicos para una póliza de auto.
	public ResponseEntity<?> crearPolizaAuto(@PathVariable int idPoliza){
		return ResponseEntity.ok("");
	}
	
	@GetMapping("/{idPoliza}/detalles-auto") //Obtener detalles de la póliza de auto específica.
	public ResponseEntity<?> obtenerPolizaAutoPorId(@PathVariable int idPoliza){
		return ResponseEntity.ok("");
	}
	
	@PutMapping("/{idPoliza}/detalles-auto") //Actualizar detalles de la póliza de auto específica.
	public ResponseEntity<?> editarPolizaAutoPorId(@PathVariable int idPoliza){
		return ResponseEntity.ok("");
	}
	
	@DeleteMapping("/{idPoliza}/detalles-auto") // Eliminar detalles de la póliza de auto.
	public ResponseEntity<?> eliminarPolizasAutoPorId(@PathVariable int idPoliza){
		return ResponseEntity.ok("");
	}
	
	// ===================================================================================
	// Detalles de Pólizas de Inmuebles

	@PostMapping("/{idPoliza}/detalles-inmueble") // Crear detalles específicos para una póliza de inmueble.
	public ResponseEntity<?> crearPolizaInmueble(@PathVariable int idPoliza){
	    return ResponseEntity.ok("");
	}

	@GetMapping("/{idPoliza}/detalles-inmueble") // Obtener detalles de la póliza de inmueble específica.
	public ResponseEntity<?> obtenerPolizaInmueblePorId(@PathVariable int idPoliza){
	    return ResponseEntity.ok("");
	}

	@PutMapping("/{idPoliza}/detalles-inmueble") // Actualizar detalles de la póliza de inmueble específica.
	public ResponseEntity<?> editarPolizaInmueblePorId(@PathVariable int idPoliza){
	    return ResponseEntity.ok("");
	}

	@DeleteMapping("/{idPoliza}/detalles-inmueble") // Eliminar detalles de la póliza de inmueble.
	public ResponseEntity<?> eliminarPolizaInmueblePorId(@PathVariable int idPoliza){
	    return ResponseEntity.ok("");
	}

	// ===================================================================================
	
	@PostMapping("/solicitud")
	public ResponseEntity<?> crearPolizaSolicitud(@RequestBody PolizaSolicitudDTO polizaSolicitudDTO) throws UsuarioNoEncontradoException, JsonProcessingException{
		
		HashMap<String, Object> response = polizaSolicitudService.crearSolicitudPoliza(polizaSolicitudDTO);
		
	 
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	@Operation(summary = "Listado de las solicitudes de polizas")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna la lista de Solicitudes de Polizas segun la paginación",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "404", description = " Solicitudes de Polizas no encontrados",
					content = @Content),
			@ApiResponse(responseCode = "400", description = "Error en la solicitud",
			content = @Content)
	})
	@GetMapping("/solicitud")
	public ResponseEntity<Page<PolizaSolicitudDTO>> obtenerSolicitudPolizas(@RequestParam(defaultValue = "0") int page,
																			@RequestParam(defaultValue = "10") int size) throws JsonProcessingException{
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("idSolicitud"));
		
		Page<PolizaSolicitudDTO> SolicitudPolizas = polizaSolicitudService.obtenerSolicitudPoliza(pageable);
		
		return ResponseEntity.ok(SolicitudPolizas);
	}
	
	
	@Operation(summary = "Modificar el estado de la solicitud de una determinada poliza")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se modificó el estado de una solicitud correctamente",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "404", description = " Solicitud de Poliza no encontrada",
					content = @Content),
			@ApiResponse(responseCode = "400", description = "Error en la solicitud",
			content = @Content)
	})
	@PatchMapping("/solicitud/{id}")
	public ResponseEntity<?> modificarSolicitudPolizas(
			@PathVariable int id ,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Payload para modificar el estado de una solicitud de poliza",
			        required = true,
			        content = @Content(
	        	            mediaType = "application/json",
	        	            schema = @Schema(
	        	                example = "{\"estado\": \"APROBADO\"}",
	        	                description = "Estado de la solicitud, valores posibles: 'PENDIENTE', 'APROBADO', 'RECHAZADO'."
	        	            )
		        		)
					)
			@RequestBody Map<String, String> requestBody) throws IllegalArgumentException, SolicitudPolizaNoEncontradoException{
		
		String estado = requestBody.get("estado");
		
		//Map<String, Object> responseMap = polizaService.modificarSolicitudPoliza(id, estado);
		
		return ResponseEntity.ok("");
	}
	
}
