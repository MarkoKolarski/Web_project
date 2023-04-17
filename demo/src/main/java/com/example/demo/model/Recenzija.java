package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Recenzija implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ocena;

    private String tekst;
    @Column(unique = true,name = "datum_recenzije")
    private Date datumRecenzije;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private StavkaPolice stavkaPolica;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "Korisnik_id")
    private Korisnik korisnik;

    //TODO DAL TREBA KONSTRUKTOR???
    public Recenzija(Long ocena, String tekst, Date datumRecenzije, Korisnik korisnik) {
        this.ocena = ocena;
        this.tekst = tekst;
        this.datumRecenzije = datumRecenzije;
        this.korisnik = korisnik;
    }

    public Recenzija() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOcena() {
        return ocena;
    }

    public void setOcena(Long ocena) {
        this.ocena = ocena;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Date getDatumRecenzije() {
        return datumRecenzije;
    }

    public void setDatumRecenzije(Date datumRecenzije) {
        this.datumRecenzije = datumRecenzije;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    @Override
    public String toString() {
        return "Recenzija{" +
                "id=" + id +
                ", ocena=" + ocena +
                ", tekst='" + tekst + '\'' +
                ", datumRecenzije=" + datumRecenzije +
                ", korisnik=" + korisnik +
                '}';
    }



    public StavkaPolice getStavkaPolica() {
        return stavkaPolica;
    }

    public void setStavkaPolica(StavkaPolice stavkaPolica) {
        this.stavkaPolica = stavkaPolica;
    }
}