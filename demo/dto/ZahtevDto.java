package com.example.demo.dto;

import com.example.demo.model.Autor;
import com.example.demo.model.Status;
import com.example.demo.model.ZahtevZaAktivacijuNalogaAutora;

import java.util.Date;

public class ZahtevDto {

    private Long id;
    private String Email;
    private String Telefon;
    private String Poruka;
    private Date Datum;
    private Status status;

    private Autor autor;


    public ZahtevDto() {
    }

    public ZahtevDto(ZahtevZaAktivacijuNalogaAutora zahtev) {
        this.id = zahtev.getId();
        this.Email = zahtev.getEmail();
        this.Telefon = zahtev.getTelefon();
        this.Poruka = zahtev.getPoruka();
        this.Datum = zahtev.getDatum();
        this.status = zahtev.getStatus();
        this.autor = zahtev.getAutor();

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    public String getPoruka() {
        return Poruka;
    }

    public void setPoruka(String poruka) {
        Poruka = poruka;
    }

    public Date getDatum() {
        return Datum;
    }

    public void setDatum(Date datum) {
        Datum = datum;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
