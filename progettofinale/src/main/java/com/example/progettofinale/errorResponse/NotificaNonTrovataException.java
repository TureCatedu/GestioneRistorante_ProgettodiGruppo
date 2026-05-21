package com.example.progettofinale.errorResponse;

// Errore custom per notifica non trovata
public class NotificaNonTrovataException extends RuntimeException {
    public NotificaNonTrovataException(int id) {
        super("Notifica con id " + id + " non trovata");
    }
}