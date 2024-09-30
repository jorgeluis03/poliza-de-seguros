package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UsuarioDTO;
import com.example.entity.Usuario;
import com.example.service.UsuarioService;

@RestController
@RequestMapping("v1/usuarios")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario){
		System.out.println(usuario.getNombreUsuario());
		usuarioService.crearUsuario(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body("Usuario Creado");
	}
	
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> obtenerUsuarios(){
		List<UsuarioDTO> lista_UsuarioDTOs =	usuarioService.obtenerUsuarios();
		return ResponseEntity.ok(lista_UsuarioDTOs);
	}
	
	
}
