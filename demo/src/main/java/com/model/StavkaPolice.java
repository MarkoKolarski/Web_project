package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class StavkaPolice extends Polica{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long StavkaPolice_id;

    @OneToMany(mappedBy = "StavkaPolice", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Recenzija> Recenzije = new HashSet<>();
    @OneToMany(mappedBy = "StavkaPolice", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Knjiga> Knjige = new HashSet<>();


    public StavkaPolice() {
    }

    public StavkaPolice(Set<Recenzija> recenzije, Set<Knjiga> knjige) {
        Recenzije = recenzije;
        Knjige = knjige;
    }

    public StavkaPolice(String naziv, Boolean primarna, Set<StavkaPolice> stavke_polica, Set<Recenzija> recenzije, Set<Knjiga> knjige) {
        super(naziv, primarna, stavke_polica);
        Recenzije = recenzije;
        Knjige = knjige;
    }

    public Set<Recenzija> getRecenzije() {
        return Recenzije;
    }

    public void setRecenzije(Set<Recenzija> recenzije) {
        Recenzije = recenzije;
    }

    public Set<Knjiga> getKnjige() {
        return Knjige;
    }

    public void setKnjige(Set<Knjiga> knjige) {
        Knjige = knjige;
    }

    @Override
    public String toString() {
        return "StavkaPolice{" +
                "StavkaPolice_id=" + StavkaPolice_id +
                ", Recenzija='" + Recenzije + '\'' +
                ", Knjige=" + Knjige +
                '}';
    }
}
