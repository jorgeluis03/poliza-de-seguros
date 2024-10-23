package com.example.role.model;

import java.util.HashSet;
import java.util.Set;

import com.example.user.model.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Rol {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rol")
	private Integer idRol;
	
	@Column(name = "nombre_rol")
	private String nombreRol;
	
	@OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
	private Set<Usuario> usuarios = new HashSet<>();

	public void addUsuario(Usuario usuario) {
		usuarios.add(usuario);
		usuario.setRol(this);
	}

	public void removeUsuario(Usuario usuario) {
		usuarios.remove(usuario);
		usuario.setRol(null);
	}

}
