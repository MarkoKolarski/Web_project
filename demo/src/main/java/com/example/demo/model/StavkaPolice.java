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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "knjiga_id")
    private Knjiga Knjiga;

    public Knjiga getKnjiga() {
        return Knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        Knjiga = knjiga;
    }

    public StavkaPolice() {
    }

    public StavkaPolice(Set<Recenzija> recenzije, Knjiga knjiga) {
        Recenzije = recenzije;
        Knjiga = knjiga;
    }

    public Set<Recenzija> getRecenzije() {
        return Recenzije;
    }

    public void setRecenzije(Set<Recenzija> recenzije) {
        Recenzije = recenzije;
    }





    @Override
    public String toString() {
        return "StavkaPolice{" +
                "id=" + id +
                ", Recenzije=" + Recenzije +
                ", Knjige=" + Knjiga +
                '}';
    }


}