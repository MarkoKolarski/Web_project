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


}
