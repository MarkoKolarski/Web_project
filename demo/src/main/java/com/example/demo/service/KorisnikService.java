package com.example.demo.service;

import com.example.demo.dto.KorisnikDto;
import com.example.demo.dto.PolicaDto;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Polica;
import com.example.demo.model.Uloga;
import com.example.demo.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class KorisnikService {
    @Autowired
    private KorisnikRepository korisnikRepository;




    public void registerUser(KorisnikDto korisnikDto) {
    Korisnik existingUser = korisnikRepository.findByMejlAdresa(korisnikDto.getMejlAdresa());
        if (existingUser != null) {
            throw new EmailAlreadyRegisteredException("Unesite drugu email adresu.");
    }

        Korisnik korisnik = new Korisnik();
        korisnik.setIme(korisnikDto.getIme());
        korisnik.setPrezime(korisnikDto.getPrezime());
        korisnik.setKorisnickoIme(korisnikDto.getKorisnickoIme());
        korisnik.setMejlAdresa(korisnikDto.getMejlAdresa());
        korisnik.setLozinka(korisnikDto.getLozinka());
        korisnikDto.setPotvrdiLozinku(korisnik.getLozinka());
        korisnik.setUloga(Uloga.CITALAC);


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

        korisnikDto.setPolice(police);
        korisnik.setPolice(police);
        korisnikRepository.save(korisnik);



        korisnikRepository.save(korisnik);
}

    public Korisnik login(String mejlAdresa, String lozinka) {
        Korisnik korisnik = korisnikRepository.getByMejlAdresa(mejlAdresa);
        if(korisnik == null || !korisnik.getLozinka().equals(lozinka))
            return null;
        return  korisnik;
    }

    public Korisnik findOne(Long id){
        Optional<Korisnik> foundKorisnik = korisnikRepository.findById(id);
        if (foundKorisnik.isPresent())
            return foundKorisnik.get();

        return null;
    }

    public List<Korisnik> findAll(){
        return korisnikRepository.findAll();
    }

    public Korisnik save(Korisnik korisnik){
        return korisnikRepository.save(korisnik);
    }


    public Optional<Korisnik> findById(Long id) {
        return korisnikRepository.findById(id);
    }

    public Korisnik korisnikBykorisnickoIme(String korisnickoIme) {
        return korisnikRepository.findBykorisnickoIme(korisnickoIme);
    }

    public void promeniKorisnika(Korisnik existingKorisnik, KorisnikDto korisnikDto) {
        if (korisnikDto.getIme() != null && !korisnikDto.getIme().isEmpty()) {
            existingKorisnik.setIme(korisnikDto.getIme());
        }
        if (korisnikDto.getPrezime() != null && !korisnikDto.getPrezime().isEmpty()) {
            existingKorisnik.setPrezime(korisnikDto.getPrezime());
        }

        if (korisnikDto.getDatumRodjenja() != null) {
            existingKorisnik.setDatumRodjenja(korisnikDto.getDatumRodjenja());
        }
        if (korisnikDto.getProfilnaSlika() != null && !korisnikDto.getProfilnaSlika().isEmpty()) {
            existingKorisnik.setProfilnaSlika(korisnikDto.getProfilnaSlika());
        }
        if (korisnikDto.getOpis() != null && !korisnikDto.getOpis().isEmpty()) {
            existingKorisnik.setOpis(korisnikDto.getOpis());
        }

        if (!existingKorisnik.getLozinka().equals(korisnikDto.getPotvrdiLozinku())) {
            throw new IllegalArgumentException("Unesena trenutna lozinka se ne poklapa sa postojećom lozinkom.");
        }

        if (korisnikDto.getMejlAdresa() != null && !korisnikDto.getMejlAdresa().isEmpty()) {
            existingKorisnik.setMejlAdresa(korisnikDto.getMejlAdresa());
        }
        if (korisnikDto.getLozinka() != null && !korisnikDto.getLozinka().isEmpty()) {
            existingKorisnik.setLozinka(korisnikDto.getLozinka());
        }


        korisnikRepository.save(existingKorisnik);
    }


    public class EmailAlreadyRegisteredException extends RuntimeException {


    // Kod ispod je vezan za exception koji se pojavljuje kada email adresa vec postoji
        public EmailAlreadyRegisteredException(String message) {
            super(message);
        }

    }
    @ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(EmailAlreadyRegisteredException.class)
        public ResponseEntity<String> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

}
