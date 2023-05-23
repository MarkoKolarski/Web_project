package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.ZahtevZaAktivacijuNalogaAutora;

@Repository
public interface ZahtevZaAktivacijuNalogaAutoraRepository extends JpaRepository<ZahtevZaAktivacijuNalogaAutora, Long> {
}