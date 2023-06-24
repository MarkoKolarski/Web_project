package com.example.demo.service;

import com.example.demo.dto.RecenzijaDto;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Recenzija;
import com.example.demo.repository.RecenzijaRepository;
import com.example.demo.repository.StavkaPoliceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RecenzijaService {

    @Autowired
    private RecenzijaRepository recenzijaRepository;

    @Autowired
    private KorisnikService korisnikService;

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

            //Mora se napraviti novi objekat Korisnika
        Optional<Korisnik> persistentKorisnikOptional = korisnikService.findById(loggedKorisnik.getId());

            Korisnik persistentKorisnik = persistentKorisnikOptional.get();
            persistentKorisnik.setIme(loggedKorisnik.getIme());
            persistentKorisnik.setPrezime(loggedKorisnik.getPrezime());
            persistentKorisnik.setKorisnickoIme(loggedKorisnik.getKorisnickoIme());
            persistentKorisnik.setMejlAdresa(loggedKorisnik.getMejlAdresa());
            persistentKorisnik.setLozinka(loggedKorisnik.getLozinka());
            persistentKorisnik.setUloga(loggedKorisnik.getUloga());


        LocalDateTime currentDateTime = LocalDateTime.now();

        // Convert LocalDateTime to Date
        ZoneId zoneId = ZoneId.systemDefault();
        Date date = Date.from(currentDateTime.atZone(zoneId).toInstant());

        Recenzija recenzija = new Recenzija();
        recenzija.setOcena(recenzijaDto.getOcena());
        recenzija.setTekst(recenzijaDto.getTekst());
        recenzija.setDatumRecenzije(date);
        recenzija.setKorisnik(persistentKorisnik);

            recenzijaRepository.save(recenzija);

    }

    public Optional<Recenzija> findById(Long id) {
        return recenzijaRepository.findById(id);
    }

    public void promeniRecenziju(Recenzija recenzija, RecenzijaDto recenzijaDto, Korisnik loggedKorisnik) {

        Optional<Korisnik> persistentKorisnikOptional = korisnikService.findById(loggedKorisnik.getId());

        Korisnik persistentKorisnik = persistentKorisnikOptional.get();
        persistentKorisnik.setIme(loggedKorisnik.getIme());
        persistentKorisnik.setPrezime(loggedKorisnik.getPrezime());
        persistentKorisnik.setKorisnickoIme(loggedKorisnik.getKorisnickoIme());
        persistentKorisnik.setMejlAdresa(loggedKorisnik.getMejlAdresa());
        persistentKorisnik.setLozinka(loggedKorisnik.getLozinka());
        persistentKorisnik.setUloga(loggedKorisnik.getUloga());


        if (recenzijaDto.getOcena() != 0) {
            recenzija.setOcena(recenzijaDto.getOcena());
        }
        if (recenzijaDto.getTekst() != null && !recenzijaDto.getTekst().isEmpty()) {
            recenzija.setTekst(recenzijaDto.getTekst());
        }
        if (recenzijaDto.getDatumRecenzije() != null) {
            recenzija.setDatumRecenzije(recenzijaDto.getDatumRecenzije());
        }
        recenzija.setKorisnik(persistentKorisnik);



        recenzijaRepository.save(recenzija);

    }
}
