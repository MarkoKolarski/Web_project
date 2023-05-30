package com.example.demo.service;

import com.example.demo.model.Knjiga;
import com.example.demo.model.Korisnik;
import com.example.demo.model.StavkaPolice;
import com.example.demo.repository.StavkaPoliceRepository;
import com.example.demo.repository.ZanrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class StavkaPoliceService {

    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;

    public Set<StavkaPolice> findByKnjiga(Knjiga knjiga){
        return (Set<StavkaPolice>) stavkaPoliceRepository.findByKnjiga(knjiga);
    }


}
