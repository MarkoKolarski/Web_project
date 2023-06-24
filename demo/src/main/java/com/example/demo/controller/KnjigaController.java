package com.example.demo.controller;

import com.example.demo.dto.KnjigaDto;
import com.example.demo.model.Autor;
import com.example.demo.model.Knjiga;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Uloga;
import com.example.demo.service.AutorService;
import com.example.demo.service.KnjigaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class KnjigaController {

    @Autowired
    private KnjigaService knjigaService;

    @Autowired
    private AutorService autorService;



    @GetMapping("/knjige")
    public String getAllBooks(Model model) {
        List<Knjiga> knjige = knjigaService.getAllBooks2();
        model.addAttribute("knjige", knjige);
        return "knjige";
    }


    @GetMapping("/pretraga-knjige")
    public String searchBooksByNaslov(String naslov, Model model) {
        Set<KnjigaDto> knjigeDto = knjigaService.searchBooks(naslov);

        if (knjigeDto.isEmpty()) {
            model.addAttribute("poruka", "Nema rezultata za dati naslov.");
        } else {
            model.addAttribute("knjige", knjigeDto);
        }

        return "pretraga-knjige"; // Vraća naziv predloška (template) ili prikaza stranice
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

    @GetMapping("/autor-dodaj-knjigu")
    public String showAutorDodajKnjiguForm(Model model, HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error"; // Return the error page or appropriate view name
        }

        if (loggedKorisnik.getUloga() != Uloga.AUTOR) {
            model.addAttribute("errorMessage", "Nisi autor.");
            return "error"; // Return the error page or appropriate view name
        }
        model.addAttribute("knjigaDto", new KnjigaDto());
        return "autor-dodaj-knjigu"; // Return the form page or appropriate view name
    }

    @RequestMapping(value = "/autor-dodaj-knjigu", method = RequestMethod.POST)
    public String autorDodajKnjigu(@ModelAttribute("knjigaDto") KnjigaDto knjigaDto, HttpSession session, Model model) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error"; // Return the error page or appropriate view name
        }

        if (loggedKorisnik.getUloga() != Uloga.AUTOR) {
            model.addAttribute("errorMessage", "Nisi autor.");
            return "error"; // Return the error page or appropriate view name
        }

        Optional<Autor> autor = autorService.AutorById(loggedKorisnik.getId());

        Autor loggedAutor = autor.get();

        if (knjigaDto == null) {
            model.addAttribute("errorMessage", "Morate uneti podatke.");
            return "error"; // Return the error page or appropriate view name
        }

        if (knjigaDto.getISBN() == null) {
            model.addAttribute("errorMessage", "Morate uneti ISBN.");
            return "error"; // Return the error page or appropriate view name
        }

        boolean postojiKnjiga = knjigaService.novaKnjigaAutoru(knjigaDto, loggedAutor);

        if (postojiKnjiga) {
            model.addAttribute("errorMessage", "Knjiga se ne sme dva puta dodati na spisak. Promeni naslov: " + knjigaDto.getNaslov() + " ili ISBN: " + knjigaDto.getISBN() + ".");
            return "error"; // Return the error page or appropriate view name
        }

        model.addAttribute("successMessage", "Dodata nova knjiga autoru: " + loggedAutor.getKorisnickoIme() + ".");
        return "uspesno-dodata-knjiga"; // Return the success page or appropriate view name
    }





//    @GetMapping(value = {"/izmeni-knjigu-autora", "/izmeni-knjigu-autora/**"})
    @GetMapping("/izmeni-knjigu-autora")
    public String prikaziFormuZaIzmenuKnjigeAutora(Model model, HttpSession session, HttpServletRequest request) {
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

        if (loggedKorisnik.getUloga() != Uloga.AUTOR) {
            model.addAttribute("errorMessage", "Nisi autor.");
            return "error"; // Return the error page or appropriate view name
        }

        KnjigaDto knjigaDto = new KnjigaDto();
        model.addAttribute("knjigaDto", knjigaDto);

        return "izmeni-knjigu-autora"; // Return the form page or appropriate view name
    }



    @RequestMapping(value = "/izmeni-knjigu-autora", method = RequestMethod.POST)
    public String izmeniKnjiguAutora(@ModelAttribute("knjigaDto")  KnjigaDto knjigaDto, HttpSession session, Model model) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error"; // Return the error page or appropriate view name
        }

        if (loggedKorisnik.getUloga() != Uloga.AUTOR) {
            model.addAttribute("errorMessage", "Nisi autor.");
            return "error"; // Return the error page or appropriate view name
        }

        Optional<Autor> autor = autorService.AutorById(loggedKorisnik.getId());
        Autor loggedAutor = autor.get();

        if (knjigaDto == null) {
            model.addAttribute("errorMessage", "Morate uneti podatke.");
            return "error"; // Return the error page or appropriate view name
        }

        if (knjigaDto.getISBN() == null) {
            model.addAttribute("errorMessage", "Morate uneti ISBN.");
            return "error"; // Return the error page or appropriate view name
        }

        Knjiga existingKnjiga = knjigaService.findByISBN(knjigaDto.getISBN());

        if (existingKnjiga == null) {
            model.addAttribute("errorMessage", "Knjiga sa datim ISBN ne postoji.");
            return "error"; // Return the error page or appropriate view name
        }

        knjigaService.promeniKnjiguAutora(existingKnjiga, knjigaDto, loggedAutor);

        model.addAttribute("successMessage", "Knjiga je uspešno ažurirana.");
        return "uspesno-izmeni-knjigu-autora"; // Return the success page or appropriate view name
    }

    @GetMapping("/izmeni-knjigu")
    public String showUpdateBookForm( HttpSession session, Model model) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error";
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            model.addAttribute("errorMessage", "Nisi administrator.");
            return "error";
        }

        KnjigaDto knjigaDto = new KnjigaDto();

        model.addAttribute("knjigaDto", knjigaDto);
        return "izmeni-knjigu";
    }



    @PostMapping("/izmeni-knjigu")
    public String updateBook(@ModelAttribute("knjigaDto") KnjigaDto knjigaDto, HttpSession session, Model model) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error";
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            model.addAttribute("errorMessage", "Nisi administrator.");
            return "error";
        }

        if (knjigaDto == null) {
            model.addAttribute("errorMessage", "Morate uneti podatke.");
            return "error";
        }

        if (knjigaDto.getISBN() == null) {
            model.addAttribute("errorMessage", "Morate uneti ISBN.");
            return "error";
        }

        Knjiga existingKnjiga = knjigaService.findByISBN(knjigaDto.getISBN());

        if (existingKnjiga == null) {
            model.addAttribute("errorMessage", "Knjiga sa datim ISBN ne postoji.");
            return "error";
        }

        knjigaService.promeniKnjigu(existingKnjiga, knjigaDto);

        model.addAttribute("successMessage", "Knjiga je uspešno ažurirana.");
        return "uspesno-azurirana-knjiga";
    }

    @GetMapping("/obrisi-knjigu")
    public String prikaziFormuObrisiKnjigu(Model model, HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            // Redirekcija na odgovarajuću stranicu za prijavu
            return "redirect:/login";
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            // Redirekcija na odgovarajuću stranicu za zabranu pristupa
            return "error";
        }

        model.addAttribute("isbn", "");
        model.addAttribute("korisnickoIme", "");

        return "obrisi-knjigu";
    }


    @PostMapping("/obrisi-knjigu")
    public String obrisiKnjigu(@RequestParam("isbn") String isbn,
                               @RequestParam("korisnickoIme") String korisnickoIme,
                               HttpSession session,
                               Model model) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            model.addAttribute("errorMessage", "Niste ulogovani.");
            return "error";
        }

        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
            model.addAttribute("errorMessage", "Nisi administrator.");
            return "error";
        }

        if (isbn == null) {
            model.addAttribute("errorMessage", "Morate uneti ISBN.");
            return "error";
        }

        boolean knjigaObrisana = knjigaService.obrisiKnjiguPoISBN(isbn, korisnickoIme);

        if (!knjigaObrisana) {
            model.addAttribute("errorMessage", "Knjiga nije obrisana.");
            return "error";
        }

        model.addAttribute("successMessage", "Knjiga obrisana.");
        return "knjiga-obrisana";
    }

//    @GetMapping("/knjige")
//    public String sveKnjige(Model model) {
//        List<Knjiga> knjige = knjigaService.getAllBooks2();
//
//        if (knjige.isEmpty()) {
//            return "error"; // Assuming you have an error view
//        }
//
//        List<KnjigaDto> knjigeDto = new ArrayList<>();
//        for (Knjiga knjiga : knjige) {
//            KnjigaDto knjigaDto = new KnjigaDto();
//            knjigaDto.setId(knjiga.getId());
//            knjigaDto.setNaslov(knjiga.getNaslov());
//            knjigaDto.setNaslovnaFotografija(knjiga.getNaslovnaFotografija());
//            knjigaDto.setISBN(knjiga.getISBN());
//            knjigaDto.setDatumObjavljivanja(knjiga.getDatumObjavljivanja());
//            knjigaDto.setBrojStrana(knjiga.getBrojStrana());
//            knjigaDto.setOpis(knjiga.getOpis());
//            knjigaDto.setZanrovi(knjiga.getZanrovi());
//            knjigaDto.setOcena(knjiga.getOcena());
//            knjigeDto.add(knjigaDto);
//        }
//
//        model.addAttribute("knjigeDto", knjigeDto);
//        return "knjige"; // Assuming you have a book list view
//    }





}
