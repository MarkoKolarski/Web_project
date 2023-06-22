package com.example.demo.model;


import jakarta.persistence.*;

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
    @JoinTable(name = "STAVKA_POLICE_RECENZIJA")
    private Set<Recenzija> recenzije = new HashSet<>();
    //U jednom odeljku("Stavka police") ima više knjiga sa više recenzija

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "knjiga_id")
    private Knjiga knjiga;


    public StavkaPolice() {
    }

    public StavkaPolice(Long id, Set<Recenzija> recenzije, Knjiga Knjiga) {
        this.id = id;
        this.recenzije = recenzije;
        this.knjiga = knjiga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Recenzija> getRecenzije() {
        return recenzije;
    }

    public void setRecenzije(Set<Recenzija> recenzije) {
        recenzije = recenzije;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    @Override
    public String toString() {
        return "StavkaPolice{" +
                "id=" + id +
                ", Recenzije=" + recenzije +
                ", Knjige=" + knjiga +
                '}';
    }


}