package com.example.progettofinale.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.progettofinale.models.Notifica;
import com.example.progettofinale.models.NotificaRequest;
import com.example.progettofinale.models.NotificaResponse;

@Component
public class NotificatoreFacade {

    private final Notificatore notificatore;

    public NotificatoreFacade(Notificatore notificatore) {
        this.notificatore = notificatore;
    }

    // Invia una notifica (Observer)
    public NotificaResponse inviaNotifica(NotificaRequest request) {
        Notifica notifica = notificatore.toNotifica(request);
        return notificatore.salvaNotifica(notifica);
    }

    // Ottieni notifiche per utente
    public List<NotificaResponse> getNotifichePerUtente(int idUtente) {
        return notificatore.getNotificazioniPerUtente(idUtente);
    }

    // Ottieni notifiche per prenotazione
    public List<NotificaResponse> getNotifichePerPrenotazione(int idPrenotazione) {
        return notificatore.getNotificazioniPerPrenotazione(idPrenotazione);
    }

    // Ottieni tutte le notifiche
    public List<NotificaResponse> getTutteLeNotifiche() {
        return notificatore.getNotificazioni();
    }

    // Cancella notifiche di un utente
    public void cancellaNotificheUtente(int idUtente) {
        notificatore.cancellaNotifichePerUtente(idUtente);
    }
}
