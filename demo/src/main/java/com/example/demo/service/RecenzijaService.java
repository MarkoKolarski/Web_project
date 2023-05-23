package com.example.demo.service;

import com.example.demo.model.Knjiga;
import com.example.demo.model.Recenzija;
import com.example.demo.repository.KnjigaRepository;
import com.example.demo.repository.RecenzijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecenzijaService {

    @Autowired
    private RecenzijaRepository recenzijaRepository;

    public List<Recenzija> getAllReviews() {
        // Implementacija za dobavljanje svih knjiga
        List<Recenzija> recenzije = recenzijaRepository.findAll();
        return recenzije;
    }

}
