package com.example.demo.service;

import com.example.demo.dto.KorisnikDto;
import com.example.demo.model.Autor;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Uloga;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikService {
    @Autowired
    private KorisnikRepository korisnikRepository;




    public void registerUser(KorisnikDto korisnikDto) {
    Korisnik existingUser = korisnikRepository.findByMejlAdresa(korisnikDto.getMejlAdresa());
        if (existingUser != null) {
            throw new EmailAlreadyRegisteredException("Email address is already registered.");
    }

        Korisnik korisnik = new Korisnik();
        korisnik.setIme(korisnikDto.getIme());
        korisnik.setPrezime(korisnikDto.getPrezime());
        korisnik.setKorisnickoIme(korisnikDto.getKorisnickoIme());
        korisnik.setMejlAdresa(korisnikDto.getMejlAdresa());
        korisnik.setLozinka(korisnikDto.getLozinka());
        korisnikDto.setPotvrdiLozinku(korisnik.getLozinka());
        korisnik.setUloga(Uloga.CITALAC);

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
