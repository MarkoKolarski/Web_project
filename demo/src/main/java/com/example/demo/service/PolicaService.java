package com.example.demo.service;

import com.example.demo.model.Korisnik;
import com.example.demo.model.Polica;
import com.example.demo.repository.KnjigaRepository;
import com.example.demo.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;

@Service
public class PolicaService {


    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private KnjigaRepository knjigaRepository;

    public PolicaService(KorisnikRepository korisnikRepository, KnjigaRepository knjigaRepository) {
        this.korisnikRepository = korisnikRepository;
        this.knjigaRepository = knjigaRepository;
    }

    public Set<Polica> getUserBookshelf(Long userId) throws ChangeSetPersister.NotFoundException {
        // Pronalaženje korisnika na osnovu ID-a
        Korisnik korisnik = korisnikRepository.findById(userId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // Dobavljanje polica korisnika
        Set<Polica> police = korisnik.getPolice();

        return police;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
