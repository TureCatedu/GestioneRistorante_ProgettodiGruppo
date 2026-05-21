package com.example.progettofinale.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.progettofinale.models.Utente;
import com.example.progettofinale.repository.UtenteRepo;

@Controller
public class ViewController {

    private final UtenteRepo utenteRepo;
    public ViewController(UtenteRepo utenteRepo) {
        this.utenteRepo = utenteRepo;
    }
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    @GetMapping("/prenotazioni/nuova")
    public String nuovaPrenotazione(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            // Recuperiamo l'utente intero dal database usando l'email dell'utente loggato
            Utente utenteLoggato = utenteRepo.findByEmail(userDetails.getUsername()).orElse(null);
            
            if (utenteLoggato != null) {
                // Passiamo l'id dell'utente alla pagina HTML tramite il Model
                model.addAttribute("utenteLoggatoId", utenteLoggato.getId());
            }
        }
        return "nuova_prenotazione";
    }
}