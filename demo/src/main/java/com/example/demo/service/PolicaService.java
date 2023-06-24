package com.example.demo.service;

import com.example.demo.dto.PolicaDto;
import com.example.demo.model.*;
import com.example.demo.repository.KnjigaRepository;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.repository.PolicaRepository;
import com.example.demo.repository.StavkaPoliceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class PolicaService {


    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;

    @Autowired
    private KorisnikService korisnikService;

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

    public  List<Polica> findAll() {
        List<Polica> police = new ArrayList<>();
        return  police =  policaRepository.findAll();
    }

    public Set<Polica> getUserBookshelf(Long userId){
        Optional<Korisnik> korisnik = korisnikRepository.findById(userId);
        Set<Polica> police = korisnik.get().getPolice();

        return police;
    }


    public List<Polica> getAllPolice() {
        return policaRepository.findAll();
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

    public Polica nadjiPolicu(String naziv, long userId) {
        Optional<Korisnik> korisnikOptional = korisnikRepository.findById(userId);

        if (korisnikOptional.isPresent()) {
            Korisnik korisnik = korisnikOptional.get();
            Set<Polica> police = korisnik.getPolice();

            for (Polica polica : police) {
                if (polica.getNaziv().equals(naziv)) {
                    return polica;
                }

            }
        }

        return null;
    }

    public Polica getPolicaById(Long policaId) {
        return policaRepository.findById(policaId)
                .orElseThrow(() -> new IllegalArgumentException("Polica sa datim ID-jem ne postoji."));
    }

    public Polica findByNaziv(String nazivPolice) {
        Polica polica = policaRepository.findByNazivContainingIgnoreCase(nazivPolice);
        return polica;
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


            policaRepository.save(existingPolica);
        }
        return bookExists;
    }


    public boolean izbaciKnjigu(Knjiga existingKnjiga, Polica existingPolica) {
        if (existingKnjiga == null || existingPolica == null) {
            return false;
        }

        // Proverava da li knjiga sa istim imenom vec postoji u existingPolica
        boolean bookExists = false;
        for (StavkaPolice stavkaPolice : existingPolica.getStavkePolice()) {
            if (stavkaPolice.getKnjiga().getNaslov().equals(existingKnjiga.getNaslov())) {
                // Knjiga sa tim imenom vec postoji
                bookExists = true;
                break;
            }
        }

        if (bookExists) {
            // Nadji StavkaPolice objekat koji sadrži existingKnjiga
            StavkaPolice stavkaPoliceToRemove = null;
            for (StavkaPolice stavkaPolice : existingPolica.getStavkePolice()) {
                if (stavkaPolice.getKnjiga().equals(existingKnjiga)) {
                    stavkaPoliceToRemove = stavkaPolice;
                    break;
                }
            }

            if (stavkaPoliceToRemove != null) {
                // Remove the StavkaPolice object from existingPolica
                existingPolica.getStavkePolice().remove(stavkaPoliceToRemove);

                // Save the updated Polica
                policaRepository.save(existingPolica);
            }
        }



        return bookExists;
    }

    public boolean isBookOnPrimaryPolica(Knjiga existingKnjiga, Korisnik loggedKorisnik) {
        List<Polica> primarnePolice = policaRepository.findByPrimarnaIsTrue();

        for (Polica polica : primarnePolice) {
            boolean isKorisnikPolica = checkIfKorisnikPolica(polica, loggedKorisnik);
            if (isKorisnikPolica) {
                for (StavkaPolice stavkaPolice : polica.getStavkePolice()) {
                    if (stavkaPolice.getKnjiga().equals(existingKnjiga)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    private boolean checkIfKorisnikPolica(Polica polica, Korisnik loggedKorisnik) {
        return loggedKorisnik.getPolice().stream()
                .map(Polica::getId) // Assuming getId returns the unique identifier of Polica
                .anyMatch(polica.getId()::equals);
    }

        public boolean isBookOnPrimaryPolicaForLoggedUser(Knjiga existingKnjiga, Korisnik loggedKorisnik, Polica existingPolica) {
        List<Polica> primarnePolice = policaRepository.findByPrimarnaIsTrue();

        for (Polica polica : primarnePolice) {
            boolean isKorisnikPolica = checkIfKorisnikPolica(polica, loggedKorisnik);
            if (isKorisnikPolica) {
                boolean isBookOnPrimaryPolica = false;
                for (StavkaPolice stavkaPolice : polica.getStavkePolice()) {
                    if (stavkaPolice.getKnjiga().equals(existingKnjiga)) {
                        if(isPrimaryPolica(existingPolica)) {
                            isBookOnPrimaryPolica = true;
                            break;
                        }
                    }
                }
                if (isBookOnPrimaryPolica) {
                    return true; // Book is already on a primary polica, return true
                }
            }
        }


        return false; // Book is not on any primary
    }


    // Add a new method to check if the polica is a primary polica
    private boolean isPrimaryPolica(Polica polica) {
        return polica.getPrimarna();
    }


    public Set<Polica> policaPoNazivu(String naziv) {
        Korisnik korisnik = korisnikService.findBykorisnickoIme(naziv);
        Set<Polica> police = korisnik.getPolice();

        return police;

    }
}
