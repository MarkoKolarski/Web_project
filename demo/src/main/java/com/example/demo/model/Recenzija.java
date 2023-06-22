package com.example.demo.model;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;



@Entity
public class Recenzija implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int ocena;

    private String tekst;
    @Column(name = "datum_recenzije")
    private Date datumRecenzije;

    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "korisnik_id")
    private Korisnik korisnik;

    public Recenzija(int ocena, String tekst, Date datumRecenzije, Korisnik korisnik) {
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

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
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
                //", korisnik=" + korisnik +
                //zbog infinite loop-a, smo privremeno izbacili
                '}';
    }

}