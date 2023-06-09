package com.example.demo.service;

import com.example.demo.dto.AutorDto;
import com.example.demo.dto.KnjigaDto;
import com.example.demo.model.*;
import com.example.demo.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

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
       return autorRepository.findBykorisnickoIme(korisnickoIme);
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


//    public void deleteKnjiga(Knjiga knjiga, Autor autor) {
//        autorRepository.delete(autor.getSpisakKnjiga(knjiga));
//    }
}
