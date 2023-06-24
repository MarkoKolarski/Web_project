package com.example.demo.controller;

import com.example.demo.model.Knjiga;
import com.example.demo.model.Zanr;
import com.example.demo.service.ZanrService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;



@Controller
public class ZanrController {

    @Autowired
    private ZanrService zanrService;


    @GetMapping("/zanrovi")
    public String getAllGenres(Model model) {
        List<Zanr> zanrovi = zanrService.getAllGenres();
        model.addAttribute("zanrovi", zanrovi);
        return "zanrovi";
    }

}
