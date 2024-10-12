package com.example.service;
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
import com.example.utils.ApiResult;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Transactional
    public ApiResult<UsuarioDTO> crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = convertirDTOAEntity(usuarioDTO);

        // Codificar la contraseña
        String contrasenaHash = passwordEncoder(usuarioDTO.getContrasena());
        usuario.setContrasena(contrasenaHash);
        usuario.setEstado(1);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Devolver el DTO con el ID generado
        return new ApiResult<UsuarioDTO>("Usuario creado correctamente", convertirEntityADTO(usuarioGuardado));
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
    
    //Cifrar la constrasena
    private static String passwordEncoder(String constrena) {
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
     	return encoder.encode(constrena);
    }
}
