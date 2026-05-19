package com.example.progettofinale.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.progettofinale.models.NotificaResponse;
import com.example.progettofinale.services.Notificatore;
import java.util.List;

@RestController
@RequestMapping("/api/notificatore")
public class NotificatoreController {
    
    private final Notificatore notificatore;

    public NotificatoreController(Notificatore notificatore) {
        this.notificatore = notificatore;
    }

    // Ottieni tutte le notifiche
    @GetMapping
    public ResponseEntity<List<NotificaResponse>> getAllNotifiche() {
        return ResponseEntity.ok(notificatore.getNotificazioni());
    }

    // Ottieni notifiche per Utente
    @GetMapping("/utente/{idUtente}")
    public ResponseEntity<List<NotificaResponse>> getNotifichePerUtente(@PathVariable int idUtente) {
        List<NotificaResponse> notifiche = notificatore.getNotificazioniPerUtente(idUtente);
        return notifiche != null ? ResponseEntity.ok(notifiche) : ResponseEntity.notFound().build();
    }

    // Ottieni notifiche per Prenotazione
    @GetMapping("/prenotazione/{idPrenotazione}")
    public ResponseEntity<List<NotificaResponse>> getNotifichePerPrenotazione(@PathVariable int idPrenotazione) {
        return ResponseEntity.ok(notificatore.getNotificazioniPerPrenotazione(idPrenotazione));
    }

    // Cancella notifiche per Utente
    @DeleteMapping("/utente/{idUtente}")
    public ResponseEntity<Void> deleteNotifichePerUtente(@PathVariable int idUtente) {
        notificatore.cancellaNotifichePerUtente(idUtente);
        return ResponseEntity.noContent().build();
    }
}