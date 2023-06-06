package com.example.demo.service;

import com.example.demo.model.Recenzija;
import com.example.demo.repository.RecenzijaRepository;
import com.example.demo.repository.StavkaPoliceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecenzijaService {

    @Autowired
    private RecenzijaRepository recenzijaRepository;

    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;

    public List<Recenzija> getAllReviews() {
        // Implementacija za dobavljanje svih knjiga
        List<Recenzija> recenzije = recenzijaRepository.findAll();
        return recenzije;
    }

    public Optional<Recenzija> getReviewsByBookId(Long bookId) {
        // Implementacija za dobavljanje recenzija po ID-u knjige
        //Optional<Recenzija> recenzije = recenzijaRepository.findById(bookId);
        Optional<Recenzija> recenzije = recenzijaRepository.findById (bookId);

        return recenzije;
    }

}
