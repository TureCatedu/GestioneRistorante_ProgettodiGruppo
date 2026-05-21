package com.example.progettofinale.errorResponse;

import java.time.LocalDateTime;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//gestore di errori globali
@ControllerAdvice
public class GlobalExceptionHandler {

    // GESTIONE ERRORE ACCESSO NEGATO
    @ExceptionHandler(value = AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException e, Model model) {
        model.addAttribute("status", 403);
        model.addAttribute("error", "Accesso Negato");
        model.addAttribute("message", "Non sei autorizzato a compiere questa azione: " + e.getMessage());
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error";
    }

    // gestione errore prenotazione non trovata
    @ExceptionHandler(value = PrenotazioneNonTrovataException.class)
    public String handlePrenotazioneNonTrovataException(PrenotazioneNonTrovataException e, Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("error", "Risorsa Non Trovata");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error";
    }

    // Gestione errore notifica non trovata
    @ExceptionHandler(value = NotificaNonTrovataException.class)
    public String handleNotificaNonTrovataException(NotificaNonTrovataException e, Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("error", e.getMessage()); // Mappa il messaggio su th:text="${error}" di error.html
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error"; 
    }
    
    // gestione errore utente non trovato
    @ExceptionHandler(value = UtenteNonTrovatoException.class)
    public String handleUtenteNonTrovatoException(UtenteNonTrovatoException e, Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("error", "Utente Non Trovato");
        model.addAttribute("message", e.getMessage());
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error";
    }

    // gestione errori generici
    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("status", 500);
        model.addAttribute("error", "Internal Server Error");
        model.addAttribute("message", "Si è verificato un errore imprevisto: " + e.getMessage());
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error";
    }
    
}
