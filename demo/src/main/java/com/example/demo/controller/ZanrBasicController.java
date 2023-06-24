package com.example.demo.controller;

import com.example.demo.dto.ZanrDto;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Uloga;
import com.example.demo.model.Zanr;
import com.example.demo.service.ZanrService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ZanrBasicController {

    @Autowired
    private ZanrService zanrService;

    @GetMapping("/dodaj-zanr")
    public String showDodajZanrForm(Model model,HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            // Redirekcija na odgovarajuću stranicu za prijavu
            return "redirect:/login";
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            // Redirekcija na odgovarajuću stranicu za zabranu pristupa
            return "error";
        }

        model.addAttribute("zanrDto", new ZanrDto());
        return "dodaj-zanr";
    }


    @PostMapping("/dodaj-zanr")
    public String dodajZanr(@ModelAttribute("zanrDto") ZanrDto zanrDto, HttpSession session, Model model) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error";
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            model.addAttribute("errorMessage", "Nisi administrator.");
            return "error";
        }

        List<Zanr> zanrovi = zanrService.getAllGenres();

        // Check if a genre with the given name already exists
        boolean exists = zanrovi.stream()
                .map(Zanr::getNaziv)
                .anyMatch(naziv -> naziv.equals(zanrDto.getNaziv()));

        if (exists) {
            model.addAttribute("errorMessage", "Žanr već postoji.");
            return "error";
        }

        zanrService.noviZanr(zanrDto);

        model.addAttribute("successMessage", "Dodat novi žanr.");
        return "uspesno-zanr-dodat";
    }

}
