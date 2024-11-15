package com.example.policy.repository;

import com.example.policy.model.PolizaCelular;
import com.example.policy.model.PolizaInmueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PolizaCelularRepository extends JpaRepository<PolizaCelular, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM polizascelular WHERE id_poliza = ?1")
    Optional<PolizaCelular> findByPolizaId(int idPoliza);
}
