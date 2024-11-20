package com.example.policy.repository;

import com.example.user.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.policy.model.Poliza;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolizaRepository extends JpaRepository<Poliza, Integer>{

	Page<Poliza> findAll (Pageable pageable);

	Page<Poliza> findByUsuario(Usuario usuario, Pageable pageable);

	@Query("SELECT p " +
			"FROM Poliza p " +
			"WHERE (:numPoliza = '' OR p.numeroPoliza = :numPoliza) " +
			"AND (:tipoPoliza = '' OR p.tipoPoliza = :tipoPoliza) " +
			"AND (:username = '' OR p.usuario.idUsuario IN " +
			"(SELECT u.idUsuario FROM Usuario u WHERE u.nombreUsuario LIKE CONCAT('%', :username, '%')))")
	List<Poliza> searchPoliza(@Param("numPoliza") String numPoliza,
							   @Param("tipoPoliza") String tipoPoliza,
							   @Param("username") String username);





}
