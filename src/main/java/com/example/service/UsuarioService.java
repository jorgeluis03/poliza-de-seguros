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
import com.example.entity.Rol;
import com.example.entity.Usuario;
import com.example.exceptions.RolNoEncontradoException;
import com.example.exceptions.UsuarioNoEncontradoException;
import com.example.repository.RolRepository;
import com.example.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	RolRepository rolRepository;
	
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
		Usuario usuario = usuarioRepository.findById(id)
							.orElseThrow(() -> new UsuarioNoEncontradoException("No se encontró el usuario con ID: "+id));
		return convertirEntityADTO(usuario);
	}
	
	
	@Transactional
	public Map<String, Object> editarUsuarioPorId(int id, UsuarioDTO usuarioDTO){
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		Usuario usuario = usuarioRepository.findById(id)
	            			.orElseThrow(() -> new UsuarioNoEncontradoException("No se encontró el usuario con ID: " + id));

        Rol rol = rolRepository.findById(usuarioDTO.getIdRol())
        			.orElseThrow(() -> new RolNoEncontradoException("No se encontró el rol con el ID: " + usuarioDTO.getIdRol()));

        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
		usuario.setCorreo(usuarioDTO.getCorreo());
		usuario.setNombre(usuarioDTO.getNombre());
		usuario.setApellido(usuarioDTO.getApellido());
		usuario.setDni(usuarioDTO.getDni());
		usuario.setTelefono(usuarioDTO.getTelefono());
		usuario.setDireccion(usuarioDTO.getDireccion());
		usuario.setRol(rol);

        usuarioRepository.save(usuario);
        
        responseMap.put("id",usuario.getIdUsuario());
        responseMap.put("mensaje", "Usuario editado correctamente");
        
        return responseMap;
	}
	
	@Transactional
	public void eliminarUsuario(int id) {
		Usuario usuario = usuarioRepository.findById(id)
	            .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + id));
	    
	    usuarioRepository.delete(usuario);
	}
	
	public UsuarioDTO convertirEntityADTO(Usuario usuario){
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(usuario.getIdUsuario());
		usuarioDTO.setNombreUsuario(usuario.getNombreUsuario());
		usuarioDTO.setCorreo(usuario.getCorreo());
		usuarioDTO.setNombre(usuario.getNombre());
		usuarioDTO.setApellido(usuario.getApellido());

		return usuarioDTO;
	}
	
	public String encoderContrasena(String contrasena) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(contrasena);
	}
}
