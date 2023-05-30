package com.example.demo.dto;

import com.example.demo.model.Zanr;

public class ZanrDto {

    private Long id;

    private String naziv;

    public ZanrDto() {

    }

    public ZanrDto(Zanr zanr)
    {
        this.id = zanr.getId();
        this.naziv = getNaziv();
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
