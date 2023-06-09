package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.repository.KnjigaRepository;
import com.example.demo.repository.ZahtevZaAktivacijuNalogaAutoraRepository;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.RecenzijaRepository;
import com.example.demo.repository.ZanrRepository;
import com.example.demo.repository.PolicaRepository;
import com.example.demo.repository.StavkaPoliceRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

//import static com.example.demo.model.Uloga.AUTOR;

/*
   @SpringBootApplication anotacija nastala je od @EnableAutoConfiguration anotacije koja
   upravlja konfiguracijom aplikacije.
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
