package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "stavka_police")
public class StavkaPolice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Recenzija> Recenzije = new HashSet<>();
    //U jednom odeljku("Stavka police") ima više knjiga sa više recenzija
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Knjiga> Knjige = new HashSet<>();

    public StavkaPolice() {
    }

    public StavkaPolice(Set<Recenzija> recenzije, Set<Knjiga> knjige) {
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
    //TODO DAL TREBA TOSTRING??
    @Override
    public String toString() {
        return "StavkaPolice{" +
                "id=" + id +
                ", Recenzije=" + Recenzije +
                ", Knjige=" + Knjige +
                '}';
    }


}