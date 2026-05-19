package com.example.progettofinale.controller;

import com.example.progettofinale.models.Ruolo;
import com.example.progettofinale.models.Utente;
import com.example.progettofinale.repository.UtenteRepo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {
    
    private final UtenteRepo utenteRepo;

    public UtenteController(UtenteRepo utenteRepo) {
        this.utenteRepo = utenteRepo;
    }

    // GET: trova utente per ID
    @GetMapping("/{id}") 
    public ResponseEntity<Utente> findById(@PathVariable Integer id) {
        return utenteRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET: lista di tutti gli utenti
    @GetMapping
    public ResponseEntity<List<Utente>> findAll() {
        List<Utente> utenti = utenteRepo.findAll();
        return ResponseEntity.ok(utenti);
    }

    // GET: cerca utenti per cognome e nome
    @GetMapping("/cognome-nome")
    public ResponseEntity<List<Utente>> findByCognomeNome(@RequestParam String cognome, @RequestParam String nome) {
        List<Utente> utenti = utenteRepo.findByCognomeNomeIgnoreCase(cognome, nome);
        return utenti.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(utenti);
    }

    // GET: trova utenti per ruolo (es. tutti i camerieri)
    @GetMapping("/ruolo")
    public ResponseEntity<List<Utente>> findByRuolo(@RequestParam Ruolo ruolo) {
        List<Utente> utenti = utenteRepo.findByRuolo(ruolo);
        return ResponseEntity.ok(utenti);
    }

    // POST: Login
    @PostMapping("/login")
    public ResponseEntity<Utente> login(@RequestParam String email, @RequestParam String password) {
        return utenteRepo.findByEmailPassword(email, password)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    // POST: Crea un nuovo utente
    @PostMapping
    public ResponseEntity<Utente> createUtente(@RequestBody Utente nuovoUtente) {
        Utente utenteSalvato = utenteRepo.save(nuovoUtente);
        return ResponseEntity.status(HttpStatus.CREATED).body(utenteSalvato); // Ritorna 201 Created
    }

    // PUT: 
    @PutMapping("/{id}")
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

    // DELETE: Rimuove un utente per ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtente(@PathVariable Integer id) {
        if (utenteRepo.existsById(id)) {
            utenteRepo.deleteById(id);
            return ResponseEntity.noContent().build(); // Ritorna 204 No Content se l'eliminazione ha successo
        }
        return ResponseEntity.notFound().build(); // Ritorna 404 Not Found se l'ID non esiste
    }
}