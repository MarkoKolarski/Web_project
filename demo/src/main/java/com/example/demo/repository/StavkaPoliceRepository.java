package com.example.demo.repository;

import com.example.demo.model.Knjiga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.StavkaPolice;

import java.util.Set;

@Repository
public interface StavkaPoliceRepository extends JpaRepository<StavkaPolice, Long> {

    Set<StavkaPolice> findByKnjiga (Knjiga knjiga);

}
