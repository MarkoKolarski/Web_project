package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity

public class Autor extends Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean aktivan;

    public Autor () {

    }

    //TODO proveriti dal treba ceo konstruktor ili samo deo od Autora
    public Autor(String ime, String prezime, String korisnickoIme, String mejlAdresa, String lozinka, Date datumRodjenja, String profilnaSlika, String opis, Uloga uloga) {
        super(ime, prezime, korisnickoIme, mejlAdresa, lozinka, datumRodjenja, profilnaSlika, opis, uloga);
    }

    @OneToMany(mappedBy = "autors", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Knjiga> spisakKnjiga = new HashSet<>();

    public Boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", aktivan=" + aktivan +
                ", id=" + id +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", korisnickoIme='" + korisnickoIme + '\'' +
                ", mejlAdresa='" + mejlAdresa + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", datumRodjenja='" + datumRodjenja + '\'' +
                ", profilnaSlika='" + profilnaSlika + '\'' +
                ", opis='" + opis + '\'' +
                ", uloga=" + uloga +
                '}';
    }
}
