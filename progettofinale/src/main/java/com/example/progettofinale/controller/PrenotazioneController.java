package com.example.progettofinale.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.progettofinale.services.Ristorante;

@RestController
@RequestMapping("/api/prenotazione")
public class PrenotazioneController {

    private final Ristorante r;

    public PrenotazioneController(Ristorante r){
        this.r = r;
    }
}
