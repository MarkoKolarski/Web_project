package com.example.demo.controller;

import com.example.demo.dto.KorisnikDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Polica;
import com.example.demo.service.KorisnikService;
import com.example.demo.service.PolicaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private PolicaService policaService;

    @Autowired
    private HttpSession session;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/registracija")
    public String showRegistrationForm(Model model) {
        model.addAttribute("korisnikDto", new KorisnikDto());
        return "registracija";
    }
    @PostMapping("/registracija")
    public String signUp(@ModelAttribute("korisnikDto") KorisnikDto korisnikDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registracija";
        }

        if (!korisnikDto.getLozinka().equals(korisnikDto.getPotvrdiLozinku())) {
            bindingResult.rejectValue("potvrdiLozinku", "error.korisnikDto", "Lozinke se ne podudaraju");
            return "registracija";
        }

        try {
            korisnikService.registerUser(korisnikDto);
        } catch (Exception e) {
            bindingResult.rejectValue("email", "error.korisnikDto", e.getMessage());
            return "registracija";
        }

        return "redirect:/uspesna-registracija";
    }

    @GetMapping("/uspesna-registracija")
    public String successfulRegistration() {
        return "uspesna-registracija";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("loginDto") LoginDto loginDto, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        loginDto.setUsername(loginDto.getUsername());
        loginDto.setPassword(loginDto.getPassword());
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        // Proverimo da li su podaci validni
        if (loginDto.getUsername().isEmpty() || loginDto.getPassword().isEmpty()) {
            bindingResult.rejectValue("username", "error.loginDto", "Pogrešni login podaci");
            return "login";
        }
        Korisnik loggedKorisnik = korisnikService.login(loginDto.getUsername(), loginDto.getPassword());
        if (loggedKorisnik == null) {
            bindingResult.rejectValue("username", "error.loginDto", "Korisnik ne postoji!");
            return "autori";
        }

        session.setAttribute("korisnik", loggedKorisnik);

        return "redirect:/pocetna";
    }


    @GetMapping("/logout")
    public String showLogout(HttpSession session) {
        session.invalidate();
        return "logout";
    }


    @GetMapping("/pocetna")
    public String home() {
        return "pocetna";
    }

    @GetMapping("/korisnici")
    public String prikaziKorisnike(Model model, HttpSession session) {
        List<Korisnik> korisnikList = korisnikService.findAll();

        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (loggedKorisnik == null) {
            System.out.println("Nema sesije");
        } else {
            System.out.println(loggedKorisnik);
        }

        model.addAttribute("korisnici", korisnikList);
        return "korisnici"; // Promijenite ovo prema vašem template imeniku
    }

    @GetMapping("/{id}")
    public String prikaziProfil(@PathVariable("id") Long id, HttpSession session, Model model) {
        // Proverite da li je korisnik prijavljen
        if (session.getAttribute("korisnik") == null) {
            return "redirect:/login"; // Ako korisnik nije prijavljen, preusmerite ga na stranicu za prijavu
        }

        // Uzmi korisnika iz sesije
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        // Dodaj korisnika na model kako biste ga prikazali u predlošku (View)
        model.addAttribute("korisnik", korisnik);

        // Vratite ime predloška (View) koji prikazuje profil korisnika
        return "korisnik/{id}";
    }

    @GetMapping("/police-korisnika")
    public Set<Polica> prikaziPoliceKorisnika(HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return null;
        }

        Set<Polica> police = policaService.getUserBookshelf(loggedKorisnik.getId());
        return police;
    }




   /* @GetMapping("/izmeni-korisnika")
    public String prikaziFormuIzmene(Model model, HttpSession session) {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        // Provera da li je korisnik prijavljen
        if (korisnik == null) {
            return "redirect:/login"; // Preusmeravanje na stranicu za prijavu ako korisnik nije prijavljen
        }

        // Dodavanje korisnika na model kako bi bio dostupan u predlošku
        model.addAttribute("korisnik", korisnik);
        model.addAttribute("korisnikDto", new KorisnikDto());

        return "uspesna-izmena-korisnika";
    }

    @PostMapping("/izmeni-korisnika")
    public String izmeniKorisnika(@ModelAttribute("korisnikDto") KorisnikDto korisnikDto, HttpSession session) {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");

        // Provera da li je korisnik prijavljen
        if (korisnik == null) {
            return "redirect:/login"; // Preusmeravanje na stranicu za prijavu ako korisnik nije prijavljen
        }

        // Izmena podataka korisnika
        korisnik.setIme(korisnikDto.getIme());
        korisnik.setPrezime(korisnikDto.getPrezime());
        korisnik.setKorisnickoIme(korisnikDto.getKorisnickoIme());
        korisnik.setMejlAdresa(korisnikDto.getMejlAdresa());

        // Poziv servisa za izmenu korisnika
        korisnikService.promeniKorisnika(korisnik, korisnikDto);

        return "redirect:/uspesna-izmena-korisnika";
    }*/
}

