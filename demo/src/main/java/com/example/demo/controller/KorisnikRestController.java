package com.example.demo.controller;

import com.example.demo.dto.KorisnikDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.model.Autor;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Uloga;
import com.example.demo.service.AutorService;
import com.example.demo.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class KorisnikRestController {



    @Autowired
    private KorisnikService korisnikService;



    @PostMapping("api/registracija")
    public ResponseEntity<String> signUp(@RequestBody KorisnikDto korisnikDto) {

        if (korisnikDto == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste uneli podatke.");
        }

        if (!korisnikDto.getLozinka().equals(korisnikDto.getPotvrdiLozinku())) {
            throw new RuntimeException("Lozinke nisi iste.");
        }

        korisnikService.registerUser(korisnikDto);

        return ResponseEntity.ok("Uspešna registracija.");
    }

    @PostMapping("api/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpSession session){
        // proverimo da li su podaci validni
        if(loginDto.getUsername().isEmpty() || loginDto.getPassword().isEmpty())
            return new ResponseEntity("Pogrešni login podaci", HttpStatus.BAD_REQUEST);

        Korisnik loggedKorisnik = korisnikService.login(loginDto.getUsername(), loginDto.getPassword());
        if (loggedKorisnik == null)
            return new ResponseEntity<>("Korisnik ne postoji!", HttpStatus.NOT_FOUND);

        session.setAttribute("korisnik", loggedKorisnik);
        return ResponseEntity.ok("Uspešna prijava");
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
