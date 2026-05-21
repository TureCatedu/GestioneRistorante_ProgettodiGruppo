package com.example.progettofinale.controller;

import com.example.progettofinale.models.PrenotazioneRequest;
import com.example.progettofinale.models.PrenotazioneResponse;
import com.example.progettofinale.repository.UtenteRepo;
import com.example.progettofinale.services.RistoranteFacade;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final RistoranteFacade ristorante;

    public PrenotazioneController(RistoranteFacade ristorante, UtenteRepo utenteRepo) {
        this.ristorante = ristorante;
    }

    // Ricerca per ID (Proprietario o Staff)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String findById(@PathVariable Integer id, Model model, Authentication authentication) {

        // Se sei un cliente, verifichiamo tramite la facade se la prenotazione ti appartiene
        if (ristorante.isCliente(authentication)) {
            if (!ristorante.verificaProprietaPrenotazione(id, authentication.getName())) {
                throw new AccessDeniedException("Non sei autorizzato a visualizzare questa prenotazione.");
            }
        }

        PrenotazioneResponse response = ristorante.cercaPrenotazione(id);
        model.addAttribute("prenotazione", response);
        return "prenotazione";
    }

    // Ottieni tutte le prenotazioni (Solo staff)
    @GetMapping
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')") // Aggiunto CLIENTE
    public String listaPrenotazioni(Model model, Authentication authentication) { // Aggiunto Authentication
        
        List<PrenotazioneResponse> listaDaMostrare;

        if (ristorante.isCliente(authentication)) {
            listaDaMostrare = ristorante.getPrenotazioniPersonali(authentication.getName());
        } else {
            // Se è amministratore o cameriere, le prendiamo tutte
            listaDaMostrare = ristorante.getPrenotazioni();
        }

        model.addAttribute("prenotazioni", listaDaMostrare);
        return "prenotazioni";
    }

    // Ricerca per Nome e Cognome (Solo staff)
    @GetMapping("/cerca")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public String findByCliente(
            @RequestParam String nome,
            @RequestParam String cognome,
            Model model) {

        List<PrenotazioneResponse> list = ristorante.cercaPrenotazionePerNomeCliente(nome, cognome);
        model.addAttribute("prenotazioni", list);
        return "prenotazioni";
    }

    // Cerca prenotazioni per giorno corrente (Solo staff)
    @GetMapping("/oggi")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public String findPrenotazioniOggi(Model model) {
        List<PrenotazioneResponse> list = ristorante.getPrenotazioniOggi();
        model.addAttribute("prenotazioni", list);
        return "prenotazioni";
    }

    // Cerca per data (Solo staff)
    @GetMapping("/cerca-data")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public String findByData(@RequestParam String data, Model model) {
        List<PrenotazioneResponse> list = ristorante.cercaPrenotazionePerData(data);
        model.addAttribute("prenotazioni", list);
        return "prenotazioni";
    }

    // Inserimento Prenotazione (Tutti)
    @PostMapping
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String aggiungiPrenotazione(PrenotazioneRequest request, Authentication authentication) {
        if(ristorante.isCliente(authentication))
        {
            ristorante.aggiungiPrenotazione(request);
            return "redirect:/";
        }
        ristorante.aggiungiPrenotazione(request);
        return "redirect:/prenotazioni";
    }

    // Modifica Prenotazione (Proprietario o Staff)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String modificaPrenotazione(
            @PathVariable Integer id,
            PrenotazioneRequest request,
            Authentication authentication) {

        if (ristorante.isCliente(authentication)) {
            if (!ristorante.verificaProprietaPrenotazione(id, authentication.getName())) {
                throw new AccessDeniedException("Non sei autorizzato a modificare questa prenotazione.");
            }
        }

        ristorante.modificaPrenotazione(id, request);
        return "redirect:/prenotazioni";
    }

    // Cancellazione Prenotazione (Proprietario o Staff)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String eliminaPrenotazione(@PathVariable Integer id, Authentication authentication) {

        // Controllo di sicurezza centralizzato sulla Facade
        if (ristorante.isCliente(authentication)) {
            if (!ristorante.verificaProprietaPrenotazione(id, authentication.getName())) {
                throw new AccessDeniedException("Non sei autorizzato a eliminare questa prenotazione.");
            }
        }

        ristorante.eliminaPrenotazione(id);
        return "redirect:/prenotazioni";
    }
}