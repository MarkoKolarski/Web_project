package com.example.demo.service;

import com.example.demo.model.Recenzija;
import com.example.demo.model.Zanr;
import com.example.demo.repository.RecenzijaRepository;
import com.example.demo.repository.ZanrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZanrService {

    @Autowired
    private ZanrRepository zanrRepository;

    public List<Zanr> getAllGenres() {
        // Implementacija za dobavljanje svih knjiga
        List<Zanr> zanrovi = zanrRepository.findAll();
        return zanrovi;
    }
}
