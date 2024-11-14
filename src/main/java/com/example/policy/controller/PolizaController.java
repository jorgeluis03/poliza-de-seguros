package com.example.policy.controller;
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

import com.example.policy.dto.DetallesAutoDTO;
import com.example.policy.dto.PolizaDTO;
import com.example.policy.dto.DetallesInmuebleDTO;
import com.example.policy.dto.PolizaSolicitudDTO;
import com.example.policy.exception.PolizaNoEncontradaException;
import com.example.policy.exception.SolicitudPolizaNoEncontradoException;
import com.example.user.exception.UsuarioNoEncontradoException;
import com.example.policy.service.PolizaService;
import com.example.policy.service.PolizaSolicitudService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.example.common.response.ApiResult;

@RestController
@RequestMapping("v1/polices")
public class PolizaController {
	
	@Autowired
	PolizaSolicitudService polizaSolicitudService;
	
	@Autowired
	PolizaService polizaService;

	
	@PostMapping
    @Operation(summary = "Crear una nueva póliza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Póliza creada correctamente",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> crearPoliza(@RequestBody PolizaDTO polizaDTO) throws IllegalArgumentException {
        ApiResult<?> apiResult = polizaService.crearPoliza(polizaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResult);
    }

    @GetMapping
    @Operation(summary = "Obtener una lista de todas las pólizas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna la lista de pólizas según la paginación",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> obtenerPolizas(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("idPoliza"));
        Page<PolizaDTO> polizasDtoPage = polizaService.obtenerPolizas(pageable);
        return ResponseEntity.ok(polizasDtoPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalles de una póliza específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna los detalles de la póliza solicitada",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Póliza no encontrada")
    })
    public ResponseEntity<?> obtenerPolizaPorId(@PathVariable Integer id) throws PolizaNoEncontradaException {
        ApiResult<?> apiResult = polizaService.obtenerPolizaPorId(id);
        return ResponseEntity.ok(apiResult);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar información de una póliza específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Póliza actualizada correctamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Póliza no encontrada")
    })
    public ResponseEntity<?> editarPolizasPorId(@PathVariable Integer id, @RequestBody PolizaDTO polizaDTO) throws PolizaNoEncontradaException {
        ApiResult<?> apiResult = polizaService.editarPoliza(id, polizaDTO);
        return ResponseEntity.ok(apiResult);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una póliza específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Póliza eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Póliza no encontrada")
    })
    public ResponseEntity<?> borrarPoliza(@PathVariable Integer id) throws PolizaNoEncontradaException {
        polizaService.borrarPoliza(id);
        return ResponseEntity.noContent().build();
    }
	
	// ===================================================================================
	// Detalles de Pólizas de Autos
	
    @PostMapping("/{idPoliza}/detalles-auto")
    @Operation(summary = "Crear detalles específicos para una póliza de auto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Detalles de la póliza de auto creados correctamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Póliza no encontrada")
    })
    public ResponseEntity<?> crearPolizaAuto(@PathVariable int idPoliza, @RequestBody DetallesAutoDTO detallesAutoDTO)  throws PolizaNoEncontradaException {
        ApiResult<?> apiResult = polizaService.crearDetallesAuto(idPoliza, detallesAutoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResult);
    }

    @GetMapping("/{idPoliza}/detalles-auto")
    @Operation(summary = "Obtener detalles de la póliza de auto específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna los detalles de la póliza de auto solicitada",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Póliza no encontrada")
    })
    public ResponseEntity<?> obtenerPolizaAutoPorId(@PathVariable int idPoliza) throws PolizaNoEncontradaException {
        ApiResult<?> apiResult = polizaService.obtenerDetallesAuto(idPoliza);
        return ResponseEntity.ok(apiResult);
    }

    @PutMapping("/{idPoliza}/detalles-auto")
    @Operation(summary = "Actualizar detalles de la póliza de auto específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles de la póliza de auto actualizados correctamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Póliza no encontrada")
    })
    public ResponseEntity<?> editarPolizaAutoPorId(@PathVariable int idPoliza, @RequestBody DetallesAutoDTO detallesAutoDTO) throws PolizaNoEncontradaException {
        ApiResult<?> apiResult = polizaService.editarDetallesAuto(idPoliza, detallesAutoDTO);
        return ResponseEntity.ok(apiResult);
    }

    @DeleteMapping("/{idPoliza}/detalles-auto")
    @Operation(summary = "Eliminar detalles de la póliza de auto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Detalles de la póliza de auto eliminados correctamente"),
            @ApiResponse(responseCode = "404", description = "Póliza no encontrada")
    })
    public ResponseEntity<?> eliminarPolizasAutoPorId(@PathVariable int idPoliza) throws PolizaNoEncontradaException{
        polizaService.eliminarDetallesAuto(idPoliza);
        return ResponseEntity.noContent().build();
    }
	
	// ===================================================================================
	// Detalles de Pólizas de Inmuebles

    @Operation(summary = "Crear detalles específicos para una póliza de inmueble")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Detalles de la póliza de inmueble creados correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud"),
            @ApiResponse(responseCode = "404", description = "Póliza no encontrada")
    })
    @PostMapping("/{idPoliza}/detalles-inmueble")
    public ResponseEntity<?> crearPolizaInmueble(@PathVariable int idPoliza, @RequestBody DetallesInmuebleDTO detallesInmuebleDTO) throws PolizaNoEncontradaException {
        ApiResult<?> apiResult = polizaService.crearPolizaInmueble(idPoliza, detallesInmuebleDTO);
        return ResponseEntity.status(201).body(apiResult);
    }

    @Operation(summary = "Obtener detalles de la póliza de inmueble específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles de la póliza de inmueble obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Póliza de inmueble no encontrada")
    })
    @GetMapping("/{idPoliza}/detalles-inmueble")
    public ResponseEntity<?> obtenerDetallesInmueble(@PathVariable int idPoliza) throws PolizaNoEncontradaException {
        ApiResult<?> apiResult = polizaService.obtenerDetallesInmueble(idPoliza);
        return ResponseEntity.status(200).body(apiResult);
    }

    @Operation(summary = "Actualizar detalles de la póliza de inmueble específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles de la póliza de inmueble actualizados correctamente"),
            @ApiResponse(responseCode = "404", description = "Póliza de inmueble no encontrada")
    })
    @PutMapping("/{idPoliza}/detalles-inmueble")
    public ResponseEntity<?> editarDetallesInmueble(@PathVariable int idPoliza, @RequestBody DetallesInmuebleDTO detallesInmuebleDTO) throws PolizaNoEncontradaException {
        ApiResult<?> apiResult = polizaService.editarDetallesInmueble(idPoliza, detallesInmuebleDTO);
        return ResponseEntity.status(200).body(apiResult);
    }

    @Operation(summary = "Eliminar detalles de la póliza de inmueble")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Detalles de la póliza de inmueble eliminados correctamente"),
            @ApiResponse(responseCode = "404", description = "Póliza de inmueble no encontrada")
    })
    @DeleteMapping("/{idPoliza}/detalles-inmueble")
    public ResponseEntity<?> eliminarDetallesInmueble(@PathVariable int idPoliza) throws PolizaNoEncontradaException {
        polizaService.eliminarDetallesInmueble(idPoliza);
        return ResponseEntity.noContent().build();
    }

	// ===================================================================================
	
	@PostMapping("/solicitud")
	public ResponseEntity<?> crearPolizaSolicitud(@RequestBody PolizaSolicitudDTO polizaSolicitudDTO) throws UsuarioNoEncontradoException, JsonProcessingException{
		 ApiResult<PolizaSolicitudDTO> apiResult = polizaSolicitudService.crearSolicitudPoliza(polizaSolicitudDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(apiResult);
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
	public ResponseEntity<?> obtenerSolicitudPolizas(@RequestParam(defaultValue = "0") int page,
																			@RequestParam(defaultValue = "10") int size){
		Pageable pageable = PageRequest.of(page, size, Sort.by("idSolicitud"));
		Page<PolizaSolicitudDTO> SolicitudPolizas = polizaSolicitudService.obtenerSolicitudPoliza(pageable);
		
		return ResponseEntity.ok(SolicitudPolizas);
	}
	
	@GetMapping("/solicitud/{id}")
	public ResponseEntity<?> obtenerSolicitudPolizasPorId(@PathVariable int id){
		ApiResult<PolizaSolicitudDTO> apiResult= polizaSolicitudService.obtenerSolicitudPolizaPorId(id);
		return ResponseEntity.ok(apiResult);
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
			@RequestBody PolizaSolicitudDTO polizaSolicitudDTO) throws IllegalArgumentException, SolicitudPolizaNoEncontradoException{
		
		ApiResult<?> apiResult = polizaSolicitudService.modificarSolicitudPoliza(id, polizaSolicitudDTO);
		return ResponseEntity.ok(apiResult);
	}
	
	
	
}
