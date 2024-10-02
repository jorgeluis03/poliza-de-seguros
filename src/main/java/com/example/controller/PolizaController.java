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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.dto.PolizaSolicitudDTO;
import com.example.exceptions.ClienteNoEncontradoException;
import com.example.exceptions.SolicitudPolizaNoEncontradoException;
import com.example.service.PolizaService;
import com.example.service.PolizaSolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/v1/polizas")
public class PolizaController {
	
	@Autowired
	PolizaSolicitudService polizaSolicitudService;
	@Autowired
	PolizaService polizaService;
	
	
	@PostMapping("/solicitud")
	public ResponseEntity<?> crearPolizaSolicitud(@RequestBody PolizaSolicitudDTO polizaSolicitudDTO) throws ClienteNoEncontradoException{
		
		int idPolizaSolicitud = polizaSolicitudService.crearSolicitudPoliza(polizaSolicitudDTO);
		
		Map<String, Integer> responseBody = new HashMap<>();
	    responseBody.put("id", idPolizaSolicitud);
	    
		return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
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
	public ResponseEntity<Page<PolizaSolicitudDTO>> obtenerSolicitudPolizas(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
			){
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("idSolicitud"));
		
		
		 Page<PolizaSolicitudDTO> obtenerTotalSolicitudPoliza = polizaSolicitudService.obtenerTotalSolicitudPoliza(pageable);
		
		return ResponseEntity.ok(obtenerTotalSolicitudPoliza);
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
		
		Map<String, Object> responseMap = polizaService.modificarSolicitudPoliza(id, estado);
		
		return ResponseEntity.ok(responseMap);
	}
	
}
