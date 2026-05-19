package com.example.progettofinale.controller;

import com.example.progettofinale.models.PrenotazioneRequest;
import com.example.progettofinale.models.PrenotazioneResponse;
import com.example.progettofinale.services.Ristorante;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazione")
public class PrenotazioneController {

    private final Ristorante ristorante;

    public PrenotazioneController(Ristorante ristorante) {
        this.ristorante = ristorante;
    }

    // Ricerca per ID
    @GetMapping("/{id}")
    public ResponseEntity<PrenotazioneResponse> findById(@PathVariable Integer id) {
        PrenotazioneResponse response = ristorante.cercaPrenotazione(id);
        return (response != null) ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    // Ottieni tutte le prenotazioni
    @GetMapping
    public ResponseEntity<List<PrenotazioneResponse>> findAll() {
        return ResponseEntity.ok(ristorante.getPrenotazioni());
    }

    // Ricerca per Nome e Cognome (cerca-nominativo)
    @GetMapping("/cerca")
    public ResponseEntity<List<PrenotazioneResponse>> findByCliente(
            @RequestParam String nome,
            @RequestParam String cognome) {
        List<PrenotazioneResponse> list = ristorante.cercaPrenotazionePerNomeCliente(nome, cognome);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    // cerca prenotazioni per giorno corrente
    @GetMapping("/oggi")
    public ResponseEntity<List<PrenotazioneResponse>> findPrenotazioniOggi() {
        List<PrenotazioneResponse> list = ristorante.getPrenotazioniOggi();
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/cerca-data")
    public ResponseEntity<List<PrenotazioneResponse>> findByData(@RequestParam String data) {
        List<PrenotazioneResponse> list = ristorante.cercaPrenotazionePerData(data);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    // Inserimento Prenotazione
    @PostMapping
    public ResponseEntity<?> aggiungiPrenotazione(@RequestBody PrenotazioneRequest request) {
        try {
            PrenotazioneResponse response = ristorante.aggiungiPrenotazione(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Gestisce l'errore di data passata (lanciato dal service)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Modifica Prenotazione
    @PutMapping("/{id}")
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

    // Cancellazione Prenotazione
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaPrenotazione(@PathVariable Integer id) {
        PrenotazioneResponse esistente = ristorante.cercaPrenotazione(id);
        if (esistente == null) {
            return ResponseEntity.notFound().build();
        }
        ristorante.eliminaPrenotazione(id);
        return ResponseEntity.noContent().build();
    }

}