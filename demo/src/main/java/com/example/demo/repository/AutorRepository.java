package com.example.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Autor;


import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {


    Optional<Autor> findById(Long id);

    Autor findBykorisnickoIme(String korisnickoIme);

}



