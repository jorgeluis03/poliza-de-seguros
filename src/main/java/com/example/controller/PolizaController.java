package com.example.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.dto.PolizaSolicitudDTO;
import com.example.exceptions.ClienteNoEncontradoException;
import com.example.service.PolizaService;
import com.example.service.PolizaSolicitudService;

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
	
	@GetMapping("/solicitud")
	public ResponseEntity<?> obtenerSolicitudPolizas(){
		
		List<PolizaSolicitudDTO> listaDtos = polizaSolicitudService.obtenerTotalSolicitudPoliza();
		
		return ResponseEntity.ok(listaDtos);
	}
	
	@PatchMapping("/solicitud/{id}")
	public ResponseEntity<?> modificarSolicitudPolizas(@PathVariable int id ,@RequestBody Map<String, String> requestBody) throws IllegalArgumentException{
		
		String estado = requestBody.get("estado");
		
	    if (estado == null) {
	        throw new IllegalArgumentException("El estado no puede ser nulo");
	    }
	    
		Map<String, Object> responseMap = polizaService.modificarSolicitudPoliza(id, estado);
		
		return ResponseEntity.ok(responseMap);
	}
	
}
