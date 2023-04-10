package com.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

enum Uloga {CITALAC, AUTOR, ADMINISTRATOR}
@Entity
public class Korisnik implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long Korisnik_id;
    protected String Ime;
    protected String Prezime;
    @Column(unique = true)
    protected String Korisnicko_ime;
    @Column(unique = true)
    protected String Mejl_adresa;
    protected String Lozinka;
    protected Date Datum_rodjenja;
    protected String Profilna_slika;
    protected String Opis;
    protected Uloga uloga;

    public Korisnik() {
    }

    public Korisnik(String ime, String prezime, String korisnicko_ime, String mejl_adresa, String lozinka, Date datum_rodjenja, String profilna_slika, String opis, Uloga uloga) {
        Ime = ime;
        Prezime = prezime;
        Korisnicko_ime = korisnicko_ime;
        Mejl_adresa = mejl_adresa;
        Lozinka = lozinka;
        Datum_rodjenja = datum_rodjenja;
        Profilna_slika = profilna_slika;
        Opis = opis;
        this.uloga = uloga;
    }

    public String getIme() {
        return Ime;
    }

    public String getPrezime() {
        return Prezime;
    }

    public String getKorisnicko_ime() {
        return Korisnicko_ime;
    }

    public String getMejl_adresa() {
        return Mejl_adresa;
    }

    public String getLozinka() {
        return Lozinka;
    }

    public Date getDatum_rodjenja() {
        return Datum_rodjenja;
    }

    public String getProfilna_slika() {
        return Profilna_slika;
    }

    public String getOpis() {
        return Opis;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setIme(String ime) {
        Ime = ime;
    }

    public void setPrezime(String prezime) {
        Prezime = prezime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        Korisnicko_ime = korisnicko_ime;
    }

    public void setMejl_adresa(String mejl_adresa) {
        Mejl_adresa = mejl_adresa;
    }

    public void setLozinka(String lozinka) {
        Lozinka = lozinka;
    }

    public void setDatum_rodjenja(Date datum_rodjenja) {
        Datum_rodjenja = datum_rodjenja;
    }

    public void setProfilna_slika(String profilna_slika) {
        Profilna_slika = profilna_slika;
    }

    public void setOpis(String opis) {
        Opis = opis;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "Korisnik_id=" + Korisnik_id +
                ", Ime='" + Ime + '\'' +
                ", Prezime='" + Prezime + '\'' +
                ", Korisnicko_ime='" + Korisnicko_ime + '\'' +
                ", Mejl_adresa='" + Mejl_adresa + '\'' +
                ", Lozinka='" + Lozinka + '\'' +
                ", Datum_rodjenja=" + Datum_rodjenja +
                ", Profilna_slika='" + Profilna_slika + '\'' +
                ", Opis='" + Opis + '\'' +
                ", uloga=" + uloga +
                '}';
    }
}
