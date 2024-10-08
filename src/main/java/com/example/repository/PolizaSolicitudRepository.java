package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.PolizaSolicitud;

@Repository
public interface PolizaSolicitudRepository extends JpaRepository<PolizaSolicitud, Integer> {
	Page<PolizaSolicitud> findAll (Pageable pageable);
}
