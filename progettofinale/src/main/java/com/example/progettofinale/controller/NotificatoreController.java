package com.example.progettofinale.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // Ottieni tutte le notifiche (Solo Admin)
    @GetMapping
    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    public ResponseEntity<List<NotificaResponse>> getAllNotifiche() {
        return ResponseEntity.ok(notificatore.getNotificazioni());
    }

    // Ottieni notifiche per Utente (Tutti i ruoli autenticati)
    @GetMapping("/utente/{idUtente}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public ResponseEntity<List<NotificaResponse>> getNotifichePerUtente(@PathVariable int idUtente) {
        List<NotificaResponse> notifiche = notificatore.getNotificazioniPerUtente(idUtente);
        return notifiche != null ? ResponseEntity.ok(notifiche) : ResponseEntity.notFound().build();
    }

    // Ottieni notifiche per Prenotazione (Admin e Camerieri)
    @GetMapping("/prenotazione/{idPrenotazione}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public ResponseEntity<List<NotificaResponse>> getNotifichePerPrenotazione(@PathVariable int idPrenotazione) {
        return ResponseEntity.ok(notificatore.getNotificazioniPerPrenotazione(idPrenotazione));
    }

    // Cancella notifiche per Utente (Tutti i ruoli)
    @DeleteMapping("/utente/{idUtente}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public ResponseEntity<Void> deleteNotifichePerUtente(@PathVariable int idUtente) {
        notificatore.cancellaNotifichePerUtente(idUtente);
        return ResponseEntity.noContent().build();
    }
}