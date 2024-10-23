package com.example.policy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.policy.model.PolizaInmueble;

@Repository
public interface PolizaInmuebleRepository extends JpaRepository<PolizaInmueble, Integer>{
	@Query(nativeQuery = true, value = "SELECT * FROM polizasinmueble WHERE id_poliza = ?1")
	Optional<PolizaInmueble> findByPolizaId(int idPoliza);
}
