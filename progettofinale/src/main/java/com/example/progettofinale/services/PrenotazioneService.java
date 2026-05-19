package com.example.progettofinale.services;

import org.springframework.stereotype.Service;

import com.example.progettofinale.models.Prenotazione;
import com.example.progettofinale.repository.PrenotazioneRepo;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepo prenotazioneRepo;

    public PrenotazioneService(PrenotazioneRepo prenotazioneRepo){
        this.prenotazioneRepo = prenotazioneRepo;
    }

    public Prenotazione creaPrenotazione(Prenotazione p){
        return prenotazioneRepo.save(p);
    }

    public Prenotazione getById(int id) {
        return prenotazioneRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));
    }

}
