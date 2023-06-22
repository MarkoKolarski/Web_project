package com.example.demo.service;

import com.example.demo.dto.KnjigaDto;
import com.example.demo.model.*;
import com.example.demo.repository.KnjigaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class KnjigaService {

    @Autowired
    private KnjigaRepository knjigaRepository;
    @Autowired
    private AutorService autorService;

    @Autowired
    private PolicaService policaService;

    @Autowired
    private StavkaPoliceService stavkaPoliceService;

    public Set <Knjiga> getAllBooks() {
        // Implementacija za dobavljanje svih knjiga
        Set <Knjiga> knjige = (Set<Knjiga>) knjigaRepository.findAll();// Logika za dobavljanje svih knjiga iz baze podataka ili nekog drugog izvora
        return knjige;
    }

    public List <Knjiga> getAllBooks2() {
        // Implementacija za dobavljanje svih knjiga
        List <Knjiga> knjige = (List<Knjiga>) knjigaRepository.findAll();// Logika za dobavljanje svih knjiga iz baze podataka ili nekog drugog izvora
        return knjige;
    }


    public Set <Knjiga> findByNaslovContainingIgnoreCase(String query){
        Set<Knjiga> knjige = knjigaRepository.findByNaslovContainingIgnoreCase( query);
        return knjige;
    }


    public Set<KnjigaDto> searchBooks(String query) {
        Set <Knjiga> knjige = new HashSet<>();
        knjige =  knjigaRepository.findByNaslovContainingIgnoreCase(query);
        Set <KnjigaDto> knjigeDto = new HashSet<>();

        for (Knjiga knjiga : knjige) {
            KnjigaDto knjigaDto = new KnjigaDto(knjiga);
            knjigeDto.add(knjigaDto);
        }

        return knjigeDto;
    }

    public Knjiga novaKnjiga(KnjigaDto knjigaDto) {



        Knjiga knjiga = new Knjiga();
        if (knjigaDto.getNaslov() != null && !knjigaDto.getNaslov().isEmpty()) {
            knjiga.setNaslov(knjigaDto.getNaslov());
        }
        if (knjigaDto.getNaslovnaFotografija() != null && !knjigaDto.getNaslovnaFotografija().isEmpty()) {
            knjiga.setNaslovnaFotografija(knjigaDto.getNaslovnaFotografija());
        }
        if (knjigaDto.getISBN() != null && !knjigaDto.getISBN().isEmpty()) {
            knjiga.setISBN(knjigaDto.getISBN());
        }
        if (knjigaDto.getDatumObjavljivanja() != null) {
            knjiga.setDatumObjavljivanja(knjigaDto.getDatumObjavljivanja());
        }
        if (knjigaDto.getBrojStrana() > 0) {
            knjiga.setBrojStrana(knjigaDto.getBrojStrana());
        }
        if (knjigaDto.getOpis() != null && !knjigaDto.getOpis().isEmpty()) {
            knjiga.setOpis(knjigaDto.getOpis());
        }
        if (knjigaDto.getZanrovi() != null && !knjigaDto.getZanrovi().isEmpty()) {
            knjiga.setZanrovi(knjigaDto.getZanrovi());
        }
        if (knjigaDto.getOcena() != null && knjigaDto.getOcena() >= 0) {
            knjiga.setOcena(knjigaDto.getOcena());
        }

        knjigaRepository.save(knjiga);
        return knjiga;
    }


    public Knjiga findByISBN(String isbn) {
        Knjiga knjiga = knjigaRepository.findByISBN(isbn);
        return  knjiga;
    }

    public void promeniKnjigu(Knjiga knjiga, KnjigaDto knjigaDto) {

        if (knjigaDto.getNaslov() != null && !knjigaDto.getNaslov().isEmpty()) {
            knjiga.setNaslov(knjigaDto.getNaslov());
        }
        if (knjigaDto.getNaslovnaFotografija() != null && !knjigaDto.getNaslovnaFotografija().isEmpty()) {
            knjiga.setNaslovnaFotografija(knjigaDto.getNaslovnaFotografija());
        }
        if (knjigaDto.getISBN() != null && !knjigaDto.getISBN().isEmpty()) {
            knjiga.setISBN(knjigaDto.getISBN());
        }
        if (knjigaDto.getDatumObjavljivanja() != null) {
            knjiga.setDatumObjavljivanja(knjigaDto.getDatumObjavljivanja());
        }
        if (knjigaDto.getBrojStrana() > 0) {
            knjiga.setBrojStrana(knjigaDto.getBrojStrana());
        }
        if (knjigaDto.getOpis() != null && !knjigaDto.getOpis().isEmpty()) {
            knjiga.setOpis(knjigaDto.getOpis());
        }
        if (knjigaDto.getZanrovi() != null && !knjigaDto.getZanrovi().isEmpty()) {
            knjiga.setZanrovi(knjigaDto.getZanrovi());
        }
        if (knjigaDto.getOcena() != null && knjigaDto.getOcena() >= 0) {
            knjiga.setOcena(knjigaDto.getOcena());
        }


            knjigaRepository.save(knjiga);
    }

    public void promeniKnjiguAutora(Knjiga existingKnjiga, KnjigaDto knjigaDto, Autor loggedAutor) {

        if (loggedAutor.getSpisakKnjiga().contains(existingKnjiga)) {
            existingKnjiga.setNaslov(knjigaDto.getNaslov());

            if (knjigaDto.getNaslov() != null && !knjigaDto.getNaslov().isEmpty()) {
                existingKnjiga.setNaslov(knjigaDto.getNaslov());
            }
            if (knjigaDto.getNaslovnaFotografija() != null && !knjigaDto.getNaslovnaFotografija().isEmpty()) {
                existingKnjiga.setNaslovnaFotografija(knjigaDto.getNaslovnaFotografija());
            }
            if (knjigaDto.getISBN() != null && !knjigaDto.getISBN().isEmpty()) {
                existingKnjiga.setISBN(knjigaDto.getISBN());
            }
            if (knjigaDto.getDatumObjavljivanja() != null) {
                existingKnjiga.setDatumObjavljivanja(knjigaDto.getDatumObjavljivanja());
            }
            if (knjigaDto.getBrojStrana() > 0) {
                existingKnjiga.setBrojStrana(knjigaDto.getBrojStrana());
            }
            if (knjigaDto.getOpis() != null && !knjigaDto.getOpis().isEmpty()) {
                existingKnjiga.setOpis(knjigaDto.getOpis());
            }
            if (knjigaDto.getZanrovi() != null && !knjigaDto.getZanrovi().isEmpty()) {
                existingKnjiga.setZanrovi(knjigaDto.getZanrovi());
            }
            if (knjigaDto.getOcena() != null && knjigaDto.getOcena() >= 0) {
                existingKnjiga.setOcena(knjigaDto.getOcena());
            }
        }

        knjigaRepository.save(existingKnjiga);
    }


    public boolean obrisiKnjiguPoISBN(String isbn, String korisnickoIme) {
        Knjiga knjiga = findByISBN(isbn);


        if (knjiga != null) {
            try {
                Autor autor = autorService.AutorBykorisnickoIme(korisnickoIme);

                knjiga.getZanrovi().clear();

                // Remove references to knjiga from Autor's SpisakKnjiga and Police
                autor.getSpisakKnjiga().remove(knjiga);

//                Set<StavkaPolice> stavkaPoliceSet = autor.getPolice().stream()
//                        .flatMap(polica -> polica.getStavkePolice().stream())
//                        .collect(Collectors.toSet());

              //  Set<StavkaPolice> stavkaPoliceSet = stavkaPoliceService.findByKnjiga(knjiga);
                List<Polica> police = policaService.findAll();


                for (Polica polica : police) {
                            policaService.izbaciKnjigu(knjiga, polica);
                        }



                knjigaRepository.delete(knjiga);
                autorService.save(autor);

                System.out.println("Knjiga uspešno obrisana.");
                return true;
            } catch (Exception e) {
                System.out.println("Eror je nastao prilikom brisanja knjige: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("Knjiga sa ISBN " + isbn + " nije pronadjena.");
            return false;
        }
    }






    public boolean novaKnjigaAutoru(KnjigaDto knjigaDto, Autor loggedAutor) {
        if (knjigaDto == null || loggedAutor == null) {

            return false;
        }


        boolean bookExists = false;
        for (Knjiga knjiga : loggedAutor.getSpisakKnjiga()) {
                if (knjiga.getNaslov().equals(knjigaDto.getNaslov()) || knjiga.getISBN().equals(knjigaDto.getISBN()) ) {
                    // Knjiga sa istim nazivom ili ISBN-om već postoji
                    bookExists = true;
                    break;
            }
        }


        if (!bookExists) {
            Knjiga knjiga = new Knjiga();
            knjiga = novaKnjiga(knjigaDto);
            loggedAutor.getSpisakKnjiga().add(knjiga);
            autorService.save(loggedAutor);
        }
        return bookExists;
    }
}
