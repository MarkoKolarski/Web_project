package com.example.demo.dto;

import com.example.demo.model.Knjiga;
import com.example.demo.model.Zanr;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class KnjigaDto {

    protected Long id;

    private String naslov;

    private String naslovnaFotografija;

    private String ISBN;

    private Date datumObjavljivanja;

    private int brojStrana;

    private String opis;

    private Set<Zanr> Zanrovi = new HashSet<>();

    private Double ocena;

    public KnjigaDto() {
    }

    public KnjigaDto(Knjiga knjiga) {
        this.id = knjiga.getId();
        this.naslov = knjiga.getNaslov();
        this.naslovnaFotografija = knjiga.getNaslovnaFotografija();
        this.ISBN = knjiga.getISBN();
        this.datumObjavljivanja = knjiga.getDatumObjavljivanja();
        this.brojStrana = knjiga.getBrojStrana();
        this.opis = knjiga.getOpis();
        this.Zanrovi = knjiga.getZanrovi();
        this.ocena = knjiga.getOcena();
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
        return Zanrovi;
    }

    public void setZanrovi(Set<Zanr> zanrovi) {
        Zanrovi = zanrovi;
    }

    public Double getOcena() {
        return ocena;
    }

    public void setOcena(Double ocena) {
        this.ocena = ocena;
    }
}
