package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "zahtev")
public class ZahtevZaAktivacijuNalogaAutora implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Zahtev_id;
    private String Email;
    private String Telefon;
    private String Poruka;
    private Date Datum;
    private Status status;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;


    public ZahtevZaAktivacijuNalogaAutora() {
    }

    public ZahtevZaAktivacijuNalogaAutora(String email, String telefon, String poruka, Date datum, Status status, Autor autor) {
        Email = email;
        Telefon = telefon;
        Poruka = poruka;
        Datum = datum;
        this.status = status;
        this.autor = autor;
    }

    public Long getZahtev_id() {
        return Zahtev_id;
    }

    public void setZahtev_id(Long zahtev_id) {
        Zahtev_id = zahtev_id;
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

    @Override
    public String toString() {
        return "ZahtevZaAktivacijuNalogaAutora{" +
                "Zahtev_id=" + Zahtev_id +
                ", Email='" + Email + '\'' +
                ", Telefon=" + Telefon +
                ", Poruka='" + Poruka + '\'' +
                ", Datum=" + Datum +
                ", status=" + status +
                ", autor=" + autor +
                '}';
    }
}
