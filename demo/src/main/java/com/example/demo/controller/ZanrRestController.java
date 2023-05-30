package com.example.demo.controller;

import com.example.demo.dto.ZanrDto;
import com.example.demo.model.Zanr;
import com.example.demo.service.ZanrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ZanrRestController {

    @Autowired
    private ZanrService zanrService;

    // Endpoint za pregled svih žanrova
    @GetMapping("/api/zanrovi")
    public ResponseEntity<List<ZanrDto>> getAllGenres() {
        List<Zanr> zanrovi = zanrService.getAllGenres();

        if (zanrovi.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<ZanrDto> zanroviDto = new ArrayList<>();
        for (Zanr zanr : zanrovi) {
            ZanrDto zanrDto = new ZanrDto();
            zanrDto.setId(zanr.getId());
            zanrDto.setNaziv(zanr.getNaziv());
            zanroviDto.add(zanrDto);
        }

        return ResponseEntity.ok(zanroviDto);
    }

}
