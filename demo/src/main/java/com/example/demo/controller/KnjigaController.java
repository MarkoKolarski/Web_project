package com.example.demo.controller;

import com.example.demo.dto.KnjigaDto;
import com.example.demo.model.Knjiga;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Uloga;
import com.example.demo.service.KnjigaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import java.text.AttributedString;
import java.util.List;
import java.util.Set;

@Controller
public class KnjigaController {

    @Autowired
    private KnjigaService knjigaService;


    @Autowired
    private HttpSession session;



    @GetMapping("/knjige")
    public String getAllBooks(Model model) {
        List<Knjiga> knjige = knjigaService.getAllBooks2();
        model.addAttribute("knjige", knjige);
        return "knjige";
    }

    /*@GetMapping("/pretraga-knjige")
    public String showPretragaKnjigePage() {
        return "pretraga-knjige";
    }


    @GetMapping("/rezultati-pretrage")
    public String searchBooksByTitle(@RequestParam(name = "naslov") String naslov, Model model) {
        Set<KnjigaDto> knjigeDto = knjigaService.searchBooks(naslov);
        model.addAttribute("knjigeDto", knjigeDto);
        return "rezultati-pretrage";
    }*/

    @GetMapping("/pretraga-knjige")
    public String searchBooksByTitle(@RequestParam(name = "naslov") String naslov, HttpSession session) {
        Set<KnjigaDto> rezultati = knjigaService.searchBooks(naslov);

        if (rezultati.isEmpty()) {
            session.setAttribute("errorMessage", "Knjige nisu pronađene");
            return "knjige-not-found"; // Kreirajte odgovarajuću stranicu za prikaz "Knjige nisu pronađene"
        }

        session.setAttribute("knjigeDto", rezultati);
        return "pretraga-knjige";
    }


    @PostMapping("/dodaj-knjigu")
    public String dodajKnjigu(@ModelAttribute KnjigaDto knjigaDto, HttpSession session) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        System.out.println("Naslov: " + knjigaDto.getNaslov());
        System.out.println("ISBN: " + knjigaDto.getISBN());


        if (loggedKorisnik == null) {
            return "login";
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            return "login";
        }

        if (knjigaDto == null) {
            return "login";
        }

        if (knjigaDto.getISBN() == null || knjigaDto.getISBN().isEmpty() ||
                knjigaDto.getNaslov() == null || knjigaDto.getNaslov().isEmpty()) {
            return "dodaj-knjigu";
        }

        List<Knjiga> knjige = knjigaService.getAllBooks2();

        // Provera da li knjiga sa datim naslovom već postoji
        boolean exists = knjige.stream()
                .anyMatch(knjiga -> knjiga.getNaslov().equals(knjigaDto.getNaslov()));

        if (exists) {
            return "dodaj-knjigu";
        }

        knjigaService.novaKnjiga(knjigaDto);

        return "uspesna-dodata-knjiga";
    }

    @GetMapping("/dodaj-knjigu")
    public String prikaziFormuDodajKnjigu(Model model, HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            // Redirekcija na odgovarajuću stranicu za prijavu
            return "redirect:/login";
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            // Redirekcija na odgovarajuću stranicu za zabranu pristupa
            return "redirect:/";
        }

        model.addAttribute("knjigaDto", new KnjigaDto());

        return "dodaj-knjigu";
    }





}
