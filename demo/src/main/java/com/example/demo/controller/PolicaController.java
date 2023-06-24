package com.example.demo.controller;

import com.example.demo.dto.PolicaDto;
import com.example.demo.model.*;
import com.example.demo.service.KnjigaService;
import com.example.demo.service.KorisnikService;
import com.example.demo.service.PolicaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class PolicaController {
    @Autowired
    private PolicaService policaService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private KnjigaService knjigaService;


    @GetMapping("/korisnici/{korisnicko_ime}/police-po-imenu")
    public String getUserBookshelf(@PathVariable String korisnicko_ime, Model model) {

        if (korisnicko_ime.isEmpty()) {
            model.addAttribute("errorMessage", "Korisnik sa datim korisničkim imenom ne postoji.");
            return "error";
        }

        Set<Polica> police = policaService.policaPoNazivu(korisnicko_ime);

        if (police.isEmpty()) {
            model.addAttribute("errorMessage", "Korisnik sa datim korisničkim imenom ne postoji.");
            return "error";
        }

        Set<PolicaDto> policeDto = new HashSet<>();
        for (Polica polica : police) {
            PolicaDto policaDto = new PolicaDto(polica);
            policeDto.add(policaDto);
        }

        model.addAttribute("policeDto", policeDto);
        return "police"; // Return the name of the user bookshelf view
    }


    @GetMapping("/dodaj-policu")
    public String getDodajPolicu(HttpSession session, Model model) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            // Redirekcija na odgovarajuću stranicu za prijavu
            return "error";
        }

        model.addAttribute("policaDto", new PolicaDto());
        return "dodaj-policu";
    }

    @RequestMapping(value = "dodaj-policu", method = RequestMethod.POST)
    public String dodajPolicu(@ModelAttribute("policaDto") PolicaDto policaDto, HttpSession session) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            ModelAndView forbiddenModel = new ModelAndView("forbidden");
            forbiddenModel.addObject("message", "Niste ulogovani.");
            return "error";
        }

        if (policaDto == null) {
            ModelAndView forbiddenModel = new ModelAndView("forbidden");
            forbiddenModel.addObject("message", "Morate uneti podatke.");
            return "error";
        }

        if (loggedKorisnik.getPolice().stream().anyMatch(polica -> polica.getNaziv().equals(policaDto.getNaziv()))) {
            ModelAndView forbiddenModel = new ModelAndView("forbidden");
            forbiddenModel.addObject("message", "Polica sa tim imenom već postoji.");
            return "error";
        }

        Polica polica = new Polica();
        polica.setNaziv(policaDto.getNaziv());
        polica.setPrimarna(false);
        polica.setStavkePolice(policaDto.getStavkePolice());

        loggedKorisnik.getPolice().add(polica);
        korisnikService.save(loggedKorisnik);

        ModelAndView successModel = new ModelAndView("success");
        successModel.addObject("message", "Dodata nova polica.");
        return "uspesno-dodaj-policu";
    }


    @GetMapping("/polica-dodaj-knjigu")
    public String getPolicaDodajKnjiguPage(Model model, HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error";
        }

        return "polica-dodaj-knjigu"; // Return the name of the view template for the polica-dodaj-knjigu page
    }

    @PostMapping("/polica-dodaj-knjigu")
    public String updateBook(@RequestParam("isbn") String isbn, @RequestParam("naziv") String naziv_police, HttpSession session, Model model) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error";
        }

        if (isbn == null) {
            model.addAttribute("message", "Morate uneti ISBN knjige.");
            return "error";
        }

        Knjiga existingKnjiga = knjigaService.findByISBN(isbn);
        Polica existingPolica = policaService.findByNaziv(naziv_police);

        if (existingKnjiga == null) {
            model.addAttribute("message", "Knjiga sa datim ISBN ne postoji.");
            return "error";
        }

        if (existingPolica == null) {
            model.addAttribute("message", "Polica sa datim nazivom ne postoji.");
            return "error";
        }

        boolean isBookOnPrimaryPolica = policaService.isBookOnPrimaryPolica(existingKnjiga, loggedKorisnik);

        if (!isBookOnPrimaryPolica) {
            model.addAttribute("message", "Knjiga mora biti dodata na jednu od primarnih polica pre nego što je možete dodati na sopstvene police.");
            return "error";
        }

        boolean isBookOnPrimaryPolicaForLoggedUser = policaService.isBookOnPrimaryPolicaForLoggedUser(existingKnjiga, loggedKorisnik, existingPolica);

        if (isBookOnPrimaryPolicaForLoggedUser) {
            model.addAttribute("message", "Knjiga se ne sme dva puta dodati na primarnu policu.");
            return "error";
        }

        boolean bookExist = policaService.dodajKnjigu(existingKnjiga, existingPolica);

        if (bookExist) {
            model.addAttribute("message", "Knjiga " + existingKnjiga.getNaslov() + " već postoji u polici: " + existingPolica.getNaziv() + ", pokušajte ponovo.");
            return "error";
        }

        model.addAttribute("message", "Knjiga " + existingKnjiga.getNaslov() + " je uspešno dodata u policu: " + existingPolica.getNaziv());
        return "uspesno-dodaj-knjigu";
    }






}


