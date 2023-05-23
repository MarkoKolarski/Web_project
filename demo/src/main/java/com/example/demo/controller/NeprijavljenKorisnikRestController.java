package com.example.demo.controller;

import com.example.demo.dto.KnjigaDto;
import com.example.demo.dto.KorisnikDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.model.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class NeprijavljenKorisnikRestController {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private KnjigaService knjigaService;

    @Autowired
    private RecenzijaService recenzijaService;

    @Autowired
    private ZanrService zanrService;

    @Autowired
    private PolicaService policaService;



    // Endpoint za prikaz svih knjiga
    @GetMapping("/knjige")
    public ResponseEntity<List<Knjiga>> getAllBooks() {
        List<Knjiga> knjige = knjigaService.getAllBooks();
        return ResponseEntity.ok(knjige);
    }





    // Endpoint za pregled svih recenzija
    @GetMapping("/recenzije")
    public ResponseEntity<List<Recenzija>> getAllReviews() {
        List<Recenzija> recenzije = recenzijaService.getAllReviews();
        return ResponseEntity.ok(recenzije);
    }

    // Endpoint za pregled svih žanrova
    @GetMapping("/zanrovi")
    public ResponseEntity<List<Zanr>> getAllGenres() {
        List<Zanr> zanrovi = zanrService.getAllGenres();
        return ResponseEntity.ok(zanrovi);
    }

    // Endpoint za pregled polica (liste knjiga) za određenog korisnika
    @GetMapping("/api/korisnici/{korisnikId}/police")
    public ResponseEntity<Set<Polica>> getUserBookshelf(@PathVariable Long korisnikId) throws ChangeSetPersister.NotFoundException {
        Set<Polica> knjige = policaService.getUserBookshelf(korisnikId);
        if (knjige.isEmpty()) {
            throw new PolicaService.UserNotFoundException("Korisnik sa ID-jem " + korisnikId + " nije pronađen.");
        }
        return ResponseEntity.ok(knjige);
    }

            //TODO Zavrisi funkciju

    // Endpoint za pregled recenzija za određenu knjigu
//    @GetMapping("/books/{bookId}/reviews")
//    public ResponseEntity<List<Recenzija>> getBookReviews(@PathVariable Long bookId) {
//        List<Recenzija> reviews = recenzijaService.getReviewsByBookId(bookId);
//        return ResponseEntity.ok(reviews);
//    }




    @GetMapping("/knjige-po-naslovu")
    public ResponseEntity<List<KnjigaDto>> searchBooks1(@RequestParam("query") String query) {
        List<KnjigaDto> knjige = knjigaService.searchBooks(query);
        return (ResponseEntity.ok(knjige));
    }


    @PostMapping("api/registracija")
    public ResponseEntity<String> signUp(@RequestBody KorisnikDto korisnikDto) {
        if (!korisnikDto.getLozinka().equals(korisnikDto.getPotvrdiLozinku())) {
            throw new RuntimeException("Passwords do not match.");
        }

        korisnikService.registerUser(korisnikDto);

        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("api/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpSession session){
        // proverimo da li su podaci validni
        if(loginDto.getUsername().isEmpty() || loginDto.getPassword().isEmpty())
            return new ResponseEntity("Invalid login data", HttpStatus.BAD_REQUEST);

        Korisnik loggedKorisnik = korisnikService.login(loginDto.getUsername(), loginDto.getPassword());
        if (loggedKorisnik == null)
            return new ResponseEntity<>("User does not exist!", HttpStatus.NOT_FOUND);

        session.setAttribute("korisnik", loggedKorisnik);
        return ResponseEntity.ok("Successfully logged in!");
    }

    @PostMapping("api/logout")
    public ResponseEntity Logout(HttpSession session){
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null)
            return new ResponseEntity("Forbidden", HttpStatus.FORBIDDEN);

        session.invalidate();
        return new ResponseEntity("Successfully logged out", HttpStatus.OK);
    }


    @GetMapping("/api/korisnici")
    public ResponseEntity<List<KorisnikDto>> getKorisnici(HttpSession session){
        List<Korisnik> korisnikList = korisnikService.findAll();

        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");
        if(loggedKorisnik == null) {
            System.out.println("Nema sesije");
        } else {
            System.out.println(loggedKorisnik);
        }

        List<KorisnikDto> dtos = new ArrayList<>();
        for(Korisnik korisnik : korisnikList){
            KorisnikDto dto = new KorisnikDto(korisnik);
            dtos.add(dto);
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/api/korisnici2")
    public ResponseEntity<List<Korisnik>> getKorisnici2(HttpSession session) {
        List<Korisnik> korisnikList = korisnikService.findAll();

        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (loggedKorisnik == null) {
            System.out.println("Nema sesije");
        } else {
            System.out.println(loggedKorisnik);
        }

        return ResponseEntity.ok(korisnikList);
    }

    @GetMapping("/api/korisnik/{id}")
    public Korisnik getKorisnik(@PathVariable(name = "id") Long id, HttpSession session){
        Korisnik korisnik = (Korisnik) session.getAttribute("user");
        session.invalidate();
        return korisnikService.findOne(id);
    }


    // Bez provere drugog parametara "HttpSession session", odnosno logina
    @GetMapping("/api/korisnik2/{id}")
    public Korisnik getKorisnik(@PathVariable(name = "id") Long id){
        return korisnikService.findOne(id);
    }


    @PostMapping("/api/save-korisnik")
    public String saveKorisnik(@RequestBody Korisnik korisnik) {
        this.korisnikService.save(korisnik);
        return "Successfully saved an employee!";
    }


}
