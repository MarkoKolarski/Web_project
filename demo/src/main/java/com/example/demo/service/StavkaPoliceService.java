package com.example.demo.service;

import com.example.demo.model.Knjiga;
import com.example.demo.model.StavkaPolice;
import com.example.demo.repository.StavkaPoliceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StavkaPoliceService {

    @Autowired
    private  StavkaPoliceRepository stavkaPoliceRepository;

    public  StavkaPolice getById(Long id) {
        return stavkaPoliceRepository.getById(id);
    }

    public Set<StavkaPolice> findByKnjiga(Knjiga knjiga){
        return (Set<StavkaPolice>) stavkaPoliceRepository.findByKnjiga(knjiga);
    }


//    public void save(Set<StavkaPolice> stavkaPolice) {
//        stavkaPoliceRepository.save(stavkaPolice);
//    }
}
