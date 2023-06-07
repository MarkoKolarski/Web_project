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

        autor.setAktivan(autorDto.getAktivan());
        autor.setIme(autorDto.getIme());
        autor.setPrezime(autorDto.getPrezime());
        autor.setKorisnickoIme(autorDto.getKorisnickoIme());
        autor.setMejlAdresa(autorDto.getMejlAdresa());
        autor.setLozinka(autorDto.getLozinka());
        autor.setDatumRodjenja(autorDto.getDatumRodjenja());
        autor.setProfilnaSlika(autorDto.getProfilnaSlika());
        autor.setOpis(autorDto.getOpis());
        autor.setUloga(autorDto.getUloga());
        autor.setPolice(autorDto.getPolice());



        autorRepository.save(autor);

    }


//    public void deleteKnjiga(Knjiga knjiga, Autor autor) {
//        autorRepository.delete(autor.getSpisakKnjiga(knjiga));
//    }
}
