package com.example.demo.controller;

import com.example.demo.dto.RecenzijaDto;
import com.example.demo.model.Recenzija;
import com.example.demo.service.RecenzijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RecenzijaRestController {

    @Autowired
    private RecenzijaService recenzijaService;

    // Endpoint za pregled svih recenzija
    @GetMapping("/api/recenzije")
    public ResponseEntity<List<RecenzijaDto>> getAllReviews() {
        List<Recenzija> recenzije = recenzijaService.getAllReviews();

        if (recenzije.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<RecenzijaDto> recenzijeDto = new ArrayList<>();
        for (Recenzija recenzija : recenzije) {
            RecenzijaDto recenzijaDto = new RecenzijaDto();
            recenzijaDto.setId(recenzija.getId());
            recenzijaDto.setOcena(recenzija.getOcena());
            recenzijaDto.setTekst(recenzija.getTekst());
            recenzijaDto.setDatumRecenzije(recenzija.getDatumRecenzije());
            recenzijaDto.setKorisnik(recenzija.getKorisnik());

            recenzijeDto.add(recenzijaDto);
        }

        return ResponseEntity.ok(recenzijeDto);
    }

}
