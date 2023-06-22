package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Knjiga;


import java.util.Set;

@Repository
public interface KnjigaRepository extends JpaRepository<Knjiga, Long> {

    //List<Knjiga> findAllByAutors(String autor);
  //  List<Knjiga> findByTitleContainingIgnoreCase(String query);

    Set<Knjiga> findByNaslovContainingIgnoreCase(String query);
    Knjiga findByISBN(String isbn);



}
