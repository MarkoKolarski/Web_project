package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Korisnik;

import java.util.List;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    Korisnik getByKorisnickoIme(String korisnickoIme);

    Korisnik findByMejlAdresa(String mejlAdresa);


}