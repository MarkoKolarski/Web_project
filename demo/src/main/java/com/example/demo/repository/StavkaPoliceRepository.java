package com.example.demo.repository;

import com.example.demo.model.Recenzija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.StavkaPolice;

import java.util.Optional;

@Repository
public interface StavkaPoliceRepository extends JpaRepository<StavkaPolice, Long> {

    Optional<StavkaPolice> findByKnjiga_Id (Long ID);

}
