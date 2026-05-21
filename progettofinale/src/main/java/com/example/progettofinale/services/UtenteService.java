package com.example.progettofinale.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.progettofinale.models.LoginResponse;
import com.example.progettofinale.models.Ruolo;
import com.example.progettofinale.models.Utente;
import com.example.progettofinale.repository.UtenteRepo;

@Service
public class UtenteService {
    private final UtenteRepo utenteRepo;
    //costruttore per injection di UtenteRepo
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
    //trova utente per id completo
    public Optional<Utente> findByIdComplete(Integer id) {
        return utenteRepo.findById(id);
    }
    //trova utente per email con anche password
    public Optional<Utente> findByEmailComplete(String email) {
        return utenteRepo.findByEmail(email);
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
    // salva utente
    public LoginResponse save(Utente utente) {
        return toLoginResponse(utenteRepo.save(utente));
    }
    // elimina utente
    public void deleteById(Integer id) {
        utenteRepo.deleteById(id);
    }
}
