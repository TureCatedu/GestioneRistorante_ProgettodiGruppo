package com.example.progettofinale.controller;

import com.example.progettofinale.models.Ruolo;
import com.example.progettofinale.models.Utente;
import com.example.progettofinale.repository.UtenteRepo;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/utenti")
public class UtenteController {
    
    private final UtenteRepo utenteRepo;

    public UtenteController(UtenteRepo utenteRepo) {
        this.utenteRepo = utenteRepo;
    }

    // GET: trova utente per ID
    @GetMapping("/{id}") 
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String findById(@PathVariable Integer id, Model model) {

        Utente utente = utenteRepo.findById(id).orElse(null);

        model.addAttribute("utente", utente);

        return "utente";
    }

    // GET: lista di tutti gli utenti (Solo Admin)
    @GetMapping
    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    public String findAll(Model model) {

        List<Utente> utenti = utenteRepo.findAll();

        model.addAttribute("utenti", utenti);

        return "utenti";
    }

    // GET: cerca utenti per cognome e nome (Admin e Camerieri)
    @GetMapping("/cognome-nome")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public String findByCognomeNome(@RequestParam String cognome,
                                     @RequestParam String nome,
                                     Model model) {

        List<Utente> utenti =
                utenteRepo.findByCognomeAndNomeIgnoreCase(cognome, nome);

        model.addAttribute("utenti", utenti);

        return "utenti";
    }

    // GET: trova utenti per ruolo (Solo Admin)
    @GetMapping("/ruolo")
    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    public String findByRuolo(@RequestParam Ruolo ruolo,
                              Model model) {

        List<Utente> utenti = utenteRepo.findByRuolo(ruolo);

        model.addAttribute("utenti", utenti);

        return "utenti";
    }
    // GET: Ottieni il profilo appena loggato 
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String getMioProfilo(org.springframework.security.core.Authentication authentication,
                                Model model) {

        Utente utente = utenteRepo.findByEmail(authentication.getName()).orElse(null);

        model.addAttribute("utente", utente);

        return "profilo";
    }

    // POST: Login 
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {

        Utente utente = utenteRepo.findByEmailAndPassword(email, password).orElse(null);

        model.addAttribute("utente", utente);

        return "profilo";
    }

    // POST: Crea un nuovo utente
    @PostMapping
    public String createUtente(Utente nuovoUtente) {

        utenteRepo.save(nuovoUtente);

        return "redirect:/utenti";
    }
    // PUT: Modifica un utente (Tutti i ruoli)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String updateUtente(@PathVariable Integer id,
                               Utente utenteAggiornato) {

        Utente utenteEsistente = utenteRepo.findById(id).orElse(null);

        if (utenteEsistente != null) {
            utenteEsistente.setNome(utenteAggiornato.getNome());
            utenteEsistente.setCognome(utenteAggiornato.getCognome());
            utenteEsistente.setEmail(utenteAggiornato.getEmail());
            utenteEsistente.setPassword(utenteAggiornato.getPassword());
            utenteEsistente.setRuolo(utenteAggiornato.getRuolo());

            utenteRepo.save(utenteEsistente);
        }

        return "redirect:/utenti";
    }

    // DELETE: Rimuove un utente per ID (Solo Admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    public String deleteUtente(@PathVariable Integer id) {

        utenteRepo.deleteById(id);

        return "redirect:/utenti";
    }
}