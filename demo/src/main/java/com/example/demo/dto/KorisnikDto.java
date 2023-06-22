package com.example.demo.dto;

import com.example.demo.model.Korisnik;
import com.example.demo.model.Polica;
import com.example.demo.model.Uloga;
import jakarta.persistence.Column;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class KorisnikDto {

    protected Long id;

    protected String ime;

    protected String prezime;

    @Column(unique = true)
    protected String korisnickoIme;

    @Column(unique = true)
    protected String mejlAdresa;

    protected String lozinka;

    protected Date datumRodjenja;

    protected String profilnaSlika;

    protected String opis;

    protected Uloga uloga;

    protected String potvrdiLozinku;

    private Set<Polica> police = new HashSet<>();


    public KorisnikDto() {
    }

    public KorisnikDto(Korisnik korisnik) {
        this.id = korisnik.getId();
        this.ime = korisnik.getIme();
        this.prezime = korisnik.getPrezime();
        this.korisnickoIme = korisnik.getKorisnickoIme();
        this.mejlAdresa = korisnik.getMejlAdresa();
        this.lozinka = korisnik.getLozinka();
        this.datumRodjenja = korisnik.getDatumRodjenja();
        this.profilnaSlika = korisnik.getProfilnaSlika();
        this.opis = korisnik.getOpis();
        this.uloga = korisnik.getUloga();
        this.potvrdiLozinku = korisnik.getLozinka();
        this.police = korisnik.getPolice();
    }

    public String getPotvrdiLozinku() {
        return potvrdiLozinku;
    }

    public void setPotvrdiLozinku(String potvrdiLozinku) {
        this.potvrdiLozinku = potvrdiLozinku;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getMejlAdresa() {
        return mejlAdresa;
    }

    public void setMejlAdresa(String mejlAdresa) {
        this.mejlAdresa = mejlAdresa;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getProfilnaSlika() {
        return profilnaSlika;
    }

    public void setProfilnaSlika(String profilnaSlika) {
        this.profilnaSlika = profilnaSlika;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

    public Set<Polica> getPolice() {
        return police;
    }

    public void setPolice(Set<Polica> police) {
       this.police = police;
    }
}
