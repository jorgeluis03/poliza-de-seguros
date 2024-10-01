package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.UsuarioDTO;
import com.example.entity.Usuario;
import com.example.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Transactional
	public void crearUsuario(Usuario usuario) {
		String contrasenaHash = encoderContrasena(usuario.getContrasena());
		usuario.setContrasena(contrasenaHash);
		usuario.setEstado(1);
		usuarioRepository.save(usuario);
	}
	
	@Transactional
	public List<UsuarioDTO> obtenerUsuarios(){
		List<Usuario> usuarios = usuarioRepository.findAll();
		return convertirUsuariosADTOs(usuarios);
	}
	
	public List<UsuarioDTO> convertirUsuariosADTOs(List<Usuario> usuarios){
		List<UsuarioDTO> usuarioDTOs = new ArrayList<UsuarioDTO>();
		for (Usuario usuario : usuarios) {
			UsuarioDTO newUsuarioDTO = new UsuarioDTO(
											usuario.getIdUsuario(), 
											usuario.getNombreUsuario(), 
											usuario.getCorreo(), 
											usuario.getEstado());
			usuarioDTOs.add(newUsuarioDTO);
		}
		return usuarioDTOs;
	}
	
	public String encoderContrasena(String contrasena) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(contrasena);
	}
}
