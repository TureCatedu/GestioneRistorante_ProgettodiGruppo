package com.example.progettofinale.errorResponse;

// errore custom per prenotazione non trovata
public class PrenotazioneNonTrovataException extends RuntimeException {
    public PrenotazioneNonTrovataException(int id) {
        super("Prenotazione con id " + id + " non trovata");
    }
    
}
