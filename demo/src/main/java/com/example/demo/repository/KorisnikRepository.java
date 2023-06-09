package com.example.demo.repository;

import com.example.demo.model.Polica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Korisnik;

import java.util.List;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    //Korisnik getByKorisnickoIme(String korisnickoIme);

    Korisnik getByMejlAdresa(String mejlAdresa);
    Korisnik findByMejlAdresa(String mejlAdresa);

    //Korisnik findById(Long id);

   // Korisnik findByMejlAdresaContainingIgnoreCase(String mejlAdresa);

    Korisnik findByImeContainingIgnoreCase(String ime);


    Korisnik findBykorisnickoIme(String korisnickoIme);
}