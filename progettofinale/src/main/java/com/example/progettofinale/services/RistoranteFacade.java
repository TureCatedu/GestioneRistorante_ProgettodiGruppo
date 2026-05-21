package com.example.progettofinale.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.progettofinale.models.Prenotazione;
import com.example.progettofinale.models.PrenotazioneRequest;
import com.example.progettofinale.models.PrenotazioneResponse;

//facade per service ristorante
@Component
public class RistoranteFacade {
    private Ristorante ristorante;
    
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

    // ottieni le prenotazioni di un cliente specifico
    public List<PrenotazioneResponse> getPrenotazioniPersonali(String email) {
        return ristorante.getPrenotazioniPersonali(email);
    }

    //verifica se la prenotazione appartiene al cliente
    public boolean verificaProprietaPrenotazione(Integer idPrenotazione, String emailCliente) {
        return ristorante.verificaProprietaPrenotazione(idPrenotazione, emailCliente);
    }

    // verifica se l'utente è un cliente
    public boolean isCliente(Authentication authentication) {
        return ristorante.isCliente(authentication);
    }


}
