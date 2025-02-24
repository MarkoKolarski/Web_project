package com.example.demo.model;




import jakarta.persistence.*;

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
    private Set<StavkaPolice> stavkePolice = new HashSet<>();

    public Polica() {
    }

    public Polica(String naziv, Boolean primarna, Set<StavkaPolice> stavkePolice) {
        this.naziv = naziv;
        this.primarna = primarna;
        this.stavkePolice = stavkePolice;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<StavkaPolice> getStavkePolice() {
        return stavkePolice;
    }

    public void setStavkePolice(Set<StavkaPolice> stavkePolice) {
        this.stavkePolice = stavkePolice;
    }

    @Override
    public String toString() {
        return "Polica{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                ", primarna=" + primarna +
                ", StavkePolice=" + stavkePolice +
                '}';
    }
}

