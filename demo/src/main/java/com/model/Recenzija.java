package com.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Recenzija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Recenzija_id;

    private Long Ocena;
    private String Tekst;
    private Date Datum_recenzije;
    @ManyToOne
    @JoinColumn(name = "Korisnik_id")
    private Korisnik korisnik;

    public Recenzija() {
    }

    public Recenzija(Long ocena, String tekst, Date datum_recenzije, Korisnik korisnik) {
        Ocena = ocena;
        Tekst = tekst;
        Datum_recenzije = datum_recenzije;
        this.korisnik = korisnik;
    }

    public Long getOcena() {
        return Ocena;
    }

    public void setOcena(Long ocena) {
        Ocena = ocena;
    }

    public String getTekst() {
        return Tekst;
    }

    public void setTekst(String tekst) {
        Tekst = tekst;
    }

    public Date getDatum_recenzije() {
        return Datum_recenzije;
    }

    public void setDatum_recenzije(Date datum_recenzije) {
        Datum_recenzije = datum_recenzije;
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
                "Recenzija_id=" + Recenzija_id +
                ", Ocena='" + Ocena + '\'' +
                ", Tekst='" + Tekst + '\'' +
                ", Datum_recenzije=" + Datum_recenzije +
                ", korisnik=" + korisnik +
                '}';
    }
}
