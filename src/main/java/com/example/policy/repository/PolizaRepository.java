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

@Repository
public interface PolizaRepository extends JpaRepository<Poliza, Integer>{

	Page<Poliza> findAll (Pageable pageable);

	Page<Poliza> findByUsuario(Usuario usuario, Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT * " +
			"FROM polizas " +
			"WHERE (:numPoliza = '' OR numero_poliza = :numPoliza) " +
			"AND (:tipoPoliza = '' OR tipo_poliza = :tipoPoliza)" +
			"AND (:idUsuario = '' OR id_usuario = :idUsuario)")
	List<Poliza> searchPoliza(@Param("numPoliza") String numPoliza,
							  @Param("tipoPoliza") String tipoPoliza,
							  @Param("idUsuario" ) String idUsuario);

}
