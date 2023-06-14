package com.example.demo.controller;


import com.example.demo.dto.RecenzijaDto;
import com.example.demo.dto.ZahtevDto;
import com.example.demo.model.Recenzija;
import com.example.demo.model.ZahtevZaAktivacijuNalogaAutora;
import com.example.demo.service.ZahtevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ZahtevRestController {

    @Autowired
    private ZahtevService zahtevService;

    @GetMapping("/api/zahtevi")
    public ResponseEntity<List<ZahtevDto>> sviZahtevi() {
        List<ZahtevZaAktivacijuNalogaAutora> zahtevi = zahtevService.getAll();

        if (zahtevi.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<ZahtevDto> zahteviDtos = new ArrayList<>();
        for (ZahtevZaAktivacijuNalogaAutora zahtev : zahtevi) {
            ZahtevDto zahtevDto = new ZahtevDto();
            zahtevDto.setId(zahtev.getId());
            zahtevDto.setEmail(zahtev.getEmail());
            zahtevDto.setDatum(zahtev.getDatum());
            zahtevDto.setPoruka(zahtev.getPoruka());
            zahtevDto.setTelefon(zahtev.getTelefon());
            zahtevDto.setAutor(zahtev.getAutor());
            zahtevDto.setStatus(zahtev.getStatus());

            zahteviDtos.add(zahtevDto);
        }

        return ResponseEntity.ok(zahteviDtos);
    }

    @PostMapping("api/zahtev-autor")
    public ResponseEntity<String> zahtevZaAutora(@RequestBody ZahtevDto zahtevDto) {

        if (zahtevDto == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unesi podatke");
        }

        boolean autorPostoji = zahtevService.registerUser(zahtevDto);

        if (!autorPostoji) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Autor nije pronadjen.");
        }

        return ResponseEntity.ok("Zahtev za aktivaciju autora uspesno poslat.");
    }


}
