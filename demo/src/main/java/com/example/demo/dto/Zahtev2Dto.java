package com.example.demo.dto;

import com.example.demo.model.Autor;
import com.example.demo.model.Status;
import com.example.demo.model.ZahtevZaAktivacijuNalogaAutora;

import java.util.Date;

public class Zahtev2Dto {

    private Long id;
    private Status status;



    public Zahtev2Dto() {
    }

    public Zahtev2Dto(ZahtevZaAktivacijuNalogaAutora zahtev) {
        this.id = zahtev.getId();
        this.status = zahtev.getStatus();


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
