package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Knjiga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Knjiga_id;

    private String Naslov;
    private String Naslovna_fotografija;
    private long ISBN;
    private Date Datum_objavljivanja;
    private long Broj_strana;
    private String Opis;
    @ManyToMany
    @JoinTable(name = "Zanr",
            joinColumns = { @JoinColumn(name = "Knjiga_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "Zanr_id", referencedColumnName = "id") })
    private Set<Zanr> Zanrovi = new HashSet<>();
    private long Ocena;

    public Knjiga() {
    }

    public Knjiga(String naslov, String naslovna_fotografija, long ISBN, Date datum_objavljivanja, long broj_strana, String opis, Set<Zanr> zanrovi, long ocena) {
        Naslov = naslov;
        Naslovna_fotografija = naslovna_fotografija;
        this.ISBN = ISBN;
        Datum_objavljivanja = datum_objavljivanja;
        Broj_strana = broj_strana;
        Opis = opis;
        Zanrovi = zanrovi;
        Ocena = ocena;
    }

    public Set<Zanr> getZanrovi() {
        return Zanrovi;
    }

    public void setZanrovi(Set<Zanr> zanrovi) {
        Zanrovi = zanrovi;
    }

    public String getNaslov() {
        return Naslov;
    }

    public void setNaslov(String naslov) {
        Naslov = naslov;
    }

    public String getNaslovna_fotografija() {
        return Naslovna_fotografija;
    }

    public void setNaslovna_fotografija(String naslovna_fotografija) {
        Naslovna_fotografija = naslovna_fotografija;
    }

    public long getISBN() {
        return ISBN;
    }

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    }

    public Date getDatum_objavljivanja() {
        return Datum_objavljivanja;
    }

    public void setDatum_objavljivanja(Date datum_objavljivanja) {
        Datum_objavljivanja = datum_objavljivanja;
    }

    public long getBroj_strana() {
        return Broj_strana;
    }

    public void setBroj_strana(long broj_strana) {
        Broj_strana = broj_strana;
    }

    public String getOpis() {
        return Opis;
    }

    public void setOpis(String opis) {
        Opis = opis;
    }




    public long getOcena() {
        return Ocena;
    }

    public void setOcena(long ocena) {
        Ocena = ocena;
    }

    @Override
    public String toString() {
        return "Knjiga{" +
                "Knjiga_id=" + Knjiga_id +
                ", Naslov='" + Naslov + '\'' +
                ", Naslovna_fotografija='" + Naslovna_fotografija + '\'' +
                ", ISBN=" + ISBN +
                ", Datum_objavljivanja=" + Datum_objavljivanja +
                ", Broj_strana=" + Broj_strana +
                ", Opis='" + Opis + '\'' +
                ", Zanrovi=" + Zanrovi +
                ", Ocena=" + Ocena +
                '}';
    }
}
