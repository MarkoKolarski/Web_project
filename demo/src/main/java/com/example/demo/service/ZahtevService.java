package com.example.demo.service;


import com.example.demo.dto.AutorDto;
import com.example.demo.dto.ZahtevDto;
import com.example.demo.model.*;
import com.example.demo.repository.ZahtevZaAktivacijuNalogaAutoraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ZahtevService {

    @Autowired
    private ZahtevZaAktivacijuNalogaAutoraRepository zahtevZaAktivacijuNalogaAutoraRepository;

    @Autowired
    private AutorService autorService;

    public boolean registerUser(ZahtevDto zahtevDto) {

        ZahtevZaAktivacijuNalogaAutora zahtev = new ZahtevZaAktivacijuNalogaAutora();

        //Autor autor = autorService.AutorBykorisnickoIme(zahtevDto.getAutor().getKorisnickoIme()) ;
        if (zahtevDto.getAutor() != null && zahtevDto.getAutor().getKorisnickoIme() != null) {
            Autor autor = autorService.AutorBykorisnickoIme(zahtevDto.getAutor().getKorisnickoIme());
            if (autor != null) {
                zahtev.setAutor(autor);
            } else {
                return false;

            }
        }


            if (zahtevDto.getEmail() != null && !zahtevDto.getEmail().isEmpty()) {
                zahtev.setEmail(zahtevDto.getEmail());
            }
            if (zahtevDto.getTelefon() != null && !zahtevDto.getTelefon().isEmpty()) {
                zahtev.setTelefon(zahtevDto.getTelefon());
            }
            if (zahtevDto.getPoruka() != null && !zahtevDto.getPoruka().isEmpty()) {
                zahtev.setPoruka(zahtevDto.getPoruka());
            }


        LocalDateTime currentDateTime = LocalDateTime.now();

        // Convert LocalDateTime to Date
        ZoneId zoneId = ZoneId.systemDefault();
        Date date = Date.from(currentDateTime.atZone(zoneId).toInstant());

        zahtev.setDatum(date);
        zahtev.setStatus(Status.NA_CEKANJU);


            zahtevZaAktivacijuNalogaAutoraRepository.save(zahtev);
            return true;
    }


    public AutorDto getZahtev(Long zahtevId) {
        Optional<ZahtevZaAktivacijuNalogaAutora> zahtev = zahtevZaAktivacijuNalogaAutoraRepository.findById(zahtevId);
        if(zahtev == null || zahtev.isEmpty()){
            return null;
        }
        Autor autor = zahtev.get().getAutor();

        if(autor == null || autor.equals(null)){
            return null;
        }

       // List<AutorDto> autoriDto = new ArrayList<>();

            AutorDto autorDto = new AutorDto();
            autorDto.setId(autor.getId());
            autorDto.setAktivan(autor.getAktivan());
            autorDto.setIme(autor.getIme());
            autorDto.setPrezime(autor.getPrezime());
            autorDto.setKorisnickoIme(autor.getKorisnickoIme());
            autorDto.setMejlAdresa(autor.getMejlAdresa());
            autorDto.setLozinka(autor.getLozinka());
            autorDto.setDatumRodjenja(autor.getDatumRodjenja());
            autorDto.setProfilnaSlika(autor.getProfilnaSlika());
            autorDto.setOpis(autor.getOpis());
            autorDto.setUloga(autor.getUloga());
            autorDto.setPolice(autor.getPolice());
           // autoriDto.add(autorDto);


        return autorDto;
    }

    public List<ZahtevZaAktivacijuNalogaAutora> getAll() {
        return zahtevZaAktivacijuNalogaAutoraRepository.findAll();
    }


    public Optional<ZahtevZaAktivacijuNalogaAutora> findById(Long id) {
        return zahtevZaAktivacijuNalogaAutoraRepository.findById(id);
    }

    public void save(ZahtevZaAktivacijuNalogaAutora zahtevZaAktivacijuNalogaAutora) {
        zahtevZaAktivacijuNalogaAutoraRepository.save(zahtevZaAktivacijuNalogaAutora);
    }
}
