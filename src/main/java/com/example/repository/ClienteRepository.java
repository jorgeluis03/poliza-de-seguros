package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Persona;

@Repository
public interface ClienteRepository extends JpaRepository<Persona, Integer>{
	@Query(nativeQuery = true, value = "SELECT * FROM persona where estado = 1 and id_rol = 2")
	List<Persona> findAllClients();
	
}
