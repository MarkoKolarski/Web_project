package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Polica;

@Repository
public interface PolicaRepository extends JpaRepository<Polica, Long> {
}