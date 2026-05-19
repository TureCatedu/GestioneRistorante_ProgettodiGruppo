package com.example.progettofinale.controller;

import com.example.progettofinale.models.Ruolo;
import com.example.progettofinale.models.Utente;
import com.example.progettofinale.repository.UtenteRepo;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {
    
    private final UtenteRepo utenteRepo;

    public UtenteController(UtenteRepo utenteRepo) {
        this.utenteRepo = utenteRepo;
    }

    // GET: trova utente per ID (Staff o l'utente stesso, per ora apriamo a tutti i loggati)
    @GetMapping("/{id}") 
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public ResponseEntity<Utente> findById(@PathVariable Integer id) {
        return utenteRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET: lista di tutti gli utenti (Solo Admin)
    @GetMapping
    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    public ResponseEntity<List<Utente>> findAll() {
        List<Utente> utenti = utenteRepo.findAll();
        return ResponseEntity.ok(utenti);
    }

    // GET: cerca utenti per cognome e nome (Admin e Camerieri)
    @GetMapping("/cognome-nome")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public ResponseEntity<List<Utente>> findByCognomeNome(@RequestParam String cognome, @RequestParam String nome) {
        List<Utente> utenti = utenteRepo.findByCognomeNomeIgnoreCase(cognome, nome);
        return utenti.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(utenti);
    }

    // GET: trova utenti per ruolo (Solo Admin)
    @GetMapping("/ruolo")
    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    public ResponseEntity<List<Utente>> findByRuolo(@RequestParam Ruolo ruolo) {
        List<Utente> utenti = utenteRepo.findByRuolo(ruolo);
        return ResponseEntity.ok(utenti);
    }

    // GET: Ottieni il profilo appena loggato 
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public ResponseEntity<Utente> getMioProfilo(Authentication authentication) {
        // authentication.getName() conterrà l'email dell'utente loggato con la Basic Auth
        return utenteRepo.findByEmail(authentication.getName())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Login (Nessuna annotazione, deve essere accessibile a chi non è ancora loggato)
    @PostMapping("/login")
    public ResponseEntity<Utente> login(@RequestParam String email, @RequestParam String password) {
        return utenteRepo.findByEmailPassword(email, password)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    // POST: Crea un nuovo utente (Nessuna annotazione, serve per la registrazione)
    @PostMapping
    public ResponseEntity<Utente> createUtente(@RequestBody Utente nuovoUtente) {
        Utente utenteSalvato = utenteRepo.save(nuovoUtente);
        return ResponseEntity.status(HttpStatus.CREATED).body(utenteSalvato); 
    }

    // PUT: Modifica un utente (Tutti i ruoli)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public ResponseEntity<Utente> updateUtente(@PathVariable Integer id, @RequestBody Utente utenteAggiornato) {
        return utenteRepo.findById(id).map(utenteEsistente -> {
            utenteEsistente.setNome(utenteAggiornato.getNome());
            utenteEsistente.setCognome(utenteAggiornato.getCognome());
            utenteEsistente.setEmail(utenteAggiornato.getEmail());
            utenteEsistente.setPassword(utenteAggiornato.getPassword()); 
            utenteEsistente.setRuolo(utenteAggiornato.getRuolo());
            
            Utente utenteSalvato = utenteRepo.save(utenteEsistente);
            return ResponseEntity.ok(utenteSalvato);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE: Rimuove un utente per ID (Solo Admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    public ResponseEntity<Void> deleteUtente(@PathVariable Integer id) {
        if (utenteRepo.existsById(id)) {
            utenteRepo.deleteById(id);
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.notFound().build(); 
    }
}