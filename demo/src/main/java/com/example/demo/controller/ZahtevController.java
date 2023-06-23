package com.example.demo.controller;

import com.example.demo.dto.KnjigaDto;
import com.example.demo.dto.ZahtevDto;
import com.example.demo.model.*;
import com.example.demo.service.ZahtevService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ZahtevController {

    @Autowired
    private ZahtevService zahtevService;

    @GetMapping("/zahtevi")
    public String sviZahtevi(Model model) {
        List<ZahtevZaAktivacijuNalogaAutora> zahtevi = zahtevService.getAll();

        if (zahtevi.isEmpty()) {
            return "no-zahtevi"; // Return the view name for no zahtevi
        }

        List<ZahtevDto> zahteviDtos = new ArrayList<>();
        for (ZahtevZaAktivacijuNalogaAutora zahtev : zahtevi) {
            ZahtevDto zahtevDto = new ZahtevDto();
            zahtevDto.setId(zahtev.getId());
            zahtevDto.setEmail(zahtev.getEmail());
            zahtevDto.setDatum(zahtev.getDatum());
            zahtevDto.setPoruka(zahtev.getPoruka());
            zahtevDto.setTelefon(zahtev.getTelefon());
            zahtevDto.setAutor(zahtev.getAutor());
            zahtevDto.setStatus(zahtev.getStatus());

            zahteviDtos.add(zahtevDto);
        }

        model.addAttribute("zahtevi", zahteviDtos);
        return "zahtevi"; // Return the view name for the list of zahtevi
    }


    @GetMapping("zahtev-autor")
    public String showZahtevZaAutoraForm(Model model) {
        model.addAttribute("zahtevDto", new ZahtevDto());
        return "zahtev-autor"; // Return the form view name
    }

    @PostMapping("zahtev-autor")
    public String submitZahtevZaAutora( @ModelAttribute("zahtevDto")  ZahtevDto zahtevDto, Model model) {

        if (zahtevDto == null) {
            model.addAttribute("errorMessage", "Morate uneti podatke.");
            return "error"; // Return the error page or appropriate view name
        }

        boolean autorPostoji = zahtevService.registerUser(zahtevDto);

        if (!autorPostoji) {
            model.addAttribute("errorMessage", "Autor nije pronadjen.");
            return "error";
        }
        model.addAttribute("successMessage", "Zahtev za aktivaciju autora uspesno poslat.");
        return "uspesno-zahtev-autor"; // Return the success page or appropriate view name
    }


}
