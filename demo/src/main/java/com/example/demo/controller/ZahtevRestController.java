package com.example.demo.controller;


import com.example.demo.dto.ZahtevDto;
import com.example.demo.service.ZahtevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZahtevRestController {

    @Autowired
    private ZahtevService zahtevService;

    @PostMapping("api/zahtev-autor")
    public ResponseEntity<String> signUp(@RequestBody ZahtevDto zahtevDto) {

        if (zahtevDto == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        zahtevService.registerUser(zahtevDto);

        return ResponseEntity.ok("Zahtev za aktivaciju autora uspesno poslat.");
    }


}
