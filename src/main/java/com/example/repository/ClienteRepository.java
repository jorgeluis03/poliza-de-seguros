package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	List<Cliente> findByEstado(int estado);
}
