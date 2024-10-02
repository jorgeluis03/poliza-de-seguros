package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public Page<UsuarioDTO> obtenerUsuarios(Pageable pageable){
		
		Page<Usuario> listaUsuarios = usuarioRepository.findAll(pageable);
		
		return convertirUsuariosADTOs(listaUsuarios);
		
	}
	
	public Page<UsuarioDTO> convertirUsuariosADTOs(Page<Usuario> listaUsuarios){
		
		return listaUsuarios.map(usuario -> 
        new UsuarioDTO(usuario.getIdUsuario(), 
                       usuario.getNombreUsuario(), 
                       usuario.getCorreo(), 
                       usuario.getEstado()));
	}
	
	public String encoderContrasena(String contrasena) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(contrasena);
	}
}
