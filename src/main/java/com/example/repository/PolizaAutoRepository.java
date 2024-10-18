package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.PolizaAuto;

@Repository
public interface PolizaAutoRepository extends JpaRepository<PolizaAuto, Integer>{

	@Query(nativeQuery = true, value = "SELECT * FROM polizasauto WHERE id_poliza = ?1")
	Optional<PolizaAuto> findByPolizaId(int idPoliza);
}
