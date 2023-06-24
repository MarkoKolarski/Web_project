package com.example.demo.controller;

import com.example.demo.dto.AutorDto;
import com.example.demo.dto.PolicaDto;
import com.example.demo.dto.ZahtevDto;
import com.example.demo.model.*;
import com.example.demo.service.AutorService;
import com.example.demo.service.ZahtevService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class AutorController {

    @Autowired
    private ZahtevService zahtevService;

    @Autowired
    private AutorService autorService;

    public void odobrenMejl(String lozinka, String email, String korisnickoIme) throws MessagingException {
        //zameniti email sa vec stavljenim email-om za slanje na dobru adresu
        autorService.sendSimpleEmail("marko.kolarski.02@gmail.com",
                "Autor: " +korisnickoIme+ " je odobren",
                "Vaš zahtev za aktivaciju autora je odobren. \n" +
                        "Lozinka je: " +lozinka);

    }

    public void odbijenMejl(String email, String korisnickoIme) throws MessagingException {
        //zameniti email sa vec stavljenim email-om za slanje na dobru adresu
        autorService.sendSimpleEmail("marko.kolarski.02@gmail.com",
                "Autor: " +korisnickoIme+ " je odbijen",
                "Vaš zahtev za aktivaciju autora je odbijen.");

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

    @GetMapping("/izmeni-autora")
    public String showIzmeniAutoraForm(Model model,HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            // Redirekcija na odgovarajuću stranicu za prijavu
            return "redirect:/login";
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            // Redirekcija na odgovarajuću stranicu za zabranu pristupa
            return "error";
        }
        model.addAttribute("autorDto", new AutorDto());
        return "izmeni-autora";
    }

    @PostMapping("/izmeni-autora")
    public String izmeniAutora(@ModelAttribute("autorDto") AutorDto autorDto, HttpSession session, Model model) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error";
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            model.addAttribute("errorMessage", "Nisi administrator.");
            return "error";
        }

        if (autorDto == null) {
            model.addAttribute("errorMessage", "Morate uneti podatke.");
            return "error";
        }

        if (autorDto.getKorisnickoIme() == null) {
            model.addAttribute("errorMessage", "Morate uneti korisničko ime.");
            return "error";
        }

        Autor existingAutor = autorService.AutorBykorisnickoIme(autorDto.getKorisnickoIme());

        if (existingAutor == null) {
            model.addAttribute("errorMessage", "Autor sa datim korisničkim imenom ne postoji.");
            return "error";
        }

        if (existingAutor.getAktivan() == true) {
            model.addAttribute("errorMessage", "Autor sa datim korisničkim imenom je već aktiviran.");
            return "error";
        }

        autorService.promeniAutora(existingAutor, autorDto);

        model.addAttribute("successMessage", "Autor " + existingAutor.getKorisnickoIme() + " je uspešno ažuriran.");
        return "uspesno-izmenjen-autor";
    }


    @RequestMapping(value = "/favicon.ico", method = RequestMethod.GET)
    @ResponseBody
    public void handleFaviconRequest(HttpServletRequest request) {
        // Do nothing, simply return an empty response
    }
    @GetMapping(value = {"/dodaj-autora", "/dodaj-autora/**"})
    public String prikaziFormuDodajAutora(Model model, HttpSession session, HttpServletRequest request) {
        String requestUrl = request.getRequestURI();

        // Skip processing for favicon request
        if (requestUrl.contains("favicon.ico")) {
            return "redirect:/";
        }

        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error"; // Return the error page or appropriate view name
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            model.addAttribute("errorMessage", "Nisi administrator.");
            return "error"; // Return the error page or appropriate view name
        }

        AutorDto autorDto = new AutorDto();
        model.addAttribute("autorDto", autorDto);

        return "dodaj-autora"; // Return the form page or appropriate view name
    }



    @RequestMapping(value = "/dodaj-autora", method = RequestMethod.POST)
    public String dodajAutora(@ModelAttribute("autorDto")  AutorDto autorDto, BindingResult bindingResult, HttpSession session, Model model) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error"; // Return the error page or appropriate view name
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            model.addAttribute("errorMessage", "Nisi administrator.");
            return "error"; // Return the error page or appropriate view name
        }

        if (bindingResult.hasErrors()) {
            // Handle validation errors, if any
            return "dodaj-autora"; // Return the form page or appropriate view name
        }

        if (autorDto == null) {
            model.addAttribute("errorMessage", "Morate uneti podatke.");
            return "error"; // Return the error page or appropriate view name
        }

        if (autorDto.getKorisnickoIme() == null) {
            model.addAttribute("errorMessage", "Morate uneti korisničko ime.");
            return "error"; // Return the error page or appropriate view name
        }

        Autor noviAutor = autorService.dodajAutora(autorDto);

        if (noviAutor == null) {
            model.addAttribute("errorMessage", "Autor sa datim korisničkim imenom ne postoji.");
            return "error"; // Return the error page or appropriate view name
        }

        autorService.promeniAutora(noviAutor, autorDto);

        model.addAttribute("successMessage", "Autor: " + noviAutor.getKorisnickoIme() + " je uspešno dodat.");
        return "uspesno-dodaj-autora"; // Return the success page or appropriate view name
    }

    @GetMapping("/odobri-autora")
    public String showOdobriAutoraForm(Model model,HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            // Redirekcija na odgovarajuću stranicu za prijavu
            return "redirect:/login";
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            // Redirekcija na odgovarajuću stranicu za zabranu pristupa
            return "error";
        }

        model.addAttribute("zahtevDto", new ZahtevDto());
        return "odobri-autora";
    }

    @PostMapping("/odobri-autora")
    public String odobriAutora(@ModelAttribute("zahtevDto") ZahtevDto zahtevDto, HttpSession session, Model model) throws MessagingException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error";
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            model.addAttribute("errorMessage", "Nisi administrator.");
            return "error";
        }

        AutorDto autorDto = zahtevService.getZahtev(zahtevDto.getId());

        if (autorDto == null) {
            model.addAttribute("errorMessage", "Autor ili zahtev ne postoji!");
            return "error";
        }

        Optional<ZahtevZaAktivacijuNalogaAutora> existingZahtev = zahtevService.findById(zahtevDto.getId());

        if (!existingZahtev.isPresent()) {
            model.addAttribute("errorMessage", "Zahtev ne postoji!");
            return "error";
        }

        if (existingZahtev.get().getStatus() == Status.ODOBREN) {
            model.addAttribute("errorMessage", "Zahtev je već odobren!");
            return "error";
        }

        if (zahtevDto.getStatus() == Status.ODOBREN) {
            if (autorDto.getAktivan() == false) {
                existingZahtev.get().setStatus(Status.ODOBREN);
                zahtevService.save(existingZahtev.get());

                autorDto.setAktivan(true);

                Polica polica1 = new Polica();
                polica1.setNaziv("WantToRead");
                polica1.setPrimarna(true);

                Polica polica2 = new Polica();
                polica2.setNaziv("CurrentlyReading");
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

                odobrenMejl(autorDto.getLozinka(), zahtevDto.getEmail(), autorDto.getKorisnickoIme());

                model.addAttribute("successMessage", "Autor je uspešno aktiviran!");
                return "autor-odobren";
            } else {
                model.addAttribute("errorMessage", "Autor je već aktiviran.");
                return "error";
            }
        } else {
            existingZahtev.get().setStatus(Status.ODBIJEN);
            zahtevService.save(existingZahtev.get());

            odbijenMejl(zahtevDto.getEmail(), autorDto.getKorisnickoIme());

            model.addAttribute("errorMessage", "Autor je odbijen!");
            return "autor-odbijen";
        }
    }



}