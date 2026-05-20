package com.example.progettofinale.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.progettofinale.models.NotificaResponse;
import com.example.progettofinale.services.NotificatoreFacade;

import java.util.List;

@Controller
@RequestMapping("/notificatore")
public class NotificatoreController {
    
    private final NotificatoreFacade notificatoreFacade;

    public NotificatoreController(NotificatoreFacade notificatoreFacade) {
        this.notificatoreFacade = notificatoreFacade;
    }

    // Ottieni tutte le notifiche (Solo Admin)
    @GetMapping
    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    public String getAllNotifiche(Model model) {

        List<NotificaResponse> notifiche =
                notificatoreFacade.getTutteLeNotifiche();

        model.addAttribute("notifiche", notifiche);

        return "notifiche";
    }

    // Ottieni notifiche per Utente (Tutti i ruoli autenticati)
    @GetMapping("/utente/{idUtente}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String getNotifichePerUtente(
        @PathVariable int idUtente,
        Model model) {

        List<NotificaResponse> notifiche =
                notificatoreFacade.getNotifichePerUtente(idUtente);

        model.addAttribute("notifiche", notifiche);

        return "notifiche";
    }

    // Ottieni notifiche per Prenotazione (Admin e Camerieri)
    @GetMapping("/prenotazione/{idPrenotazione}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public String getNotifichePerPrenotazione(
            @PathVariable int idPrenotazione,
            Model model) {

        List<NotificaResponse> notifiche =
                notificatoreFacade.getNotifichePerPrenotazione(idPrenotazione);

        model.addAttribute("notifiche", notifiche);

        return "notifiche";
    }

    // Cancella notifiche per Utente (Tutti i ruoli)
    @DeleteMapping("/utente/{idUtente}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String deleteNotifichePerUtente(@PathVariable int idUtente) {

        notificatoreFacade.cancellaNotificheUtente(idUtente);

        return "redirect:/notifiche";
    }
}