package com.example.demo.controller;

import com.example.demo.dto.AutorDto;
import com.example.demo.dto.PolicaDto;
import com.example.demo.dto.ZahtevDto;
import com.example.demo.model.*;
import com.example.demo.service.AutorService;
import com.example.demo.service.ZahtevService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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


    public void odobrenMejl(String lozinka, String email, String korisnickoIme) throws MessagingException {
        //zameniti email sa vec stavljenim email-om za slanje na dobru adresu
        autorService.sendSimpleEmail("marko.kolarski.02@gmail.com",
                "Autor: " +korisnickoIme+ " je odobren",
                "Vaš zahtev za aktivaciju autora je odobren. \n" +
                        "Lozinka je: " +lozinka);

    }

    public void odbijenMejl(String email, String korisnickoIme) throws MessagingException {
        //zameniti email sa vec stavljenim email-om za slanje na dobru adresu
        autorService.sendSimpleEmail("marko.kolarski.02@gmail.com",
                "Autor: " +korisnickoIme+ " je odbijen",
                "Vaš zahtev za aktivaciju autora je odbijen.");

    }
    @PostMapping("api/odobri-autora")
    public ResponseEntity<String> odobriAutora(@RequestBody ZahtevDto zahtevDto, HttpSession session) throws MessagingException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            return new ResponseEntity<>("Nisi administrator", HttpStatus.BAD_REQUEST);
        }

        AutorDto autorDto = zahtevService.getZahtev(zahtevDto.getId());

        if(autorDto == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Autor ili zahtev ne postoji!");
        }

        Optional<ZahtevZaAktivacijuNalogaAutora> existingZahtev = zahtevService.findById(zahtevDto.getId());


        if(existingZahtev.equals(null)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Zahtev ne postoji!");
        }


        if(existingZahtev.get().getStatus() == Status.ODOBREN){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Zahtev je vec odobren!");
        }
       // ZahtevZaAktivacijuNalogaAutora getexistingZahtev = existingZahtev.get();

        if (zahtevDto.getStatus() == Status.ODOBREN) {
            if(autorDto.getAktivan() == false) {



                existingZahtev.get().setStatus(Status.ODOBREN);
                zahtevService.save(existingZahtev.get());

                autorDto.setAktivan(true);


                Polica polica1 = new Polica();
                polica1.setNaziv("WantToRead");
                polica1.setPrimarna(true);

                Polica polica2 = new Polica();
                polica2.setNaziv("CurrentlyReading");
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


                odobrenMejl(autorDto.getLozinka(), zahtevDto.getEmail(), autorDto.getKorisnickoIme());

                return ResponseEntity.ok("Autor je uspesno aktiviran!");
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Autor je već aktiviran");

        } else {

            // Reject the request and send an email notification
            existingZahtev.get().setStatus(Status.ODBIJEN);
            zahtevService.save(existingZahtev.get());

            // Send a rejection email to the provided email address
//            String email = zahtevDto.getEmail();
//            sendRejectionEmail(email);
                odbijenMejl(zahtevDto.getEmail(), autorDto.getKorisnickoIme());

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Autor je odbijen!");
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

    @PostMapping("/api/dodaj-autora")
    public ResponseEntity<String> dodajAutora(@RequestBody AutorDto autorDto, HttpSession session) {
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

        Autor noviAutor = autorService.dodajAutora(autorDto);

        if (noviAutor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor sa datim korisničkim imenom ne postoji.");
        }

        if (noviAutor.getAktivan() == true) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor sa datim korisničkim imenom je već aktiviran.");
        }

        autorService.promeniAutora(noviAutor, autorDto);

        return ResponseEntity.ok("Autor: " + noviAutor.getKorisnickoIme() +  " je uspešno dodat.");
    }

}