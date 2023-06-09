package com.example.demo.controller;

import com.example.demo.dto.RecenzijaDto;
import com.example.demo.model.Knjiga;
import com.example.demo.model.Recenzija;
import com.example.demo.model.StavkaPolice;
import com.example.demo.repository.StavkaPoliceRepository;
import com.example.demo.service.KnjigaService;
import com.example.demo.service.StavkaPoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Set;

@RestController
public class StavkaPoliceRestController {


    @Autowired
    private KnjigaService knjigaService;

    @Autowired
    private StavkaPoliceService stavkaPoliceService;

    // Endpoint za prikaz Recenzije po knjizi
    @GetMapping("/api/knjiga/{nazivKnjige}/recenzije")
    public ResponseEntity<Set<RecenzijaDto>> getRecenzijeByNazivKnjige(@PathVariable("nazivKnjige") String nazivKnjige) {
        Set<Knjiga> knjige = knjigaService.findByNaslovContainingIgnoreCase(nazivKnjige);

        if (knjige.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        Set <RecenzijaDto> recenzijeDto = new HashSet<>();

        for (Knjiga knjiga : knjige) {
            Set <StavkaPolice> stavkePolice = (Set<StavkaPolice>) stavkaPoliceService.findByKnjiga(knjiga);
            for (StavkaPolice stavkaPolice : stavkePolice) {
                Set<Recenzija> recenzije = stavkaPolice.getRecenzije();
                for (Recenzija recenzija : recenzije) {
                    RecenzijaDto recenzijaDto = new RecenzijaDto(recenzija);
                    recenzijeDto.add(recenzijaDto);
                }
            }
        }

        return ResponseEntity.ok(recenzijeDto);
    }

}
