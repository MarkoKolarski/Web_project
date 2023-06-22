package com.example.demo.service;

import com.example.demo.dto.AutorDto;
import com.example.demo.model.*;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.KorisnikRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AutorService {

    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("marko.kolarski.02@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Uspešno poslato");


    }



    public List<Autor> getAllAutors() {
        // Implementacija za dobavljanje svih knjiga
        List<Autor> autori = autorRepository.findAll();
        return autori;
    }

    public Optional<Autor> AutorById(Long id) {
        // Implementacija za dobavljanje svih knjiga
        Optional <Autor> autori = autorRepository.findById(id);
        return autori;
    }

    public void save(Autor autor) {
        // Implementacija za dobavljanje svih knjiga
        autorRepository.save(autor);
    }


    public Autor AutorBykorisnickoIme(String korisnickoIme) {
        Autor autor = autorRepository.findBykorisnickoIme(korisnickoIme);
        return autor;
    }

    public void promeniAutora(Autor autor, AutorDto autorDto) {

            if (autorDto.getAktivan() != null) {
                autor.setAktivan(autorDto.getAktivan());
            }
            if (autorDto.getIme() != null && !autorDto.getIme().isEmpty()) {
                autor.setIme(autorDto.getIme());
            }
            if (autorDto.getPrezime() != null && !autorDto.getPrezime().isEmpty()) {
                autor.setPrezime(autorDto.getPrezime());
            }
            if (autorDto.getKorisnickoIme() != null && !autorDto.getKorisnickoIme().isEmpty()) {
                autor.setKorisnickoIme(autorDto.getKorisnickoIme());
            }
            if (autorDto.getMejlAdresa() != null && !autorDto.getMejlAdresa().isEmpty()) {
                autor.setMejlAdresa(autorDto.getMejlAdresa());
            }
            if (autorDto.getLozinka() != null && !autorDto.getLozinka().isEmpty()) {
                autor.setLozinka(autorDto.getLozinka());
            }
            if (autorDto.getDatumRodjenja() != null) {
                autor.setDatumRodjenja(autorDto.getDatumRodjenja());
            }
            if (autorDto.getProfilnaSlika() != null && !autorDto.getProfilnaSlika().isEmpty()) {
                autor.setProfilnaSlika(autorDto.getProfilnaSlika());
            }
            if (autorDto.getOpis() != null && !autorDto.getOpis().isEmpty()) {
                autor.setOpis(autorDto.getOpis());
            }
            if (autorDto.getUloga() != null && !autorDto.getUloga().equals(null)) {
                autor.setUloga(autorDto.getUloga());
            }
            if (autorDto.getPolice() != null) {
                autor.setPolice(autorDto.getPolice());

        }




        autorRepository.save(autor);

    }

    public Autor dodajAutora(AutorDto autorDto) {

        Korisnik existingUser = korisnikService.findByMejlAdresa(autorDto.getMejlAdresa());
        if (existingUser != null) {
            throw new KorisnikService.EmailAlreadyRegisteredException("Unesite drugu email adresu.");
        }
        Korisnik existingUser2 = korisnikService.findBykorisnickoIme(autorDto.getKorisnickoIme());
        if (existingUser2 != null) {
            throw new KorisnikService.EmailAlreadyRegisteredException("Unesite drugo korisničko ime.");
        }

        Autor autor = new Autor();

        autor.setAktivan(true);

        if (autorDto.getIme() != null && !autorDto.getIme().isEmpty()) {
            autor.setIme(autorDto.getIme());
        }
        if (autorDto.getPrezime() != null && !autorDto.getPrezime().isEmpty()) {
            autor.setPrezime(autorDto.getPrezime());
        }
        if (autorDto.getKorisnickoIme() != null && !autorDto.getKorisnickoIme().isEmpty()) {
            autor.setKorisnickoIme(autorDto.getKorisnickoIme());
        }
        if (autorDto.getMejlAdresa() != null && !autorDto.getMejlAdresa().isEmpty()) {
            autor.setMejlAdresa(autorDto.getMejlAdresa());
        }
        if (autorDto.getLozinka() != null && !autorDto.getLozinka().isEmpty()) {
            autor.setLozinka(autorDto.getLozinka());
        }
        if (autorDto.getDatumRodjenja() != null) {
            autor.setDatumRodjenja(autorDto.getDatumRodjenja());
        }
        if (autorDto.getProfilnaSlika() != null && !autorDto.getProfilnaSlika().isEmpty()) {
            autor.setProfilnaSlika(autorDto.getProfilnaSlika());
        }
        if (autorDto.getOpis() != null && !autorDto.getOpis().isEmpty()) {
            autor.setOpis(autorDto.getOpis());
        }
        if (autorDto.getUloga() != null && !autorDto.getUloga().equals(null)) {
            autor.setUloga(autorDto.getUloga());
        }
        if (autorDto.getPolice() != null) {
            autor.setPolice(autorDto.getPolice());
        }

        autorRepository.save(autor);
        return autor;


    }

    public void deleteKnjiga(Knjiga knjiga, Autor autor) {
        for (Polica polica : autor.getPolice()) {
            Set<StavkaPolice> stavkeToRemove = new HashSet<>();
            for (StavkaPolice stavkaPolice : polica.getStavkePolice()) {
                if (stavkaPolice.getKnjiga().equals(knjiga)) {
                    stavkeToRemove.add(stavkaPolice);
                }
            }
            polica.getStavkePolice().removeAll(stavkeToRemove);
        }
        autorRepository.save(autor);
    }



    @ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(KorisnikService.EmailAlreadyRegisteredException.class)
        public ResponseEntity<String> handleEmailAlreadyRegisteredException(KorisnikService.EmailAlreadyRegisteredException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

}
