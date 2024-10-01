package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.ClienteDTO;
import com.example.entity.Cliente;
import com.example.entity.Usuario;
import com.example.exceptions.UsuarioNoEncontradoException;
import com.example.repository.ClienteRepository;
import com.example.repository.UsuarioRepository;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Transactional
	public void crearCliente(ClienteDTO clienteDTO) {
		Cliente newCliente = convertirDTOAEntity(clienteDTO);
		clienteRepository.save(newCliente);
	}
	
	@Transactional
	public List<ClienteDTO> obtenerTotalClientes(){
		List<Cliente> clientes =  clienteRepository.findAll();
		return convertirEntityADTO(clientes);
	}
	
	public Cliente convertirDTOAEntity(ClienteDTO clienteDto) {
		Usuario usuario = usuarioRepository.findById(clienteDto.getIdUsuario())
				.orElseThrow(()->new UsuarioNoEncontradoException("No se encontró el usuario con el ID: "+clienteDto.getIdUsuario()));

		Cliente newCliente = new Cliente();
		newCliente.setUsuario(usuario);
		newCliente.setDni(clienteDto.getDni());
		newCliente.setNombre(clienteDto.getNombre());
		newCliente.setApellido(clienteDto.getApellido());
		newCliente.setTelefono(clienteDto.getTelefono());
		newCliente.setDireccion(clienteDto.getDireccion());
		newCliente.setEstado(1);
		
		return newCliente;
	}
	
	public List<ClienteDTO> convertirEntityADTO(List<Cliente> clientes) {
		List<ClienteDTO> clienteDTOs = new ArrayList<>();
		
		
		for (Cliente cliente : clientes) {
			ClienteDTO clienteDTO = new ClienteDTO(
										cliente.getUsuario().getIdUsuario(), 
										cliente.getDni(), 
										cliente.getNombre(), 
										cliente.getApellido(), 
										cliente.getTelefono(), 
										cliente.getDireccion(),
										cliente.getEstado());								
			clienteDTOs.add(clienteDTO);
		}
		
		return clienteDTOs;
	}
}
