package com.example.demo.controller;

import com.example.demo.dto.Zahtev2Dto;
import com.example.demo.dto.ZanrDto;
import com.example.demo.model.*;
import com.example.demo.service.ZanrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ZanrRestController {

    @Autowired
    private ZanrService zanrService;

    // Endpoint za pregled svih žanrova
    @GetMapping("/api/zanrovi")
    public ResponseEntity<List<ZanrDto>> getAllGenres() {
        List<Zanr> zanrovi = zanrService.getAllGenres();

        if (zanrovi.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<ZanrDto> zanroviDto = new ArrayList<>();
        for (Zanr zanr : zanrovi) {
            ZanrDto zanrDto = new ZanrDto();
            zanrDto.setId(zanr.getId());
            zanrDto.setNaziv(zanr.getNaziv());
            zanroviDto.add(zanrDto);
        }

        return ResponseEntity.ok(zanroviDto);
    }

    @PostMapping("api/dodaj-žanr")
    public ResponseEntity<String> login(@RequestBody ZanrDto zanrDto, HttpSession session) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            return new ResponseEntity<>("Nisi administrator", HttpStatus.BAD_REQUEST);
        }

        List<Zanr> zanrovi = zanrService.getAllGenres();

        // Check if a genre with the given name already exists
        boolean exists = zanrovi.stream()
                .map(Zanr::getNaziv)
                .anyMatch(naziv -> naziv.equals(zanrDto.getNaziv()));

        if (exists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Žanr već postoji.");
        }

        zanrService.noviZanr(zanrDto);

        return ResponseEntity.ok("Dodat novi žanr");

    }

}
