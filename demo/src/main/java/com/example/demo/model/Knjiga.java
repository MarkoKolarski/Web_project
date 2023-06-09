package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Knjiga implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String naslov;
    @Column(name = "naslovna_fotografija")
    private String naslovnaFotografija;
    @Column(unique = true)
    private String ISBN;
    @Column(name = "datum_objavljivanja")
    private Date datumObjavljivanja;
    @Column(name = "broj_strana")
    private int brojStrana;

    private String opis;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    @JoinTable(name = "izbor_zanra",
            joinColumns = { @JoinColumn(name = "knjiga_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "zanr_id", referencedColumnName = "id") })
    private Set<Zanr> zanrovi = new HashSet<>();

    private Double ocena;

    public Knjiga() {
    }

    public Knjiga(String naslov, String naslovnaFotografija, String ISBN, Date datumObjavljivanja, int brojStrana, String opis, Set<Zanr> zanrovi, Double ocena) {
        this.naslov = naslov;
        this.naslovnaFotografija = naslovnaFotografija;
        this.ISBN = ISBN;
        this.datumObjavljivanja = datumObjavljivanja;
        this.brojStrana = brojStrana;
        this.opis = opis;
        this.zanrovi = zanrovi;
        this.ocena = ocena;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Date getDatumObjavljivanja() {
        return datumObjavljivanja;
    }

    public void setDatumObjavljivanja(Date datumObjavljivanja) {
        this.datumObjavljivanja = datumObjavljivanja;
    }

    public int getBrojStrana() {
        return brojStrana;
    }

    public void setBrojStrana(int brojStrana) {
        this.brojStrana = brojStrana;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Set<Zanr> getZanrovi() {
        return zanrovi;
    }

    public void setZanrovi(Set<Zanr> zanrovi) {
        this.zanrovi = zanrovi;
    }

    public Double getOcena() {
        return ocena;
    }

    public void setOcena(Double ocena) {
        this.ocena = ocena;
    }

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
                ", Zanrovi=" + zanrovi +
                ", ocena=" + ocena +
                '}';
    }

}