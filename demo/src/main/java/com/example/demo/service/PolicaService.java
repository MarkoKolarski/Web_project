package com.example.demo.service;

import com.example.demo.dto.AutorDto;
import com.example.demo.dto.PolicaDto;
import com.example.demo.dto.ZanrDto;
import com.example.demo.model.Autor;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Polica;
import com.example.demo.model.Zanr;
import com.example.demo.repository.KnjigaRepository;
import com.example.demo.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

}
