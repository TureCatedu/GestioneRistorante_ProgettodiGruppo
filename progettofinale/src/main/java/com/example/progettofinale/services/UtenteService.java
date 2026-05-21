package com.example.progettofinale.services;

import java.util.List;

import com.example.progettofinale.models.LoginResponse;
import com.example.progettofinale.models.Ruolo;
import com.example.progettofinale.models.Utente;
import com.example.progettofinale.repository.UtenteRepo;

public class UtenteService {
    private final UtenteRepo utenteRepo;

    public UtenteService(UtenteRepo utenteRepo) {
        this.utenteRepo = utenteRepo;
    }
    // da utente a login response
    public LoginResponse toLoginResponse(Utente utente) {
        return new LoginResponse(utente.getId(), utente.getNome(), utente.getCognome(), utente.getEmail(), utente.getRuolo());
    }
    // trova utente per ID
    public LoginResponse findById(Integer id) {
        return toLoginResponse(utenteRepo.findById(id).orElse(null));
    }
    //find by email
    public LoginResponse findByEmail(String email) {
        return toLoginResponse(utenteRepo.findByEmail(email).orElse(null));
    }
    //trova tutti gli utenti
    public List<LoginResponse> findAll() {
        return utenteRepo.findAll().stream().map(this::toLoginResponse).toList();
    }
    // trova utenti per cognome e nome
    public List<LoginResponse> findByCognomeAndNomeIgnoreCase(String cognome, String nome) {
        return utenteRepo.findByCognomeAndNomeIgnoreCase(cognome, nome).stream().map(this::toLoginResponse).toList();
    }
    // trova utenti per ruolo
    public List<LoginResponse> findByRuolo(Ruolo ruolo) {
        return utenteRepo.findByRuolo(ruolo).stream().map(this::toLoginResponse).toList();
    }
}
