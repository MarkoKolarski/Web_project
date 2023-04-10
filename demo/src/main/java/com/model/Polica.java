package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Polica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Polica_id;

    private String Naziv;
    private Boolean Primarna;
    @OneToMany(mappedBy = "Polica", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<StavkaPolice> Stavke_polica = new HashSet<>();

    public Polica() {
    }

    public Polica(String naziv, Boolean primarna, Set<StavkaPolice> stavke_polica) {
        Naziv = naziv;
        Primarna = primarna;
        Stavke_polica = stavke_polica;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setNaziv(String naziv) {
        Naziv = naziv;
    }

    public Boolean getPrimarna() {
        return Primarna;
    }

    public void setPrimarna(Boolean primarna) {
        Primarna = primarna;
    }

    public Set<StavkaPolice> getStavke_polica() {
        return Stavke_polica;
    }

    public void setStavke_polica(Set<StavkaPolice> stavke_polica) {
        Stavke_polica = stavke_polica;
    }

    @Override
    public String toString() {
        return "Polica{" +
                "Polica_id=" + Polica_id +
                ", Naziv='" + Naziv + '\'' +
                ", Primarna=" + Primarna +
                ", Stavke_polica=" + Stavke_polica +
                '}';
    }
}
