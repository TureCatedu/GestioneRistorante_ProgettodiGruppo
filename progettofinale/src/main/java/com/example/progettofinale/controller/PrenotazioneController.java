package com.example.progettofinale.controller;

import com.example.progettofinale.models.PrenotazioneRequest;
import com.example.progettofinale.models.PrenotazioneResponse;
import com.example.progettofinale.services.Ristorante;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazione")
public class PrenotazioneController {

    private final Ristorante ristorante;

    public PrenotazioneController(Ristorante ristorante) {
        this.ristorante = ristorante;
    }

    // Ricerca per ID (Accessibile a tutti)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public ResponseEntity<PrenotazioneResponse> findById(@PathVariable Integer id) {
        PrenotazioneResponse response = ristorante.cercaPrenotazione(id);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    // Ottieni tutte le prenotazioni (Solo staff)
    @GetMapping
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public ResponseEntity<List<PrenotazioneResponse>> findAll() {
        return ResponseEntity.ok(ristorante.getPrenotazioni());
    }

    // Ricerca per Nome e Cognome (Solo staff)
    @GetMapping("/cerca")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public ResponseEntity<List<PrenotazioneResponse>> findByCliente(
            @RequestParam String nome,
            @RequestParam String cognome) {
        List<PrenotazioneResponse> list = ristorante.cercaPrenotazionePerNomeCliente(nome, cognome);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    // cerca prenotazioni per giorno corrente (Solo staff)
    @GetMapping("/oggi")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public ResponseEntity<List<PrenotazioneResponse>> findPrenotazioniOggi() {
        List<PrenotazioneResponse> list = ristorante.getPrenotazioniOggi();
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    // cerca per data (Solo staff)
    @GetMapping("/cerca-data")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public ResponseEntity<List<PrenotazioneResponse>> findByData(@RequestParam String data) {
        List<PrenotazioneResponse> list = ristorante.cercaPrenotazionePerData(data);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    // Inserimento Prenotazione (Tutti)
    @PostMapping
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public ResponseEntity<?> aggiungiPrenotazione(@RequestBody PrenotazioneRequest request) {
        try {
            PrenotazioneResponse response = ristorante.aggiungiPrenotazione(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Modifica Prenotazione (Tutti)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public ResponseEntity<?> modificaPrenotazione(@PathVariable Integer id, @RequestBody PrenotazioneRequest request) {
        try {
            PrenotazioneResponse response = ristorante.modificaPrenotazione(id, request);
            if (response == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Cancellazione Prenotazione (Tutti)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public ResponseEntity<Void> eliminaPrenotazione(@PathVariable Integer id) {
        PrenotazioneResponse esistente = ristorante.cercaPrenotazione(id);
        if (esistente == null) {
            return ResponseEntity.notFound().build();
        }
        ristorante.eliminaPrenotazione(id);
        return ResponseEntity.noContent().build();
    }
}