package com.example.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Auto;
import com.example.entity.PolizaAuto;
import com.example.entity.PolizaSolicitud;
import com.example.enume.TipoPoliza;
import com.example.interfaces.NumeroPoliza;
import com.example.interfaces.PolizaStrategy;
import com.example.repository.AutoRepository;
import com.example.repository.PolizaAutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;

@Service
public class PolizaAutoStrategy implements PolizaStrategy, NumeroPoliza{
	
	@Autowired
	AutoRepository autoRepository;
	
	@Autowired
	PolizaAutoRepository polizaAutoRepository;

	@Override
	@Transactional
	public void crearPoliza(PolizaSolicitud polizaSolicitud) {
		
		try {
			
			String detallesAutoJSON = polizaSolicitud.getDetalles();
			System.out.println("llego hasta acaaaaa");
			
			ObjectMapper objectMapper = new ObjectMapper();
			Auto auto = objectMapper.readValue(detallesAutoJSON, Auto.class);
			auto.setCliente(polizaSolicitud.getCliente());
			autoRepository.save(auto);
			
			
			PolizaAuto newPolizaAuto = new PolizaAuto();
			newPolizaAuto.setCliente(polizaSolicitud.getCliente());
			newPolizaAuto.setAuto(auto);
			newPolizaAuto.setNumeroPoliza(generatePolicyNumber(TipoPoliza.AUTO));
			polizaAutoRepository.save(newPolizaAuto);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String generatePolicyNumber(TipoPoliza tipoPoliza) {
		return NumeroPoliza.super.generatePolicyNumber(tipoPoliza);
	}
	
	
	
}
