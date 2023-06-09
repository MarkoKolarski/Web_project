package com.example.demo.controller;

import com.example.demo.dto.KnjigaDto;

import com.example.demo.model.Autor;
import com.example.demo.model.Knjiga;

import com.example.demo.model.Korisnik;
import com.example.demo.model.Uloga;
import com.example.demo.service.AutorService;
import com.example.demo.service.KnjigaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class KnjigaRestController {

    @Autowired
    private KnjigaService knjigaService;

    @Autowired
    private AutorService autorService;





    // Endpoint za prikaz svih knjiga
    @GetMapping("/api/knjige")
    public ResponseEntity<List<KnjigaDto>> getAllBooks() {
        List <Knjiga> knjige = knjigaService.getAllBooks2();

        if (knjige.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<KnjigaDto> knjigeDto = new ArrayList<>();
        for (Knjiga knjiga : knjige) {
            KnjigaDto knjigaDto = new KnjigaDto();
            knjigaDto.setId(knjiga.getId());
            knjigaDto.setNaslov(knjiga.getNaslov());
            knjigaDto.setNaslovnaFotografija(knjiga.getNaslovnaFotografija());
            knjigaDto.setISBN(knjiga.getISBN());
            knjigaDto.setDatumObjavljivanja(knjiga.getDatumObjavljivanja());
            knjigaDto.setBrojStrana(knjiga.getBrojStrana());
            knjigaDto.setOpis(knjiga.getOpis());
            knjigaDto.setZanrovi(knjiga.getZanrovi());
            knjigaDto.setOcena(knjiga.getOcena());
            knjigeDto.add(knjigaDto);
        }

        return ResponseEntity.ok(knjigeDto);
    }


    @GetMapping("/api/{naslov}")
    public ResponseEntity<Set<KnjigaDto>> searchBooks1(@PathVariable(name = "naslov") String naslov) {
        Set <KnjigaDto> knjigeDto = (Set<KnjigaDto>) knjigaService.searchBooks(naslov);

        if (knjigeDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }



        return ResponseEntity.ok(knjigeDto);
    }

    @PostMapping("api/dodaj-knjigu")
    public ResponseEntity<String> dodajKnjigu(@RequestBody KnjigaDto knjigaDto, HttpSession session) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            return new ResponseEntity<>("Nisi administrator.", HttpStatus.BAD_REQUEST);
        }

        if (knjigaDto == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti podatke.");
        }

        if (knjigaDto.getISBN() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti ISBN.");
        }

        List<Knjiga> knjige = knjigaService.getAllBooks2();

        // Check if a book with the given title already exists
        boolean exists = knjige.stream()
                .map(Knjiga::getNaslov)
                .anyMatch(naslov -> naslov.equals(knjigaDto.getNaslov()));

        if (exists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Knjiga već postoji.");
        }

        knjigaService.novaKnjiga(knjigaDto);

        return ResponseEntity.ok("Dodata nova knjiga.");

    }

    @PutMapping("api/izmeni-knjigu")
    public ResponseEntity<String> updateBook(@RequestBody KnjigaDto knjigaDto, HttpSession session)  {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            return new ResponseEntity<>("Nisi administrator.", HttpStatus.BAD_REQUEST);
        }

        if (knjigaDto == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti podatke.");
        }

        if (knjigaDto.getISBN() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti ISBN.");
        }

        Knjiga existingKnjiga = knjigaService.findByISBN(knjigaDto.getISBN());

        if (existingKnjiga == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Knjiga sa datim ISBN ne postoji.");
        }


        knjigaService.promeniKnjigu(existingKnjiga, knjigaDto);

        return ResponseEntity.ok("Knjiga je uspešno ažurirana.");
    }

    @DeleteMapping("/api/obrisi-knjigu")
    public ResponseEntity<String> obrisiKnjigu(@RequestParam("isbn") String isbn, @RequestParam("korisnickoIme") String korisnickoIme, HttpSession session) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            return new ResponseEntity<>("Nisi administrator.", HttpStatus.BAD_REQUEST);
        }

        if (isbn == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti ISBN.");
        }

        knjigaService.obrisiKnjiguPoISBN(isbn, korisnickoIme);

        return ResponseEntity.ok("Knjiga obrisana.");
    }

    @PostMapping("api/autor-dodaj-knjigu")
    public ResponseEntity<String> autorDodajKnjigu(@RequestBody KnjigaDto knjigaDto, HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }

        Optional<Autor> autor = autorService.AutorById(loggedKorisnik.getId());

        Autor loggedAutor = autor.get();

        if (loggedAutor.getUloga() != Uloga.AUTOR) {
            return new ResponseEntity<>("Nisi autor.", HttpStatus.BAD_REQUEST);
        }

        if (knjigaDto == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti podatke.");
        }

        if (knjigaDto.getISBN() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti ISBN.");
        }
        Knjiga existingKnjiga = knjigaService.findByISBN(knjigaDto.getISBN());

        if (existingKnjiga == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Nije pronadjena knjiga");
        }

       boolean postojiKnjiga = knjigaService.novaKnjigaAutoru(existingKnjiga, loggedAutor);

        if (postojiKnjiga) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Knjiga se ne sme dva puta dodati na spisak.");
        }

        return ResponseEntity.ok("Dodata nova knjiga autoru: "+loggedAutor.getKorisnickoIme()+ " .");

    }

    @PutMapping("api/izmeni-knjigu-autora")
    public ResponseEntity<String> izmeniKnjiguAutora(@RequestBody KnjigaDto knjigaDto, HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }

        if (loggedKorisnik.getUloga() != Uloga.AUTOR) {
            return new ResponseEntity<>("Nisi autor.", HttpStatus.BAD_REQUEST);
        }

        Optional<Autor> autor = autorService.AutorById(loggedKorisnik.getId());

        Autor loggedAutor = autor.get();

        if (knjigaDto == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti podatke.");
        }

        if (knjigaDto.getISBN() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti ISBN.");
        }

        Knjiga existingKnjiga = knjigaService.findByISBN(knjigaDto.getISBN());

        if (existingKnjiga == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Knjiga sa datim ISBN ne postoji.");
        }


        knjigaService.promeniKnjiguAutora(existingKnjiga, knjigaDto, loggedAutor );

        return ResponseEntity.ok("Knjiga je uspešno ažurirana.");
    }

}
