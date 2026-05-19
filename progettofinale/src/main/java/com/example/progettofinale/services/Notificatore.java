package com.example.progettofinale.services;

import java.util.ArrayList;
import java.util.List;

import com.example.progettofinale.models.Notifica;
import com.example.progettofinale.models.NotificaRequest;
import com.example.progettofinale.models.NotificaResponse;
import com.example.progettofinale.models.Ruolo;
import com.example.progettofinale.models.Utente;
import com.example.progettofinale.repository.NotificatoreRepo;
import com.example.progettofinale.repository.UtenteRepo;

public class Notificatore implements Observer {
    private final NotificatoreRepo notificatoreRepo;
    UtenteRepo utenteRepo;
    //costruttore con parametri per l'injection
    public Notificatore(NotificatoreRepo notificatoreRepo, UtenteRepo utenteRepo) {
        this.notificatoreRepo = notificatoreRepo;
        this.utenteRepo = utenteRepo;
    }
    @Override
    public void update(Notifica notifica) {
        List<Utente> utenti = utenteRepo.findByRuolo(Ruolo.AMMINISTRATORE);
        utenti.addAll(utenteRepo.findByRuolo(Ruolo.CAMERIERE));
        Notifica notificaDb = new Notifica(notifica.getPrenotazione(), notifica.getDescrizione());
        
        for (Utente utente : utenti) {
            notificaDb.setUtente(utenteRepo.findById(utente.getId()).orElse(null));
            notificatoreRepo.save(notificaDb);
        }
    }
    //da notifica a notificaResponse
    NotificaResponse notifica(Notifica notifica) {
        return new NotificaResponse(notifica.getId(), notifica.getPrenotazione(), notifica.getDescrizione());
    }
    //da notificaRequest a notifica
    Notifica notifica(NotificaRequest notificaRequest) {
        return new Notifica(notificaRequest.prenotazione(), notificaRequest.descrizione());
    }

    //ottieni notifiche per utente
    public List<NotificaResponse> getNotificazioniPerUtente(int idUtente) {
        Utente utente = utenteRepo.findById(idUtente).orElse(null);
        if (utente == null) {
            return null;
        }
        List<Notifica> notifiche = notificatoreRepo.findByUtenteId(idUtente);
        List<NotificaResponse> notificheResponse = new ArrayList<>();
        for (Notifica notifica : notifiche) {
            notificheResponse.add(notifica(notifica));
        }
        return notificheResponse;
    }
    //Ottieni tutte le notifiche per un prenotazione
    public List<NotificaResponse> getNotificazioniPerPrenotazione(int idPrenotazione) {
        List<Notifica> notifiche = notificatoreRepo.findByPrenotazioneId(idPrenotazione);
        List<NotificaResponse> notificheResponse = new ArrayList<>();
        for (Notifica notifica : notifiche) {
            notificheResponse.add(notifica(notifica));
        }
        return notificheResponse;
    }
    //Ottieni tutte le notifiche
    public List<NotificaResponse> getNotificazioni() {
        List<Notifica> notifiche = notificatoreRepo.findAll();
        List<NotificaResponse> notificheResponse = new ArrayList<>();
        for (Notifica notifica : notifiche) {
            notificheResponse.add(notifica(notifica));
        }
        return notificheResponse;
    }
    //cancella notiche per utente
    public void cancellaNotifichePerUtente(int idUtente) {
        Utente utente = utenteRepo.findById(idUtente).orElse(null);
        if (utente == null) {
            return;
        }
        notificatoreRepo.deleteByUtenteId(idUtente);
    }
    
    //salva notifica
    NotificaResponse salvaNotifica(Notifica notifica) {
        Notifica notificaDb = notificatoreRepo.save(notifica);
        return notifica(notificaDb);
    }
}
