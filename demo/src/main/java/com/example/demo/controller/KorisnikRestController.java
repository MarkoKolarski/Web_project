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




    //  Neprijavljeni korisnik može još i da podnese zahtev za aktivaciju naloga autora, o
//  čemu će biti reči posle.//TODO

//  Administratori se programski učitavaju iz baze i ne mogu se naknadno dodati.
//  Autora može kreirati samo administrator. Čitalac ne može da postane autor.

    // Zahtev za aktivaciju naloga autora: dodat profil autora na koji se odnosi zahtev za aktivaciju naloga.

    //  za sada ne radi šta treba !!!! //TODO


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
