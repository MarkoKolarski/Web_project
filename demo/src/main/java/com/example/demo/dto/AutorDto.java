package com.example.demo.dto;

import com.example.demo.model.Autor;
import com.example.demo.model.Knjiga;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

public class AutorDto {

    // Povezano sa id od korisnika
    private Long id;

    private Boolean aktivan;

    public AutorDto () {

    }
    public AutorDto(Autor autor) {
        this.id = autor.getId();
        this.aktivan = autor.getAktivan();
        this.spisakKnjiga = autor.getSpisakKnjiga();
    }

    @ManyToMany(fetch = FetchType.EAGER)
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

}
