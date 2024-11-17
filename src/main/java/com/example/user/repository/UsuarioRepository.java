package com.example.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.user.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	Page<Usuario> findAll (Pageable pageable);
	Boolean existsByNombreUsuario (String username);
	Optional<Usuario> findByNombreUsuario(String username);
	Optional<Usuario> findByCorreo (String correo);

}
