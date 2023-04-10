package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Autor extends Korisnik{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Autor_id;
    private boolean Aktivan;
    @OneToMany(mappedBy = "Autor", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Knjiga> Spisak_knjiga = new HashSet<>();

    public Autor() {
    }

    public Autor(boolean aktivan, Set<Knjiga> spisak_knjiga) {
        Aktivan = aktivan;
        Spisak_knjiga = spisak_knjiga;
    }
    public Autor(String ime, String prezime, String korisnicko_ime, String mejl_adresa, String lozinka, Date datum_rodjenja, String profilna_slika, String opis, Uloga uloga, boolean aktivan, Set<Knjiga> spisak_knjiga) {
        super(ime, prezime, korisnicko_ime, mejl_adresa, lozinka, datum_rodjenja, profilna_slika, opis, uloga);
        Aktivan = aktivan;
        Spisak_knjiga = spisak_knjiga;
    }

    public boolean isAktivan() {
        return Aktivan;
    }

    public Set<Knjiga> getSpisak_knjiga() {
        return Spisak_knjiga;
    }

    public void setAktivan(boolean aktivan) {
        Aktivan = aktivan;
    }

    public void setSpisak_knjiga(Set<Knjiga> spisak_knjiga) {
        Spisak_knjiga = spisak_knjiga;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "Autor_id=" + Autor_id +
                ", Aktivan=" + Aktivan +
                ", Spisak_knjiga=" + Spisak_knjiga +
                '}';
    }
}
