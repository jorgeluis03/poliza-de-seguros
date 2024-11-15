package com.example.policy.repository;

import com.example.user.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.policy.model.Poliza;

@Repository
public interface PolizaRepository extends JpaRepository<Poliza, Integer>{

	Page<Poliza> findAll (Pageable pageable);

	Page<Poliza> findByUsuario(Usuario usuario, Pageable pageable);
}
