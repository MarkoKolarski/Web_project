package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Polica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;

    private Boolean primarna;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "izbor_stavke_police",
            joinColumns = { @JoinColumn(name = "polica_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "stavka_police_id", referencedColumnName = "id") })
    private Set<StavkaPolice> StavkePolice = new HashSet<>();

    public Polica() {
    }

    public Polica(String naziv, Boolean primarna, Set<StavkaPolice> stavkePolice) {
        this.naziv = naziv;
        this.primarna = primarna;
        StavkePolice = stavkePolice;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Boolean getPrimarna() {
        return primarna;
    }

    public void setPrimarna(Boolean primarna) {
        this.primarna = primarna;
    }

    public Set<StavkaPolice> getStavkePolice() {
        return StavkePolice;
    }

    public void setStavkePolice(Set<StavkaPolice> stavkePolice) {
        StavkePolice = stavkePolice;
    }

    @Override
    public String toString() {
        return "Polica{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                ", primarna=" + primarna +
                ", StavkePolice=" + StavkePolice +
                '}';
    }
}

