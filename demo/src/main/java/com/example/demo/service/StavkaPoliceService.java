package com.example.demo.service;

import com.example.demo.model.Knjiga;
import com.example.demo.model.StavkaPolice;
import com.example.demo.repository.StavkaPoliceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
public class StavkaPoliceService {

    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;

    public StavkaPolice getById(Long id) {
        return stavkaPoliceRepository.getById(id);
    }

    public Set<StavkaPolice> findByKnjiga(Knjiga knjiga) {
        Set<StavkaPolice> police = stavkaPoliceRepository.findByKnjiga(knjiga);
        return police;
    }

    public void delete(StavkaPolice stavkaPolice) {
        stavkaPoliceRepository.delete(stavkaPolice);
    }

    public void save(StavkaPolice stavkaPolice) {
        stavkaPoliceRepository.save(stavkaPolice);
    }

    public void saveAll(Set<StavkaPolice> stavkaPoliceSet) {
        stavkaPoliceRepository.saveAll(stavkaPoliceSet);
    }

    public Set<StavkaPolice> findAll() {
        Set<StavkaPolice> police = (Set<StavkaPolice>) stavkaPoliceRepository.findAll();
        return police;
    }
}