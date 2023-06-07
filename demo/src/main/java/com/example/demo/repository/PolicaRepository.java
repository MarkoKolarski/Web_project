package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Polica;

import java.util.Optional;

@Repository
public interface PolicaRepository extends JpaRepository<Polica, Long> {

   Optional<Polica> findById (Long id);

    Polica findByNazivContainingIgnoreCase(String nazivPolice);
}