package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;;

import java.util.HashSet;
import java.util.Set;


@Entity
public class Autor extends Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Povezano sa id od korisnika
    private Long id;

    private Boolean aktivan;

    public Autor () {

    }
    public Autor(Boolean aktivan, Set<Knjiga> spisakKnjiga) {
        this.aktivan = aktivan;
        this.spisakKnjiga = spisakKnjiga;
    }


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "spisak_knjiga",
            joinColumns = { @JoinColumn(name = "autor_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "knjiga_id", referencedColumnName = "id") })
    private Set<Knjiga> spisakKnjiga = new HashSet<>();

    public Boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Set<Knjiga> getSpisakKnjiga() {
        return spisakKnjiga;
    }

    public void setSpisakKnjiga(Set<Knjiga> spisakKnjiga) {
        this.spisakKnjiga = spisakKnjiga;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", aktivan=" + aktivan +
                ", spisakKnjiga=" + spisakKnjiga +
                ", id=" + id +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", korisnickoIme='" + korisnickoIme + '\'' +
                ", mejlAdresa='" + mejlAdresa + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", datumRodjenja=" + datumRodjenja +
                ", profilnaSlika='" + profilnaSlika + '\'' +
                ", opis='" + opis + '\'' +
                ", uloga=" + uloga +
                '}';
    }
}
