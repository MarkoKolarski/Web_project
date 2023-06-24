package com.example.demo.controller;

import com.example.demo.dto.PolicaDto;
import com.example.demo.model.Knjiga;
import com.example.demo.model.Polica;
import com.example.demo.model.Zanr;
import com.example.demo.service.KnjigaService;
import com.example.demo.service.KorisnikService;
import com.example.demo.service.PolicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PolicaController {
    @Autowired
    private PolicaService policaService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private KnjigaService knjigaService;


    @GetMapping("/polica/{id}")
    public String prikaziPolica(@PathVariable("id") Long id, Model model) {
        // Dohvaćanje police
        Polica polica = policaService.getPolicaById(id);

        // Dohvaćanje liste knjiga na polici

        model.addAttribute("polica", polica);

        return "polica";
    }

}


