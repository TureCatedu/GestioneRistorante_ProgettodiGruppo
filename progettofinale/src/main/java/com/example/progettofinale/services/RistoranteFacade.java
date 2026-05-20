package com.example.progettofinale.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.progettofinale.models.PrenotazioneRequest;
import com.example.progettofinale.models.PrenotazioneResponse;

//facade per service ristorante
@Component
public class RistoranteFacade {
    private Ristorante ristorante;
    //costruttore con parametri per l'injection
    public RistoranteFacade(Ristorante ristorante) {
        this.ristorante = ristorante;
    }

    //aggiungi prenotazione
    public PrenotazioneResponse aggiungiPrenotazione(PrenotazioneRequest prenotazioneRequest) {
        return ristorante.aggiungiPrenotazione(prenotazioneRequest);
    }

    // ottieni tutte le prenotazioni
    public List<PrenotazioneResponse> getPrenotazioni() {
        return ristorante.getPrenotazioni();
    }
    // cerca prenotazione per id
    public PrenotazioneResponse cercaPrenotazione(Integer id) {
        return ristorante.cercaPrenotazione(id);
    }

    // cerca prenotazione per data
    public List<PrenotazioneResponse> cercaPrenotazionePerData(String dataString) {
        return ristorante.cercaPrenotazionePerData(dataString);
    }

    // cerca prenotazione per giorno corrente
    public List<PrenotazioneResponse> getPrenotazioniOggi() {
        return ristorante.getPrenotazioniOggi();
    }

    // elimina prenotazione per id
    public void eliminaPrenotazione(Integer id) {
        ristorante.eliminaPrenotazione(id);
    }

    // modifica prenotazione per id
    public PrenotazioneResponse modificaPrenotazione(Integer id, PrenotazioneRequest prenotazioneRequest) {
        return ristorante.modificaPrenotazione(id, prenotazioneRequest);
    }

    // cerca prenotazione per nome e cognome cliente
    public List<PrenotazioneResponse> cercaPrenotazionePerNomeCliente(String nome, String cognome) {
        return ristorante.cercaPrenotazionePerNomeCliente(nome, cognome);
    }
}
