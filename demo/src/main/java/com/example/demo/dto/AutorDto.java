package com.example.demo.dto;

import com.example.demo.model.Autor;
import com.example.demo.model.Knjiga;



import java.util.HashSet;
import java.util.Set;

public class AutorDto extends KorisnikDto{

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

    private Set<Knjiga> spisakKnjiga = new HashSet<>();

    public Boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Knjiga> getSpisakKnjiga() {
        return spisakKnjiga;
    }

    public void setSpisakKnjiga(Set<Knjiga> spisakKnjiga) {
        this.spisakKnjiga = spisakKnjiga;
    }
}
