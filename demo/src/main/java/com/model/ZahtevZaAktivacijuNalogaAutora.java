package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

enum Status{NA_CEKANJU, ODOBREN, ODBIJEN}

@Entity
public class ZahtevZaAktivacijuNalogaAutora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Zahtev_id;
    private String Email;
    private long Telefon;
    private String Poruka;
    private Date Datum;
    private Status status;

    public ZahtevZaAktivacijuNalogaAutora() {
    }
    public ZahtevZaAktivacijuNalogaAutora(String email, long telefon, String poruka, Date datum, Status status) {
        Email = email;
        Telefon = telefon;
        Poruka = poruka;
        Datum = datum;
        this.status = status;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public long getTelefon() {
        return Telefon;
    }

    public void setTelefon(long telefon) {
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

    @Override
    public String toString() {
        return "ZahtevZaAktivacijuNalogaAutora{" +
                "Zahtev_id=" + Zahtev_id +
                ", Email='" + Email + '\'' +
                ", Telefon=" + Telefon +
                ", Poruka='" + Poruka + '\'' +
                ", Datum=" + Datum +
                ", status=" + status +
                '}';
    }
}
