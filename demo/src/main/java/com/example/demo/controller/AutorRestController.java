package com.example.demo.controller;

import com.example.demo.dto.AutorDto;
import com.example.demo.dto.PolicaDto;
import com.example.demo.dto.Zahtev2Dto;
import com.example.demo.model.*;
import com.example.demo.service.AutorService;
import com.example.demo.service.ZahtevService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class AutorRestController {

    @Autowired
    private ZahtevService zahtevService;

    @Autowired
    private AutorService autorService;


    @PostMapping("api/odobri-autora2")
    public ResponseEntity<String> odobriAutora(@RequestBody Zahtev2Dto zahtev2Dto, HttpSession session) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            return new ResponseEntity<>("Nisi administrator", HttpStatus.BAD_REQUEST);
        }

        AutorDto autorDto = zahtevService.getZahtev(zahtev2Dto.getId());

        if (zahtev2Dto.getStatus() == Status.ODOBREN) {
            if(autorDto.getAktivan() == false) {
                autorDto.setAktivan(true);


                Polica polica1 = new Polica();
                polica1.setNaziv("Want to Read");
                polica1.setPrimarna(true);

                Polica polica2 = new Polica();
                polica2.setNaziv("Currently Reading");
                polica2.setPrimarna(true);

                Polica polica3 = new Polica();
                polica3.setNaziv("Read");
                polica3.setPrimarna(true);

                Set<Polica> police = new HashSet<>();
                police.add(polica1);
                police.add(polica2);
                police.add(polica3);


                Set<PolicaDto> policaDtos = new HashSet<>();
                for (Polica polica : police) {
                    PolicaDto policaDto = new PolicaDto();
                    policaDto.setId(polica.getId());
                    policaDto.setNaziv(polica.getNaziv());
                    policaDto.setPrimarna(polica.getPrimarna());
                    policaDto.setStavkePolice(polica.getStavkePolice());
                    policaDtos.add(policaDto);
                }

                autorDto.setPolice(police);
                autorDto.setAktivan(true);


                autorDto.setPolice(police);


                Optional<Autor> optionalAutor = autorService.AutorById(autorDto.getId());
                if (optionalAutor.isPresent()) {
                    Autor autor = optionalAutor.get();
                    autor.setAktivan(true);
                    autor.setPolice(police);
                    autorService.save(autor);
                }

                return ResponseEntity.ok("Autor je uspesno aktiviran!");
            }
            return ResponseEntity.ok("Autor je već aktiviran");

        } else {
            return ResponseEntity.ok("Autor je odbijen!");
        }
    }


    @GetMapping("/api/autori")
    public ResponseEntity<List<AutorDto>> getAllAutors() {
        List <Autor> autori = autorService.getAllAutors();

        if (autori.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<AutorDto> autoriDto = new ArrayList<>();
        for (Autor autor : autori) {
            AutorDto autorDto = new AutorDto();
            autorDto.setId(autor.getId());
            autorDto.setAktivan(autor.getAktivan());
            autorDto.setIme(autor.getIme());
            autorDto.setPrezime(autor.getPrezime());
            autorDto.setKorisnickoIme(autor.getKorisnickoIme());
            autorDto.setMejlAdresa(autor.getMejlAdresa());
            autorDto.setLozinka(autor.getLozinka());
            autorDto.setDatumRodjenja(autor.getDatumRodjenja());
            autorDto.setProfilnaSlika(autor.getProfilnaSlika());
            autorDto.setOpis(autor.getOpis());
            autorDto.setUloga(autor.getUloga());
            autorDto.setPolice(autor.getPolice());
            autoriDto.add(autorDto);
        }

        return ResponseEntity.ok(autoriDto);
    }

    @PutMapping("/api/izmeni-autora")
    public ResponseEntity<String> izmeniAutora(@RequestBody AutorDto autorDto, HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            return new ResponseEntity<>("Nisi administrator.", HttpStatus.BAD_REQUEST);
        }

        if (autorDto == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti podatke.");
        }

        if (autorDto.getKorisnickoIme() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti korisničko ime.");
        }

        Autor existingAutor = autorService.AutorBykorisnickoIme(autorDto.getKorisnickoIme());

        if (existingAutor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor sa datim korisničkim imenom ne postoji.");
        }

        if (existingAutor.getAktivan() == true) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor sa datim korisničkim imenom je već aktiviran.");
        }

        autorService.promeniAutora(existingAutor, autorDto);

        return ResponseEntity.ok("Autor " + existingAutor.getKorisnickoIme() +  " je uspešno ažuriran.");
    }

}