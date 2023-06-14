package com.example.demo.dto;

import com.example.demo.model.Polica;
import com.example.demo.model.StavkaPolice;


import java.util.HashSet;
import java.util.Set;

public class PolicaDto {

    private Long id;

    private String naziv;

    private Boolean primarna;

    private Set<StavkaPolice> stavkePolice = new HashSet<>();

    public PolicaDto() {
    }

    public PolicaDto(Polica polica) {
        this.id = polica.getId();
        this.naziv = polica.getNaziv();
        this.primarna = polica.getPrimarna();
        this.stavkePolice = polica.getStavkePolice();
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

}
