package com.example.policy.repository;

import com.example.policy.model.PolizaCelular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolizaCelularRepository extends JpaRepository<PolizaCelular, Integer> {
}
