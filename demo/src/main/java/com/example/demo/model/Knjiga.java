package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Knjiga implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String naslov;
    @Column(unique = true,name = "naslovna_fotografija")
    private String naslovnaFotografija;

    private Long ISBN;
    @Column(unique = true,name = "datum_objavljivanja")
    private Date datumObjavljivanja;
    @Column(unique = true,name = "broj_strana")
    private Long brojStrana;

    private String opis;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "izbor_zanra",
            joinColumns = { @JoinColumn(name = "knjiga_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "zanr_id", referencedColumnName = "id") })
    private Set<Zanr> Zanrovi = new HashSet<>();

    @ManyToOne(optional = false)
    private StavkaPolice stavkaPolica2;

    private Long ocena;

    public Knjiga() {
    }
    //TODO Da li u konstruktoru treba sve sem žanra???
    public Knjiga(String naslov, String naslovnaFotografija, Long ISBN, Date datumObjavljivanja, Long brojStrana, String opis, Long ocena) {
        this.naslov = naslov;
        this.naslovnaFotografija = naslovnaFotografija;
        this.ISBN = ISBN;
        this.datumObjavljivanja = datumObjavljivanja;
        this.brojStrana = brojStrana;
        this.opis = opis;
        ocena = ocena;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getNaslovnaFotografija() {
        return naslovnaFotografija;
    }

    public void setNaslovnaFotografija(String naslovnaFotografija) {
        this.naslovnaFotografija = naslovnaFotografija;
    }

    public Long getISBN() {
        return ISBN;
    }

    public void setISBN(Long ISBN) {
        this.ISBN = ISBN;
    }

    public Date getDatumObjavljivanja() {
        return datumObjavljivanja;
    }

    public void setDatumObjavljivanja(Date datumObjavljivanja) {
        this.datumObjavljivanja = datumObjavljivanja;
    }

    public Long getBrojStrana() {
        return brojStrana;
    }

    public void setBrojStrana(Long brojStrana) {
        this.brojStrana = brojStrana;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Set<Zanr> getZanrovi() {
        return Zanrovi;
    }

    public void setZanrovi(Set<Zanr> zanrovi) {
        this.Zanrovi = zanrovi;
    }

    public Long getOcena() {
        return ocena;
    }

    public void setOcena(Long ocena) {
        this.ocena = ocena;
    }

    //TODO Ispisati Žanr?
    @Override
    public String toString() {
        return "Knjiga{" +
                "id=" + id +
                ", naslov='" + naslov + '\'' +
                ", naslovnaFotografija='" + naslovnaFotografija + '\'' +
                ", ISBN=" + ISBN +
                ", datumObjavljivanja=" + datumObjavljivanja +
                ", brojStrana=" + brojStrana +
                ", opis='" + opis + '\'' +
                ", Zanrovi=" + Zanrovi +
                ", ocena=" + ocena +
                '}';
    }



    public StavkaPolice getStavkaPolica2() {
        return stavkaPolica2;
    }

    public void setStavkaPolica2(StavkaPolice stavkaPolica2) {
        this.stavkaPolica2 = stavkaPolica2;
    }
}