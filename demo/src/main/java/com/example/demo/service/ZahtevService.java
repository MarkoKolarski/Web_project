package com.example.demo.service;


import com.example.demo.dto.AutorDto;
import com.example.demo.dto.ZahtevDto;
import com.example.demo.model.*;
import com.example.demo.repository.ZahtevZaAktivacijuNalogaAutoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ZahtevService {

    @Autowired
    private ZahtevZaAktivacijuNalogaAutoraRepository zahtevZaAktivacijuNalogaAutoraRepository;

    public void registerUser(ZahtevDto zahtevDto) {

        ZahtevZaAktivacijuNalogaAutora zahtev = new ZahtevZaAktivacijuNalogaAutora();
        zahtev.setEmail(zahtevDto.getEmail());
        zahtev.setTelefon(zahtevDto.getTelefon());
        zahtev.setPoruka(zahtevDto.getPoruka());
        zahtev.setStatus(Status.NA_CEKANJU);


        zahtevZaAktivacijuNalogaAutoraRepository.save(zahtev);
    }


    public AutorDto getZahtev(Long zahtevId) throws ChangeSetPersister.NotFoundException {
        Optional<ZahtevZaAktivacijuNalogaAutora> zahtev = zahtevZaAktivacijuNalogaAutoraRepository.findById(zahtevId);
        Autor autor = zahtev.get().getAutor();

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
}
