package com.example.demo.controller;

import com.example.demo.dto.RecenzijaDto;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Recenzija;
import com.example.demo.service.RecenzijaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("api/dodaj-recenziju")
    public ResponseEntity<String> dodajRecenziju(@RequestBody RecenzijaDto recenzijaDto, HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");



        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }

        if (recenzijaDto == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti podatke.");
        }

//        if (knjigaDto.getISBN() == null) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti ISBN.");
//        }

        recenzijaService.novaRecenzija(recenzijaDto, loggedKorisnik);

        return ResponseEntity.ok("Dodata nova recenzija.");

    }

    @PutMapping("api/izmeni-recenziju")
    public ResponseEntity<String> updateBook(@RequestBody RecenzijaDto recenzijaDto, HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }


        if (recenzijaDto == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti podatke.");
        }

//        if (knjigaDto.getISBN() == null) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti ISBN.");
//        }
        //Jedino unique u recenziji je id, pa mora po njemu
        Optional <Recenzija> existingRecenzija = recenzijaService.findById(recenzijaDto.getId());
        Recenzija recenzija = existingRecenzija.get();


        if (recenzija == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Knjiga sa datim ISBN ne postoji.");
        }


        recenzijaService.promeniRecenziju(recenzija, recenzijaDto, loggedKorisnik );

        return ResponseEntity.ok("Recenzija je uspešno ažurirana.");
    }



}
