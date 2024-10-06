package com.example.service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.UsuarioDTO;
import com.example.entity.Usuario;
import com.example.exceptions.UsuarioNoEncontradoException;
import com.example.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Transactional
	public Map<String, Object> crearUsuario(Usuario usuario) {
		Map<String, Object>  responseMap = new HashMap<>();
		String contrasenaHash = encoderContrasena(usuario.getContrasena());
		usuario.setContrasena(contrasenaHash);
		usuario.setEstado(1);
		
		usuarioRepository.save(usuario);
		
		responseMap.put("id", usuario.getIdUsuario());
		responseMap.put("mensaje", "Usuario creado exitosamente");
		
		return responseMap;
	}
	
	@Transactional
	public Page<UsuarioDTO> obtenerUsuarios(Pageable pageable){
		
		Page<Usuario> listaUsuarios = usuarioRepository.findAll(pageable);
		Page<UsuarioDTO> usuariosDTOs = listaUsuarios.map((usuario) -> convertirEntityADTO(usuario));
		
		return usuariosDTOs;
	}
	
	
	@Transactional
	public UsuarioDTO obtenerUsuarioPorId(int id) {
		Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
		
		if(optionalUsuario.isPresent()) {
			Usuario usuario = optionalUsuario.get();
			return convertirEntityADTO(usuario);
		}else {
			throw new UsuarioNoEncontradoException("No se encontró el usuario con ID: "+id);
		}
	}
	
	
	@Transactional
	public Map<String, Object> editarUsuarioPorId(int id, Usuario usuario){
		Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
		Map<String, Object>  responseMap = new HashMap<>();
		
		if(optionalUsuario.isPresent()) {
			
			usuarioRepository.save(usuario);
			
			responseMap.put("id", usuario.getIdUsuario());
			responseMap.put("mensaje", "Usuario editado exitosamente");
			
			return responseMap;
			
		}else {
			throw new UsuarioNoEncontradoException("No se encontró el usuario con ID: "+id);
		}
	}
	
	
	public UsuarioDTO convertirEntityADTO(Usuario usuario){
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(usuario.getIdUsuario());
		usuarioDTO.setNomUsuario(usuario.getNombreUsuario());
		usuarioDTO.setCorreo(usuario.getCorreo());
		
		return usuarioDTO;
	}
	
	public String encoderContrasena(String contrasena) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(contrasena);
	}
}
