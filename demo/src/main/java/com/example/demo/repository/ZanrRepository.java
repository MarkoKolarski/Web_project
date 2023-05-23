package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Zanr;
@Repository
public interface ZanrRepository extends JpaRepository<Zanr, Long> {
}
