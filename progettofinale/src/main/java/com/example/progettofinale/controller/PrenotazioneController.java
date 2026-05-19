package com.example.progettofinale.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.progettofinale.services.PrenotazioneService;

@RestController
@RequestMapping("/api/prenotazione")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService){
        this.prenotazioneService = prenotazioneService;
    }
}
