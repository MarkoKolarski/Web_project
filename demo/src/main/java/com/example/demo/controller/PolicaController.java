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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @GetMapping("/police-korisnika/{id}")
    public ModelAndView getUserBookshelf(@PathVariable Long id) {
        Set<Polica> police = policaService.getUserBookshelf(id);

        if (police.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("greska");
            modelAndView.addObject("errorMessage", "Nije pronađena polica za datog korisnika");
            return modelAndView;
        }

        Set<PolicaDto> policeDto = new HashSet<>();
        for (Polica polica : police) {
            PolicaDto policaDto = new PolicaDto(polica);
            policeDto.add(policaDto);
        }

        ModelAndView modelAndView = new ModelAndView("police-korisnika");
        modelAndView.addObject("police-korisnika", policeDto);
        return modelAndView;
    }

}


