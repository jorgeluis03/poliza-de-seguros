package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Page<ClienteDTO> obtenerTotalClientes(Pageable pageable){
		
		Page<Cliente> listaPage = clienteRepository.findAll(pageable);
		
		return convertirEntityADTO(listaPage);
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
	
	public Page<ClienteDTO> convertirEntityADTO(Page<Cliente> listaPage) {
		return listaPage.map(cliente ->
				new ClienteDTO(
					cliente.getUsuario().getIdUsuario(), 
					cliente.getDni(), 
					cliente.getNombre(), 
					cliente.getApellido(), 
					cliente.getTelefono(), 
					cliente.getDireccion(),
					cliente.getEstado())	
				);
	}
}
