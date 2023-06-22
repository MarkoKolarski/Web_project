package com.example.demo.controller;

import com.example.demo.dto.AutorDto;
import com.example.demo.dto.PolicaDto;
import com.example.demo.dto.Zahtev2Dto;
import com.example.demo.model.*;
import com.example.demo.service.AutorService;
import com.example.demo.service.ZahtevService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class AutorController {

    @Autowired
    private ZahtevService zahtevService;

    @Autowired
    private AutorService autorService;

    @PostMapping("api/odobri-autora")
    public String odobriAutora(@ModelAttribute("zahtev2Dto") Zahtev2Dto zahtev2Dto, HttpSession session, Model model) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error";
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            model.addAttribute("errorMessage", "Nisi administrator.");
            return "error";
        }

        AutorDto autorDto = zahtevService.getZahtev(zahtev2Dto.getId());

        if (zahtev2Dto.getStatus() == Status.ODOBREN) {
            if (autorDto.getAktivan() == false) {
                autorDto.setAktivan(true);

                Polica polica1 = new Polica();
                polica1.setNaziv("Want to Read");
                polica1.setPrimarna(true);

                Polica polica2 = new Polica();
                polica2.setNaziv("Currently Reading");
                polica2.setPrimarna(true);

                Polica polica3 = new Polica();
                polica3.setNaziv("Read");
                polica3.setPrimarna(true);

                Set<Polica> police = new HashSet<>();
                police.add(polica1);
                police.add(polica2);
                police.add(polica3);

                Set<PolicaDto> policaDtos = new HashSet<>();
                for (Polica polica : police) {
                    PolicaDto policaDto = new PolicaDto();
                    policaDto.setId(polica.getId());
                    policaDto.setNaziv(polica.getNaziv());
                    policaDto.setPrimarna(polica.getPrimarna());
                    policaDto.setStavkePolice(polica.getStavkePolice());
                    policaDtos.add(policaDto);
                }

                autorDto.setPolice(police);
                autorDto.setAktivan(true);

                autorDto.setPolice(police);

                Optional<Autor> optionalAutor = autorService.AutorById(autorDto.getId());
                if (optionalAutor.isPresent()) {
                    Autor autor = optionalAutor.get();
                    autor.setAktivan(true);
                    autor.setPolice(police);
                    autorService.save(autor);
                }

                model.addAttribute("successMessage", "Autor je uspešno aktiviran!");
            } else {
                model.addAttribute("infoMessage", "Autor je već aktiviran");
            }

        } else {
            model.addAttribute("infoMessage", "Autor je odbijen!");
        }

        return "success";
    }

    @GetMapping("/autori")
    public String getAllAutors(Model model) {
        List<Autor> autori = autorService.getAllAutors();

        if (autori.isEmpty()) {
            model.addAttribute("errorMessage", "Nema dostupnih autora.");
            return "error";
        }

        List<AutorDto> autoriDto = new ArrayList<>();
        for (Autor autor : autori) {
            AutorDto autorDto = new AutorDto();
            autorDto.setId(autor.getId());
            autorDto.setAktivan(autor.getAktivan());
            autorDto.setIme(autor.getIme());
            autorDto.setPrezime(autor.getPrezime());
            autorDto.setKorisnickoIme(autor.getKorisnickoIme());
            autorDto.setMejlAdresa(autor.getMejlAdresa());
            autorDto.setLozinka(autor.getLozinka());
            autorDto.setDatumRodjenja(autor.getDatumRodjenja());
            autorDto.setProfilnaSlika(autor.getProfilnaSlika());
            autorDto.setOpis(autor.getOpis());
            autorDto.setUloga(autor.getUloga());
            autorDto.setPolice(autor.getPolice());
            autoriDto.add(autorDto);
        }

        model.addAttribute("autori", autoriDto);
        return "autori";
    }

    @PutMapping("/izmeni-autora")
    public String izmeniAutora(@ModelAttribute("autorDto") AutorDto autorDto, HttpSession session, Model model) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error.html";
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            model.addAttribute("errorMessage", "Nisi administrator.");
            return "error.html";
        }

        if (autorDto == null) {
            model.addAttribute("errorMessage", "Morate uneti podatke.");
            return "error.html";
        }

        if (autorDto.getKorisnickoIme() == null) {
            model.addAttribute("errorMessage", "Morate uneti korisničko ime.");
            return "error.html";
        }

        Autor existingAutor = autorService.AutorBykorisnickoIme(autorDto.getKorisnickoIme());

        if (existingAutor == null) {
            model.addAttribute("errorMessage", "Autor sa datim korisničkim imenom ne postoji.");
            return "error.html";
        }

        if (existingAutor.getAktivan() == true) {
            model.addAttribute("errorMessage", "Autor sa datim korisničkim imenom je već aktiviran.");
            return "error.html";
        }

        autorService.promeniAutora(existingAutor, autorDto);

        model.addAttribute("successMessage", "Autor " + existingAutor.getKorisnickoIme() + " je uspešno ažuriran.");
        return "success.html";
    }

}