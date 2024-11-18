package com.example.policy.controller;
import com.example.user.exception.UsuarioNoEncontradoException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.example.policy.dto.PolizaDTO;
import com.example.policy.exception.PolizaNoEncontradaException;
import com.example.policy.service.PolizaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.example.common.response.ApiResult;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("v1/polices")
public class PolizaController {
	
	@Autowired
	PolizaService polizaService;
	
	@PostMapping
    @Operation(summary = "Crear una nueva póliza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Póliza creada correctamente",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> crearPoliza(@RequestBody PolizaDTO polizaDTO) throws IllegalArgumentException, MessagingException, IOException {
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

    @GetMapping("by-user")
    public ResponseEntity<?> obtenerPolizaPorUsuario(
                                                    @RequestParam("usuario") String usuario,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) throws UsernameNotFoundException {
        Pageable pageable = PageRequest.of(page, size, Sort.by("idPoliza"));
        Page<PolizaDTO> polizasByUsuario = polizaService.obtenerPolizaPorUsuario(usuario, pageable);
        return ResponseEntity.ok(polizasByUsuario);
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
    @PatchMapping("/{idPoliza}")
    public ResponseEntity<?> modificarEstadoPolizas(
            @PathVariable int idPoliza ,
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
            @RequestBody Map<String, Object> body) throws IllegalArgumentException, PolizaNoEncontradaException{
        System.out.println("datos que ser reciben: "+body);
        ApiResult<?> apiResult = polizaService.modificarEstadoPoliza(idPoliza, body);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/search")
    public ResponseEntity<?> buscarPoliza(@RequestParam(defaultValue = "") String numeroPoliza,
                                          @RequestParam(defaultValue = "") String tipoPoliza,
                                          @RequestParam(defaultValue = "") String usuarioCorreo) throws PolizaNoEncontradaException, UsuarioNoEncontradoException {
        List<PolizaDTO> polizaDTOS = polizaService.buscarPoliza(numeroPoliza, tipoPoliza, usuarioCorreo);
        return ResponseEntity.ok(polizaDTOS);
    }
	
	// ===================================================================================
	// Detalles de Pólizas de Autos
    @GetMapping("/autos/{idPolicy}")
    @Operation(summary = "Obtener detalles de la póliza de auto específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna los detalles de la póliza de auto solicitada",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Póliza no encontrada")
    })
    public ResponseEntity<?> obtenerPolizaAutoPorId(@PathVariable int idPolicy) throws PolizaNoEncontradaException {
        ApiResult<?> apiResult = polizaService.obtenerDetallesAuto(idPolicy);
        return ResponseEntity.ok(apiResult);
    }

	// ===================================================================================
	// Detalles de Pólizas de Inmuebles
    @Operation(summary = "Obtener detalles de la póliza de inmueble específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles de la póliza de inmueble obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Póliza de inmueble no encontrada")
    })
    @GetMapping("/property/{idPolicy}")
    public ResponseEntity<?> obtenerDetallesInmueble(@PathVariable int idPolicy) throws PolizaNoEncontradaException {
        ApiResult<?> apiResult = polizaService.obtenerDetallesInmueble(idPolicy);
        return ResponseEntity.status(200).body(apiResult);
    }

    // ===================================================================================
    // Detalles de Pólizas de Inmuebles
    @GetMapping("/phone/{idPolicy}")
    public ResponseEntity<?> obtenerDetallesCelular(@PathVariable int idPolicy) throws PolizaNoEncontradaException {
        ApiResult<?> apiResult = polizaService.obtenerDetallesCelular(idPolicy);
        return ResponseEntity.status(200).body(apiResult);
    }

}
