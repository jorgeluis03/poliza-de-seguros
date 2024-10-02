package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Inmueble;

@Repository
public interface InmuebleRepository extends JpaRepository<Inmueble, Integer>{

}
