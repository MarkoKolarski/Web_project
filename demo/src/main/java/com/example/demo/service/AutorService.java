package com.example.demo.service;

import com.example.demo.model.Autor;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Uloga;
import com.example.demo.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public void createAutor(Autor autor) {
        if (!autor.getUloga().equals(Uloga.AUTOR)) {
            throw new IllegalArgumentException("Only administrators can create authors.");
        }


        // Additional validation logic if needed

        // Set the user's account as active
        autor.setAktivan(true);

        // Save the user to the database or perform other necessary operations
        autorRepository.save(autor);
    }
}
