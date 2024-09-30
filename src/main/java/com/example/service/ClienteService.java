package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.ClienteDTO;
import com.example.entity.Persona;
import com.example.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;
	
	public List<Persona> getAllClients() {
		List<Persona> lista_clientes = clienteRepository.findAllClients();
		return lista_clientes;
	}
	
	public void saveClient(ClienteDTO clienteDTO) {
		String password = encodePassword(clienteDTO.getPassword());
		Persona newCliente = new Persona(clienteDTO.getNombre(), 
												clienteDTO.getEmail(), 
												clienteDTO.getTelefono(), 
												clienteDTO.getDireccion(), 
												password,
												2);
		newCliente.setEstado(1);
		clienteRepository.save(newCliente);
	}
	
	public String encodePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
}
