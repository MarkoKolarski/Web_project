package com.example.demo.service;

import com.example.demo.dto.PolicaDto;
import com.example.demo.model.*;
import com.example.demo.repository.KnjigaRepository;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.PolicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PolicaService {


    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private static PolicaRepository staticpolicaRepository;

    @Autowired
    private  PolicaRepository policaRepository;
    @Autowired
    private KnjigaRepository knjigaRepository;

    public PolicaService(KorisnikRepository korisnikRepository, KnjigaRepository knjigaRepository) {
        this.korisnikRepository = korisnikRepository;
        this.knjigaRepository = knjigaRepository;
    }

    public static Optional<Polica> findById(Long id) {
        return staticpolicaRepository.findById(id);
    }

    public Set<Polica> getUserBookshelf(Long userId) throws ChangeSetPersister.NotFoundException {
        Optional<Korisnik> korisnik = korisnikRepository.findById(userId);
        Set<Polica> police = korisnik.get().getPolice();

        return police;
    }


    public void novePolice(PolicaDto policaDto) {

        Set<Polica> police = new HashSet<>();
        for (Polica polica : police) {
//            autorDto.setId(autor.getId());
//            autorDto.setAktivan(autor.getAktivan());
//            autorDto.setIme(autor.getIme());
//            autorDto.setPrezime(autor.getPrezime());
//            autorDto.setKorisnickoIme(autor.getKorisnickoIme());
//            autorDto.setMejlAdresa(autor.getMejlAdresa());
//            autorDto.setLozinka(autor.getLozinka());
//            autorDto.setDatumRodjenja(autor.getDatumRodjenja());
//            autorDto.setProfilnaSlika(autor.getProfilnaSlika());
//            autorDto.setOpis(autor.getOpis());
//            autorDto.setUloga(autor.getUloga());
//            autorDto.setPolice(autor.getPolice());
//            autoriDto.add(autorDto);
        }

//        return ResponseEntity.ok(autoriDto);
    }

//    public void promeniPolicu(Optional<Polica> existingPolica, PolicaDto policaDto) {
//
//
//        if (existingPolica.isPresent()) {
//            Polica polica = existingPolica.get();
//            polica.setNaziv(policaDto.getNaziv());
//            polica.setPrimarna(policaDto.getPrimarna());
//            polica.setStavkePolice(policaDto.getStavkePolice());
//
//            policaRepository.save(polica);
//        }
//    }


    public void novaPolica(PolicaDto policaDto) {

        Polica polica = new Polica();
        polica.setNaziv(policaDto.getNaziv());
        polica.setPrimarna(policaDto.getPrimarna());
        polica.setStavkePolice(policaDto.getStavkePolice());

        policaRepository.save(polica);
    }

    public void deletePolica(Polica polica) {
        policaRepository.delete(polica);
    }

//    public Polica nadjiPolicu(long id, long userId) {
//       Optional<Korisnik> korisnik = korisnikRepository.findById(userId);
//       Polica polica = (Polica) korisnikRepository.korisnik.get().getPolice();
//       return polica;
//    }

//    public Polica nadjiPolicu(long id, long userId) {
//        Optional<Korisnik> korisnikOptional = korisnikRepository.findById(userId);
//
//        if (korisnikOptional.isPresent()) {
//            Korisnik korisnik = korisnikOptional.get();
//
//            Set<Polica> police = korisnik.getPolice(); // Assuming Korisnik has a method to get the set of policies
//
//            Optional<Polica> policaOptional = police.stream()
//                    .filter(polica -> polica.getId() == id)
//                    .findFirst();
//
//            if (policaOptional.isPresent()) {
//                return policaOptional.get();
//            } else {
//                // Handle case when policy with the given ID is not found
//                try {
//                    throw new ChangeSetPersister.NotFoundException();
//                } catch (ChangeSetPersister.NotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        } else {
//            // Handle case when user with the given ID is not found
//            try {
//                throw new ChangeSetPersister.NotFoundException();
//            } catch (ChangeSetPersister.NotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public Polica nadjiPolicu(long id, long userId) throws ChangeSetPersister.NotFoundException {
        Optional<Korisnik> korisnikOptional = korisnikRepository.findById(userId);

        if (korisnikOptional.isPresent()) {
            Korisnik korisnik = korisnikOptional.get();
            Set<Polica> police = korisnik.getPolice();

            for (Polica polica : police) {
                if (polica.getId().equals(id)) {
                    return polica;
                }
            }
        }

        throw new ChangeSetPersister.NotFoundException(); // Handle case when policy with the given ID is not found
    }

    public Polica findByNaziv(String nazivPolice) {
        return policaRepository.findByNazivContainingIgnoreCase(nazivPolice);
    }

    public boolean dodajKnjigu(Knjiga existingKnjiga, Polica existingPolica) {
        if (existingKnjiga == null || existingPolica == null) {
            // Handle null objects appropriately
            return false;
        }

        // Check if a book with the same name already exists in existingPolica
        boolean bookExists = false;
        for (StavkaPolice stavkaPolice : existingPolica.getStavkePolice()) {
            if (stavkaPolice.getKnjiga().getNaslov().equals(existingKnjiga.getNaslov())) {
                // Book with the same name already exists
                bookExists = true;
                break;
            }
        }

        if (!bookExists) {
            StavkaPolice stavkaPolice = new StavkaPolice();
            stavkaPolice.setKnjiga(existingKnjiga);
            existingPolica.getStavkePolice().add(stavkaPolice);

            // Make sure policaRepository is instantiated and available
            policaRepository.save(existingPolica);
        }
        return bookExists;
    }



}
