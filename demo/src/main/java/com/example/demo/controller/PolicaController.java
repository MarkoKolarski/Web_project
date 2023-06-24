package com.example.demo.controller;

import com.example.demo.dto.PolicaDto;
import com.example.demo.model.Polica;
import com.example.demo.service.KnjigaService;
import com.example.demo.service.KorisnikService;
import com.example.demo.service.PolicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.Set;

@Controller
public class PolicaController {

    @Autowired
    private PolicaService policaService;


    @GetMapping("/korisnici/{korisnikId}/police")
    public Set<PolicaDto> getUserBookshelf(@PathVariable Long korisnikId) throws ChangeSetPersister.NotFoundException {
        Set<Polica> police = policaService.getUserBookshelf(korisnikId);

        Set<PolicaDto> policeDto = new HashSet<>();
        for (Polica polica : police) {
            PolicaDto policaDto = new PolicaDto(polica);
            policeDto.add(policaDto);
        }

        return policeDto;
    }


}
