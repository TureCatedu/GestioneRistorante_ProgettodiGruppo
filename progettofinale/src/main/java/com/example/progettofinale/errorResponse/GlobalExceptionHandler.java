package com.example.progettofinale.errorResponse;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//gestore di errori globali
@ControllerAdvice
public class GlobalExceptionHandler {

    // GESTIONE ERRORE ACCESSO NEGATO
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        ErrorResponse errorResponse = new ErrorResponse(403, "Accesso negato: " + e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(403).body(errorResponse);
    }
    //gestione errori generici
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error: " + e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }
    //gestione errore prenotazione non trovata
    @ExceptionHandler(value = PrenotazioneNonTrovataException.class)
    public ResponseEntity<ErrorResponse> handlePrenotazioneNonTrovataException(PrenotazioneNonTrovataException e) {
        ErrorResponse errorResponse = new ErrorResponse(404, "Prenotazione non trovata: " + e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }
    //gestione errore utente non trovato
    @ExceptionHandler(value = UtenteNonTrovatoException.class)
    public ResponseEntity<ErrorResponse> handleUtenteNonTrovatoException(UtenteNonTrovatoException e) {
        ErrorResponse errorResponse = new ErrorResponse(404, "Utente non trovato: " + e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }
    
}
