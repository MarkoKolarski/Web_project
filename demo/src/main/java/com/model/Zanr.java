package com.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Set;

public class Zanr extends Knjiga{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Zanr_id;
    private String Naziv;

    public Zanr() {
    }

    public Zanr(String naziv) {
        Naziv = naziv;
    }

    public Zanr(String naslov, String naslovna_fotografija, long ISBN, Date datum_objavljivanja, long broj_strana, String opis, Set<Zanr> zanrovi, long ocena, String naziv) {
        super(naslov, naslovna_fotografija, ISBN, datum_objavljivanja, broj_strana, opis, zanrovi, ocena);
        Naziv = naziv;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setNaziv(String naziv) {
        Naziv = naziv;
    }

    @Override
    public String toString() {
        return "Zanr{" +
                "Zanr_id=" + Zanr_id +
                ", Naziv='" + Naziv + '\'' +
                '}';
    }


}
