package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.PolizaAuto;

@Repository
public interface PolizaAutoRepository extends JpaRepository<PolizaAuto, Integer>{

}
