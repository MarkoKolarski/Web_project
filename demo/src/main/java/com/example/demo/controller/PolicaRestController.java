package com.example.demo.controller;


import com.example.demo.dto.KnjigaDto;
import com.example.demo.dto.KorisnikDto;
import com.example.demo.dto.PolicaDto;
import com.example.demo.model.Knjiga;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Polica;
import com.example.demo.model.Uloga;
import com.example.demo.service.PolicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
public class PolicaRestController {

    @Autowired
    private PolicaService policaService;


    // Endpoint za pregled polica (liste knjiga) za određenog korisnika
    @GetMapping("/api/korisnici/{korisnikId}/police")
    public ResponseEntity<Set<PolicaDto>> getUserBookshelf(@PathVariable Long korisnikId) throws ChangeSetPersister.NotFoundException {
        Set<Polica> police = policaService.getUserBookshelf(korisnikId);

        if (police.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }


        Set<PolicaDto> policeDto = new HashSet<>();
        for (Polica polica : police) {
            PolicaDto policaDto = new PolicaDto(polica);
            policeDto.add(policaDto);
        }

        return ResponseEntity.ok(policeDto);
    }


    @PostMapping("api/dodaj-policu")
    public ResponseEntity<String> login(@RequestBody PolicaDto policaDto, HttpSession session) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }

//        if (loggedKorisnik.getUloga() != Uloga.CITALAC && loggedKorisnik.getUloga() != Uloga.AUTOR) {
//            return new ResponseEntity<>("Nisi citalac ili autor.", HttpStatus.BAD_REQUEST);
//        }

        if (policaDto == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti podatke.");
        }

        Polica polica = new Polica();
        polica.setNaziv(policaDto.getNaziv());
        polica.setPrimarna(policaDto.getPrimarna());
        polica.setStavkePolice(policaDto.getStavkePolice());


        loggedKorisnik.getPolice().add(polica);

        //policaService.novaPolica(policaDto);

        return ResponseEntity.ok("Dodata nova polica.");

    }
    // Knjiga existingKnjiga = knjigaService.findByISBN(knjigaDto.getISBN());
//        knjigaService.promeniKnjigu(existingKnjiga, knjigaDto );

    // Optional<Polica> existingPolica = PolicaService.findById(policaDto.getId());

}
