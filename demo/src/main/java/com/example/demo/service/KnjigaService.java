package com.example.demo.service;

import com.example.demo.dto.KnjigaDto;
import com.example.demo.model.*;
import com.example.demo.repository.KnjigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class KnjigaService {

    @Autowired
    private KnjigaRepository knjigaRepository;
    @Autowired
    private AutorService autorService;

    @Autowired
    private StavkaPoliceService stavkaPoliceService;

    public Set <Knjiga> getAllBooks() {
        // Implementacija za dobavljanje svih knjiga
        Set <Knjiga> knjige = (Set<Knjiga>) knjigaRepository.findAll();// Logika za dobavljanje svih knjiga iz baze podataka ili nekog drugog izvora
        return knjige;
    }

    public List <Knjiga> getAllBooks2() {
        // Implementacija za dobavljanje svih knjiga
        List <Knjiga> knjige =  knjigaRepository.findAll();// Logika za dobavljanje svih knjiga iz baze podataka ili nekog drugog izvora
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

    public void novaKnjiga(KnjigaDto knjigaDto) {



        Knjiga knjiga = new Knjiga();
        knjiga.setNaslov(knjigaDto.getNaslov());
        knjiga.setNaslovnaFotografija(knjigaDto.getNaslovnaFotografija());
        knjiga.setISBN(knjigaDto.getISBN());
        knjiga.setDatumObjavljivanja(knjigaDto.getDatumObjavljivanja());
        knjiga.setBrojStrana(knjigaDto.getBrojStrana());
        knjiga.setOpis(knjigaDto.getOpis());
        knjiga.setZanrovi(knjigaDto.getZanrovi());
        knjiga.setOcena(knjigaDto.getOcena());



        knjigaRepository.save(knjiga);
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

    public void obrisiKnjiguPoISBN(String isbn, String korisnickoIme) {
        Knjiga knjiga = findByISBN(isbn);

        if (knjiga != null) {
            try {
                Autor autor = autorService.AutorBykorisnickoIme(korisnickoIme);
                autor.getSpisakKnjiga().remove(knjiga);
                //autor.getPolice().remove(knjiga);
                autorService.save(autor);
                // autorService.deleteKnjiga(knjiga, autor);

//               Set<StavkaPolice> stavkaPolice = (Set<StavkaPolice>) StavkaPoliceService.getById(id);
//               stavkaPolice.remove(knjiga);
               //stavkaPoliceService.save(stavkaPolice);





                // Then delete the knjiga object
                knjigaRepository.delete(knjiga);
                System.out.println("Knjiga deleted successfully.");
            } catch (Exception e) {
                System.out.println("An error occurred while deleting the knjiga: " + e.getMessage());
            }
        } else {
            System.out.println("Knjiga with ISBN " + isbn + " not found.");
        }
    }


    public boolean novaKnjigaAutoru(Knjiga existingKnjiga, Autor loggedAutor) {
        if (existingKnjiga == null || loggedAutor == null) {

            return false;
        }


        boolean bookExists = false;
        for (Knjiga knjiga : loggedAutor.getSpisakKnjiga()) {
                if (knjiga.getNaslov().equals(existingKnjiga.getNaslov())) {
                    // Knjiga sa istim nazivom već postoji
                    bookExists = true;
                    break;
            }
        }

        if (!bookExists) {
            loggedAutor.getSpisakKnjiga().add(existingKnjiga);
            autorService.save(loggedAutor);
        }
        return bookExists;
    }
}
