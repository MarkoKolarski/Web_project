package com.example.demo.service;

import com.example.demo.dto.KnjigaDto;
import com.example.demo.dto.RecenzijaDto;
import com.example.demo.model.Knjiga;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Recenzija;
import com.example.demo.model.Uloga;
import com.example.demo.repository.KorisnikRepository;
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
    private KorisnikRepository korisnikRepository;

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

    public void novaRecenzija(RecenzijaDto recenzijaDto, Korisnik loggedKorisnik) {

        Korisnik persistentKorisnik = korisnikRepository.findById(loggedKorisnik.getId()).orElse(null);
        if (persistentKorisnik != null) {
            persistentKorisnik.setIme(loggedKorisnik.getIme());
            persistentKorisnik.setPrezime(loggedKorisnik.getPrezime());
            persistentKorisnik.setKorisnickoIme(loggedKorisnik.getKorisnickoIme());
            persistentKorisnik.setMejlAdresa(loggedKorisnik.getMejlAdresa());
            persistentKorisnik.setLozinka(loggedKorisnik.getLozinka());
            persistentKorisnik.setUloga(loggedKorisnik.getUloga());
        }


        Recenzija recenzija = new Recenzija();
        recenzija.setOcena(recenzijaDto.getOcena());
        recenzija.setTekst(recenzijaDto.getTekst());
        recenzija.setDatumRecenzije(recenzijaDto.getDatumRecenzije());
        recenzija.setKorisnik(persistentKorisnik);

            recenzijaRepository.save(recenzija);

    }
}
