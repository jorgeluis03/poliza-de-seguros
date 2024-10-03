package com.example.clases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Inmueble;
import com.example.entity.PolizaInmueble;
import com.example.entity.PolizaSolicitud;
import com.example.enume.TipoPoliza;
import com.example.interfaces.NumeroPoliza;
import com.example.interfaces.PolizaStrategy;
import com.example.repository.InmuebleRepository;
import com.example.repository.PolizaInmuebleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class PolizaInmuebleStrategy implements PolizaStrategy, NumeroPoliza{
	
	@Autowired
	InmuebleRepository inmuebleRepository;
	
	@Autowired
	PolizaInmuebleRepository polizaInmuebleRepository;
	
	@Override
	@Transactional
	public void crearPoliza(PolizaSolicitud polizaSolicitud) {
		
		try {
			
			String detallesAutoJSON = polizaSolicitud.getDetalles();
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			//guardar en la tabla Inmuble
			Inmueble inmueble = objectMapper.readValue(detallesAutoJSON, Inmueble.class);
			inmueble.setCliente(polizaSolicitud.getCliente());
			inmuebleRepository.save(inmueble);
			
			//Guardar en la tabla PolizaInmueble
			PolizaInmueble polizaInmueble = new PolizaInmueble();
			polizaInmueble.setCliente(polizaSolicitud.getCliente());
			polizaInmueble.setInmueble(inmueble);
			polizaInmueble.setNumeroPoliza(generatePolicyNumber(TipoPoliza.INMUEBLE));
			
			polizaInmuebleRepository.save(polizaInmueble);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String generatePolicyNumber(TipoPoliza tipoPoliza) {
		// TODO Auto-generated method stub
		return NumeroPoliza.super.generatePolicyNumber(tipoPoliza);
	}
	
	

}
