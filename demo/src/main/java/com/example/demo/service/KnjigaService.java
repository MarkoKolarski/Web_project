package com.example.demo.service;

import com.example.demo.dto.KnjigaDto;
import com.example.demo.dto.KorisnikDto;
import com.example.demo.model.Knjiga;
import com.example.demo.repository.KnjigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KnjigaService {

    @Autowired
    private KnjigaRepository knjigaRepository;

    public List<Knjiga> getAllBooks() {
        // Implementacija za dobavljanje svih knjiga
        List<Knjiga> knjige = knjigaRepository.findAll();// Logika za dobavljanje svih knjiga iz baze podataka ili nekog drugog izvora
        return knjige;
    }

    public List<KnjigaDto> searchBooks(String query) {
        List<Knjiga> knjige = knjigaRepository.findByNaslovContainingIgnoreCase(query);
        List<KnjigaDto> knjigeDto = new ArrayList<>();

        for (Knjiga knjiga : knjige) {
            KnjigaDto knjigaDto = new KnjigaDto(knjiga);
            knjigeDto.add(knjigaDto);
        }

        return knjigeDto;
    }


}
