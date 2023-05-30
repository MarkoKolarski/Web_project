package com.example.demo.service;

import com.example.demo.model.Korisnik;
import com.example.demo.model.Polica;
import com.example.demo.repository.KnjigaRepository;
import com.example.demo.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        Optional<Korisnik> korisnik = korisnikRepository.findById(userId);
        Set<Polica> police = korisnik.get().getPolice();

        return police;
    }

}
