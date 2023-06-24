package com.example.demo.controller;


import ch.qos.logback.core.model.Model;
import com.example.demo.dto.RecenzijaDto;
import com.example.demo.model.Knjiga;
import com.example.demo.model.Recenzija;
import com.example.demo.service.RecenzijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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


}