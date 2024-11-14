package com.example.user.service;
import com.example.role.model.Rol;
import com.example.role.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.user.dto.UsuarioDTO;
import com.example.user.model.Usuario;
import com.example.user.exception.UsuarioNoEncontradoException;
import com.example.user.repository.UsuarioRepository;
import com.example.common.response.ApiResult;

import java.util.Optional;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    PasswordEncoder encoder;
	
	@Transactional
    public ApiResult<UsuarioDTO> crearUsuario(UsuarioDTO signUpRequest) {

        Rol role = rolRepository.findByNombreRol("ROLE_CLIENT");

        Usuario user = new Usuario();
        user.setNombreUsuario(signUpRequest.getNombreUsuario());
        user.setContrasena(encoder.encode(signUpRequest.getContrasena()));
        user.setCorreo(signUpRequest.getCorreo());
        user.setRol(role);
        user.setEstado(1);

        Usuario usuarioGuardado = usuarioRepository.save(user);
        return new ApiResult<UsuarioDTO>("Registro completado", convertirEntityADTO(usuarioGuardado));
    }

    @Transactional(readOnly = true)
    public Page<UsuarioDTO> obtenerUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(this::convertirEntityADTO);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO obtenerUsuarioPorId(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("No se encontró el usuario con ID: " + id));
        return convertirEntityADTO(usuario);
    }

    @Transactional
    public ApiResult<UsuarioDTO> editarUsuarioPorId(int id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("No se encontró el usuario con ID: " + id));

        // Actualiza los campos que no son nulos
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setDni(usuarioDTO.getDni());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setDireccion(usuarioDTO.getDireccion());

        Usuario usuarioEditado = usuarioRepository.save(usuario);
     
        return new ApiResult<UsuarioDTO>("Usuario creado correctamente",convertirEntityADTO(usuarioEditado));
    }

    @Transactional
    public void eliminarUsuario(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + id));

        usuarioRepository.delete(usuario);
    }

    // Conversión de Entity a DTO
    private UsuarioDTO convertirEntityADTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(usuario.getIdUsuario());
        usuarioDTO.setNombreUsuario(usuario.getNombreUsuario());
        usuarioDTO.setCorreo(usuario.getCorreo());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setDni(usuario.getDni());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setDireccion(usuario.getDireccion());
        return usuarioDTO;
    }

    // Conversión de DTO a Entity
    private Usuario convertirDTOAEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setDni(usuarioDTO.getDni());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setDireccion(usuarioDTO.getDireccion());
        usuario.setContrasena(usuarioDTO.getContrasena());
        return usuario;
    }
}
