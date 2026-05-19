package com.example.progettofinale.errorResponse;

public class UtenteNonTrovatoException extends RuntimeException {
    public UtenteNonTrovatoException(int id) {
        super("Utente con id " + id + " non trovato");
    }
    
}
