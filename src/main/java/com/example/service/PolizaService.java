package com.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.PolizaDto;
import com.example.entity.PolizaEntity;
//import com.example.repository.PolizaRepository;

@Service
public class PolizaService {
	
	@Autowired
	//PolizaRepository polizaRepository;
	
	public List<PolizaDto> getPolizas() {
		//List<PolizaEntity> list_polizaEntity = polizaRepository.getAllPolizas();
		
		List<PolizaDto> listaPolizaDtos = new ArrayList<PolizaDto>();
		PolizaDto polizaDto1 = new PolizaDto(1,"Lima");
		PolizaDto polizaDto2 = new PolizaDto(2,"Ica");
		PolizaDto polizaDto3= new PolizaDto(3,"Arequipa");
		listaPolizaDtos.add(polizaDto1);
		listaPolizaDtos.add(polizaDto2);
		listaPolizaDtos.add(polizaDto3);
		return listaPolizaDtos;
	}
	
	private List<PolizaDto> convertirListaEntidadADto(List<PolizaEntity> list_polizaEntity) {
		List<PolizaDto> list_polizaDto = new ArrayList<PolizaDto>();
		for (PolizaEntity polizaEntity : list_polizaEntity) {
			PolizaDto polizaDto = new PolizaDto(1,"Lima");
			list_polizaDto.add(polizaDto);
		}
		return list_polizaDto;
	}
}
