package com.example.demo.controller;


import com.example.demo.dto.PolicaDto;
import com.example.demo.model.Knjiga;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Polica;
import com.example.demo.service.KnjigaService;
import com.example.demo.service.KorisnikService;
import com.example.demo.service.PolicaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashSet;
import java.util.Set;

@RestController
public class PolicaRestController {

    @Autowired
    private PolicaService policaService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private KnjigaService knjigaService;


    // Endpoint za pregled polica (liste knjiga) za određenog korisnika
    @GetMapping("/api/korisnici/{korisnikId}/police")
    public ResponseEntity<Set<PolicaDto>> getUserBookshelf(@PathVariable Long korisnikId) throws ChangeSetPersister.NotFoundException {
        Set<Polica> police = policaService.getUserBookshelf(korisnikId);

        if (police.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }


        Set<PolicaDto> policeDto = new HashSet<>();
        for (Polica polica : police) {
            PolicaDto policaDto = new PolicaDto(polica);
            policeDto.add(policaDto);
        }

        return ResponseEntity.ok(policeDto);
    }


    @GetMapping("/api/korisnici/{korisnicko_ime}/police-po-imenu")
    public ResponseEntity<Set<PolicaDto>> getUserBookshelf(@PathVariable String korisnicko_ime)  {
        Set<Polica> police = policaService.policaPoNazivu(korisnicko_ime);

        if (police.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }


        Set<PolicaDto> policeDto = new HashSet<>();
        for (Polica polica : police) {
            PolicaDto policaDto = new PolicaDto(polica);
            policeDto.add(policaDto);
        }

        return ResponseEntity.ok(policeDto);
    }


    @PostMapping("api/dodaj-policu")
    public ResponseEntity<String> login(@RequestBody PolicaDto policaDto, HttpSession session) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }

//        if (loggedKorisnik.getUloga() != Uloga.CITALAC && loggedKorisnik.getUloga() != Uloga.AUTOR) {
//            return new ResponseEntity<>("Nisi citalac ili autor.", HttpStatus.BAD_REQUEST);
//        }

        if (policaDto == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti podatke.");
        }

        if (loggedKorisnik.getPolice().stream().anyMatch(polica -> polica.getNaziv().equals(policaDto.getNaziv()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Polica sa tim imenom već postoji.");
        }





        Polica polica = new Polica();
        polica.setNaziv(policaDto.getNaziv());
        polica.setPrimarna(false);
        polica.setStavkePolice(policaDto.getStavkePolice());


        loggedKorisnik.getPolice().add(polica);
        korisnikService.save(loggedKorisnik);

        //policaService.novaPolica(policaDto);

        return ResponseEntity.ok("Dodata nova polica.");

    }
    // Knjiga existingKnjiga = knjigaService.findByISBN(knjigaDto.getISBN());
//        knjigaService.promeniKnjigu(existingKnjiga, knjigaDto );

    // Optional<Polica> existingPolica = PolicaService.findById(policaDto.getId());

    @DeleteMapping("/api/obrisi-policu")
    public ResponseEntity<String> obrisiPolicu(@RequestParam("id") long id, HttpSession session) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }

//        if (loggedKorisnik.getUloga() != Uloga.ADMINISTRATOR) {
//            return new ResponseEntity<>("Nisi administrator.", HttpStatus.BAD_REQUEST);
//        }

//        if (isbn == null) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti ISBN.");
//        }

        Polica polica =  policaService.nadjiPolicu(id, loggedKorisnik.getId());

        loggedKorisnik.getPolice().remove(polica);
        korisnikService.save(loggedKorisnik);
        policaService.deletePolica(polica);
        korisnikService.save(loggedKorisnik);
        //policaService.obrisiPolicu(isbn, korisnickoIme);

        return ResponseEntity.ok("Polica obrisana.");
    }

    @PutMapping("api/polica-dodaj-knjigu")
    public ResponseEntity<String> updateBook(@RequestParam("isbn") String isbn, @RequestParam("naziv") String naziv_police, HttpSession session) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }


        if (isbn == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti isbn knjige");
        }
//
//        if (id_police == null) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ne postoji polica.");
//        }
        Knjiga existingKnjiga = knjigaService.findByISBN((isbn));
        Polica existingPolica = policaService.findByNaziv((naziv_police));

        if (existingKnjiga == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Knjiga sa datim ISBN ne postoji.");
        }

        if (existingPolica == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Polica sa datim nazivom ne postoji.");
        }

        // Check if the book is already added to one of the three primarne police
        boolean isBookOnPrimaryPolica = policaService.isBookOnPrimaryPolica(existingKnjiga, loggedKorisnik);

        if (!isBookOnPrimaryPolica) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Knjiga mora biti dodata na jednu od primarnih polica pre nego što je možete dodati na sopstvene police");
        }
        boolean  isBookOnPrimaryPolicaForLoggedUser = policaService.isBookOnPrimaryPolicaForLoggedUser(existingKnjiga, loggedKorisnik, existingPolica);

        if (isBookOnPrimaryPolicaForLoggedUser) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Knjiga se ne sme dva puta dodati na primarnu policu.");
        }

        boolean bookExist = policaService.dodajKnjigu(existingKnjiga, existingPolica );

        if(bookExist){
            return ResponseEntity.ok("Knjiga " + existingKnjiga.getNaslov() + " vec postoji u polici: " + existingPolica.getNaziv() + ", pokušajte ponovo " );
        }

        return ResponseEntity.ok("Knjiga " + existingKnjiga.getNaslov() + " je uspešno dodata u policu: " + existingPolica.getNaziv());

    }

    @PutMapping("api/obrisi-knjigu-sa-police")
    public ResponseEntity<String> ObrisiKnjiguSaPolice(@RequestParam("isbn") String isbn, @RequestParam("naziv") String naziv_police, HttpSession session) {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (loggedKorisnik == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Niste ulogovani.");
        }


        if (isbn == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Morate uneti isbn knjige");
        }

//        if (id_police == null) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ne postoji polica.");
//        }

        Knjiga existingKnjiga = knjigaService.findByISBN((isbn));
        Polica existingPolica = policaService.findByNaziv((naziv_police));

        if (existingKnjiga == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Knjiga sa datim ISBN ne postoji.");
        }

        if (existingPolica == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Polica sa datim nazivom ne postoji.");
        }

        boolean bookExist = policaService.izbaciKnjigu(existingKnjiga, existingPolica);

        if(bookExist){
            return ResponseEntity.ok("Knjiga " + existingKnjiga.getNaslov() + " je izbrisana iz police: " + existingPolica.getNaziv() );
        }

        return ResponseEntity.ok("Knjiga " + existingKnjiga.getNaslov() + " nije u polici: " + existingPolica.getNaziv());

    }

}
