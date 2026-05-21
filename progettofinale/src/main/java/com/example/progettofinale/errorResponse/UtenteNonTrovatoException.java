package com.example.progettofinale.errorResponse;

// errore custom per utente non trovato
public class UtenteNonTrovatoException extends RuntimeException {
    public UtenteNonTrovatoException(int id) {
        super("Utente con id " + id + " non trovato");
    }
    public UtenteNonTrovatoException(String email) {
        super("Utente con email " + email + " non trovato");
    }
    
}
