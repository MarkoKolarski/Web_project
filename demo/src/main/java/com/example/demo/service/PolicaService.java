package com.example.demo.service;

import com.example.demo.dto.PolicaDto;
import com.example.demo.model.*;
import com.example.demo.repository.KnjigaRepository;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.PolicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PolicaService {


    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private static PolicaRepository staticpolicaRepository;

    @Autowired
    private  PolicaRepository policaRepository;
    @Autowired
    private KnjigaRepository knjigaRepository;

    public PolicaService(KorisnikRepository korisnikRepository, KnjigaRepository knjigaRepository) {
        this.korisnikRepository = korisnikRepository;
        this.knjigaRepository = knjigaRepository;
    }

    public static Optional<Polica> findById(Long id) {
        return staticpolicaRepository.findById(id);
    }

    public Set<Polica> getUserBookshelf(Long userId) throws ChangeSetPersister.NotFoundException {
        Optional<Korisnik> korisnik = korisnikRepository.findById(userId);
        Set<Polica> police = korisnik.get().getPolice();

        return police;
    }


    public void novePolice(PolicaDto policaDto) {

        Set<Polica> police = new HashSet<>();
        for (Polica polica : police) {
//            autorDto.setId(autor.getId());
//            autorDto.setAktivan(autor.getAktivan());
//            autorDto.setIme(autor.getIme());
//            autorDto.setPrezime(autor.getPrezime());
//            autorDto.setKorisnickoIme(autor.getKorisnickoIme());
//            autorDto.setMejlAdresa(autor.getMejlAdresa());
//            autorDto.setLozinka(autor.getLozinka());
//            autorDto.setDatumRodjenja(autor.getDatumRodjenja());
//            autorDto.setProfilnaSlika(autor.getProfilnaSlika());
//            autorDto.setOpis(autor.getOpis());
//            autorDto.setUloga(autor.getUloga());
//            autorDto.setPolice(autor.getPolice());
//            autoriDto.add(autorDto);
        }

//        return ResponseEntity.ok(autoriDto);
    }

//    public void promeniPolicu(Optional<Polica> existingPolica, PolicaDto policaDto) {
//
//
//        if (existingPolica.isPresent()) {
//            Polica polica = existingPolica.get();
//            polica.setNaziv(policaDto.getNaziv());
//            polica.setPrimarna(policaDto.getPrimarna());
//            polica.setStavkePolice(policaDto.getStavkePolice());
//
//            policaRepository.save(polica);
//        }
//    }


    public void novaPolica(PolicaDto policaDto) {

        Polica polica = new Polica();
        polica.setNaziv(policaDto.getNaziv());
        polica.setPrimarna(policaDto.getPrimarna());
        polica.setStavkePolice(policaDto.getStavkePolice());

        policaRepository.save(polica);
    }
}
