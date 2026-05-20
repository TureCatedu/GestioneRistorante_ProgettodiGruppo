package com.example.progettofinale.controller;

import com.example.progettofinale.models.PrenotazioneRequest;
import com.example.progettofinale.models.PrenotazioneResponse;
import com.example.progettofinale.services.RistoranteFacade;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final RistoranteFacade ristorante;

    public PrenotazioneController(RistoranteFacade ristorante) {
        this.ristorante = ristorante;
    }

    // Ricerca per ID (Accessibile a tutti)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String findById(@PathVariable Integer id, Model model) {

        PrenotazioneResponse response = ristorante.cercaPrenotazione(id);

        model.addAttribute("prenotazione", response);

        return "prenotazione";
    }

    // Ottieni tutte le prenotazioni (Solo staff)
    @GetMapping
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public String listaPrenotazioni(Model model) {

        model.addAttribute(
                "prenotazioni",
                ristorante.getPrenotazioni()
        );

        return "prenotazioni";
    }

    // Ricerca per Nome e Cognome (Solo staff)
    @GetMapping("/cerca")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public String findByCliente(
            @RequestParam String nome,
            @RequestParam String cognome,
            Model model) {

        List<PrenotazioneResponse> list =
                ristorante.cercaPrenotazionePerNomeCliente(nome, cognome);

        model.addAttribute("prenotazioni", list);

        return "prenotazioni";
    }
    // cerca prenotazioni per giorno corrente (Solo staff)
    @GetMapping("/oggi")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public String findPrenotazioniOggi(Model model) {

        List<PrenotazioneResponse> list =
                ristorante.getPrenotazioniOggi();

        model.addAttribute("prenotazioni", list);

        return "prenotazioni";
    }
    // cerca per data (Solo staff)
    @GetMapping("/cerca-data")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public String findByData(@RequestParam String data, Model model) {

        List<PrenotazioneResponse> list =
                ristorante.cercaPrenotazionePerData(data);

        model.addAttribute("prenotazioni", list);

        return "prenotazioni";
    }
    // Inserimento Prenotazione (Tutti)
    @PostMapping
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String aggiungiPrenotazione(PrenotazioneRequest request) {

        ristorante.aggiungiPrenotazione(request);

        return "redirect:/prenotazioni";
    }


    // Modifica Prenotazione (Tutti)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String modificaPrenotazione(
            @PathVariable Integer id,
            PrenotazioneRequest request) {

        ristorante.modificaPrenotazione(id, request);

        return "redirect:/prenotazioni";
    }

    // Cancellazione Prenotazione (Tutti)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String eliminaPrenotazione(@PathVariable Integer id) {

        ristorante.eliminaPrenotazione(id);

        return "redirect:/prenotazioni";
    }
}