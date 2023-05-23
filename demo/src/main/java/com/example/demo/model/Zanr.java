package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Zanr implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    public Zanr() {

    }

    public Zanr(String naziv)
    {
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return "Zanr{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                '}';
    }
}