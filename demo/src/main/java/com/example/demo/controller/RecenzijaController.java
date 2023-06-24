package com.example.demo.controller;


import ch.qos.logback.core.model.Model;
import com.example.demo.dto.RecenzijaDto;
import com.example.demo.model.Knjiga;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Recenzija;
import com.example.demo.service.RecenzijaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecenzijaController {

    @Autowired
    private RecenzijaService recenzijaService;


    @GetMapping("/recenzije")
    public String getAllReviews(org.springframework.ui.Model model) {
        List<RecenzijaDto> recenzijeDto = new ArrayList<>();
        List<Recenzija> recenzije = recenzijaService.getAllReviews();
        for (Recenzija recenzija : recenzije) {
            RecenzijaDto recenzijaDto = new RecenzijaDto();
            recenzijaDto.setId(recenzija.getId());
            recenzijaDto.setOcena(recenzija.getOcena());
            recenzijaDto.setTekst(recenzija.getTekst());
            recenzijaDto.setDatumRecenzije(recenzija.getDatumRecenzije());
            recenzijaDto.setKorisnik(recenzija.getKorisnik());

            recenzijeDto.add(recenzijaDto);
        }


        model.addAttribute("recenzije", recenzijeDto);
        return "recenzije";
    }


    @GetMapping("/dodaj-recenziju")
    public String dodajRecenzijuForm(ModelMap model, HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error";
        }
        model.addAttribute("recenzijaDto", new RecenzijaDto());
        return "dodaj-recenziju";
    }

    @PostMapping("/dodaj-recenziju")
    public String dodajRecenziju(@ModelAttribute("recenzijaDto") RecenzijaDto recenzijaDto, HttpSession session, ModelMap model) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error";
        }

        if (recenzijaDto == null) {
            model.addAttribute("message", "Morate uneti podatke.");
            return "error";
        }

        recenzijaService.novaRecenzija(recenzijaDto, loggedKorisnik);

        model.addAttribute("message", "Dodata nova recenzija.");
        return "uspesno-dodaj-recenziju";
    }



}