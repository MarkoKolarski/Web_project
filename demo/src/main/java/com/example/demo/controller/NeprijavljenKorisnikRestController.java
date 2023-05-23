package com.example.demo.controller;

import com.example.demo.dto.KnjigaDto;
import com.example.demo.dto.KorisnikDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.model.*;
import com.example.demo.repository.StavkaPoliceRepository;
import com.example.demo.service.*;
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

    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;

    @Autowired
    private AutorService autorService;

//  Neprijavljeni korisnik može još i da podnese zahtev za aktivaciju naloga autora, o
//  čemu će biti reči posle.//TODO

//  Administratori se programski učitavaju iz baze i ne mogu se naknadno dodati.
//  Autora može kreirati samo administrator. Čitalac ne može da postane autor.

    // Zahtev za aktivaciju naloga autora: dodat profil autora na koji se odnosi zahtev za aktivaciju naloga.

//  za sada ne radi šta treba !!!! //TODO
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody Autor autor) {
        try {
            if (autor.getUloga() == Uloga.AUTOR) {
                autorService.createAutor(autor);
            }
            // Save the user to the database or perform other necessary operations
            // ...

            return ResponseEntity.ok("User created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
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


    // Endpoint za prikaz svih knjiga
    @GetMapping("/api/knjige")
    public ResponseEntity<List<Knjiga>> getAllBooks() {
        List<Knjiga> knjige = knjigaService.getAllBooks();
        return ResponseEntity.ok(knjige);
    }

    // Endpoint za pregled svih recenzija
    @GetMapping("/api/recenzije")
    public ResponseEntity<List<Recenzija>> getAllReviews() {
        List<Recenzija> recenzije = recenzijaService.getAllReviews();
        return ResponseEntity.ok(recenzije);
    }

    // Endpoint za pregled svih žanrova
    @GetMapping("/api/zanrovi")
    public ResponseEntity<List<Zanr>> getAllGenres() {
        List<Zanr> zanrovi = zanrService.getAllGenres();
        return ResponseEntity.ok(zanrovi);
    }

    // Endpoint za pregled polica (liste knjiga) za određenog korisnika
    @GetMapping("/api/korisnici/{korisnikId}/police")
    public ResponseEntity<Set<Polica>> getUserBookshelf(@PathVariable Long korisnikId) throws ChangeSetPersister.NotFoundException {
        Set<Polica> police = policaService.getUserBookshelf(korisnikId);
        if (police.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(police);
    }


    @GetMapping("/api/knjiga/{knjigaId}")
    public ResponseEntity<List<Recenzija>> getRecenzijeByKnjigaId(@PathVariable Long knjigaId) {
        Optional<StavkaPolice> stavkaPoliceOptional = stavkaPoliceRepository.findByKnjiga_Id(knjigaId);
        if (stavkaPoliceOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Set<Recenzija> recenzije = stavkaPoliceOptional.get().getRecenzije();
        return ResponseEntity.ok(new ArrayList<>(recenzije));
    }




    @GetMapping("/api/knjige-po-naslovu")
    public ResponseEntity<List<KnjigaDto>> searchBooks1(@RequestParam("query") String query) {
        List<KnjigaDto> knjige = knjigaService.searchBooks(query);
        return (ResponseEntity.ok(knjige));
    }




//    @PostMapping("api/logout")
//    public ResponseEntity Logout(HttpSession session){
//        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");
//
//        if (loggedKorisnik == null)
//            return new ResponseEntity("Forbidden", HttpStatus.FORBIDDEN);
//
//        session.invalidate();
//        return new ResponseEntity("Successfully logged out", HttpStatus.OK);
//    }


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
// Zbog dodatnog parametara koji se prikazuje, ova fukncija je uradjena bez dto podataka
    @GetMapping("/api/korisnici2-bez-dto")
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



}
