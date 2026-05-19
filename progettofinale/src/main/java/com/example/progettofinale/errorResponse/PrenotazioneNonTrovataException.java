package com.example.progettofinale.errorResponse;

public class PrenotazioneNonTrovataException extends RuntimeException {
    public PrenotazioneNonTrovataException(int id) {
        super("Prenotazione con id " + id + " non trovata");
    }
    
}
