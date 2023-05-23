package com.example.demo.repository;

import com.example.demo.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Knjiga;

import java.util.List;

@Repository
public interface KnjigaRepository extends JpaRepository<Knjiga, Long> {

    //List<Knjiga> findAllByAutors(String autor);
}
