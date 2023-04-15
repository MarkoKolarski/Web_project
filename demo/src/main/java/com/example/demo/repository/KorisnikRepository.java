package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Korisnik;

import java.util.List;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    /*
    Spring na osnovu imena metode kreira upit za bazu.

    Traži sve zaposlene (employee) koji imaju poziciju koja se prosledjuje ovoj metodi kao string (String position).
    Sortira sve koje je pronašao po imenu i vraća kolekciju zaposlenih.
 */
    //List<Korisnik> findAllByPositionOrderByName(String position);
    List<Korisnik> findAllByPrezime(String prezime);

    /*
        Traži sve zaposlene po imenu ili prezimenu.
     */
   // List<Korisnik> findByFirstNameOrLastName(String firstName, String lastName);

    /*
        Traži sve zaposlene po imenu, ignorišu se mala i velika slova.
     */
    //List<Korisnik> findByFirstNameIgnoreCase(String firstName);

    /*
        Traži sve zaposlene po nazivu departmana.
     */
   // List<Korisnik> findByDepartmentName(String departmentName);
}