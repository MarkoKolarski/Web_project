package com.example.demo.controller;

import com.example.demo.dto.KnjigaDto;

import com.example.demo.model.Knjiga;

import com.example.demo.service.KnjigaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
public class KnjigaRestController {

    @Autowired
    private KnjigaService knjigaService;





    // Endpoint za prikaz svih knjiga
    @GetMapping("/api/knjige")
    public ResponseEntity<List<KnjigaDto>> getAllBooks() {
        List<Knjiga> knjige = knjigaService.getAllBooks();

        if (knjige.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<KnjigaDto> knjigeDto = new ArrayList<>();
        for (Knjiga knjiga : knjige) {
            KnjigaDto knjigaDto = new KnjigaDto();
            knjigaDto.setId(knjiga.getId());
            knjigaDto.setNaslov(knjiga.getNaslov());
            knjigaDto.setNaslovnaFotografija(knjiga.getNaslovnaFotografija());
            knjigaDto.setDatumObjavljivanja(knjiga.getDatumObjavljivanja());
            knjigaDto.setBrojStrana(knjiga.getBrojStrana());
            knjigaDto.setOpis(knjiga.getOpis());
            knjigaDto.setZanrovi(knjiga.getZanrovi());
            knjigaDto.setOcena(knjiga.getOcena());
            knjigeDto.add(knjigaDto);
        }

        return ResponseEntity.ok(knjigeDto);
    }


    @GetMapping("/api/knjige-po-naslovu")
    public ResponseEntity<List<KnjigaDto>> searchBooks1(@RequestParam("query") String query) {
        List<KnjigaDto> knjigeDto = knjigaService.searchBooks(query);

        if (knjigeDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }



        return ResponseEntity.ok(knjigeDto);
    }

}
