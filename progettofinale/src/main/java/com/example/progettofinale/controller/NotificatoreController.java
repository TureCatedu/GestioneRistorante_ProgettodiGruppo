package com.example.progettofinale.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.progettofinale.models.NotificaResponse;
import com.example.progettofinale.models.Utente;
import com.example.progettofinale.repository.UtenteRepo;
import com.example.progettofinale.services.NotificatoreFacade;

import java.util.List;

@Controller
@RequestMapping("/notificatore")
public class NotificatoreController {
    
    private final NotificatoreFacade notificatoreFacade;
    private final UtenteRepo utenteRepo;

    public NotificatoreController(NotificatoreFacade notificatoreFacade, UtenteRepo utenteRepo) {
        this.notificatoreFacade = notificatoreFacade;
        this.utenteRepo = utenteRepo;
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
    @GetMapping("/utente/me")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String getNotifichePerUtente(
        Authentication authentication,
        Model model) {
        Utente utente = utenteRepo.findByEmail(authentication.getName()).orElse(null);
        Integer idUtente = utente.getId();
        List<NotificaResponse> notifiche =
                notificatoreFacade.getNotifichePerUtente(idUtente);

        model.addAttribute("notifiche", notifiche);

        return "notifiche";
    }
    //cancella notifica per id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String cancellaNotificaSingola(@PathVariable int id) {
        
        // Chiama il facade per rimuovere la notifica dal database
        notificatoreFacade.cancellaNotifica(id);
        
        // Effettua un redirect per ricaricare la pagina delle notifiche aggiornata
        return "redirect:/notificatore"; 
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
    //cancella notifiche per utente
    @DeleteMapping("/utente/me")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String deleteNotifichePerUtente(Authentication authentication) {
        String email = authentication.getName();
        Utente utente = utenteRepo.findByEmail(email).orElse(null);
        int idUtente = utente.getId();
        notificatoreFacade.cancellaNotificheUtente(idUtente);
        return "redirect:/notificatore/utente/me";
    }
}