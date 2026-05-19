package com.example.progettofinale.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.progettofinale.errorResponse.PrenotazioneNonTrovataException;
import com.example.progettofinale.errorResponse.UtenteNonTrovatoException;
import com.example.progettofinale.models.Notifica;
import com.example.progettofinale.models.NotificaRequest;
import com.example.progettofinale.models.NotificaResponse;
import com.example.progettofinale.models.Prenotazione;
import com.example.progettofinale.models.Ruolo;
import com.example.progettofinale.models.Utente;
import com.example.progettofinale.repository.NotificatoreRepo;
import com.example.progettofinale.repository.PrenotazioneRepo;
import com.example.progettofinale.repository.UtenteRepo;

@Service
public class Notificatore implements Observer {
    private final NotificatoreRepo notificatoreRepo;
    private final PrenotazioneRepo prenotazioneRepo;
    UtenteRepo utenteRepo;
    //costruttore con parametri per l'injection
    public Notificatore(NotificatoreRepo notificatoreRepo, PrenotazioneRepo prenotazioneRepo, UtenteRepo utenteRepo) {
        this.notificatoreRepo = notificatoreRepo;
        this.utenteRepo = utenteRepo;
        this.prenotazioneRepo = prenotazioneRepo;
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
    NotificaResponse toNotificaResponse(Notifica notifica) {
        return new NotificaResponse(notifica.getId(), notifica.getPrenotazione().getId(), notifica.getDescrizione());
    }
    //da notificaRequest a notifica
    Notifica toNotifica(NotificaRequest notificaRequest) {
        Prenotazione prenotazione = prenotazioneRepo.findById(notificaRequest.prenotazioneId()).orElseThrow(() -> new PrenotazioneNonTrovataException(notificaRequest.prenotazioneId()));
        return new Notifica(prenotazione, notificaRequest.descrizione());
    }

    //ottieni notifiche per utente
    public List<NotificaResponse> getNotificazioniPerUtente(int idUtente) {
        utenteRepo.findById(idUtente).orElseThrow(() -> new UtenteNonTrovatoException(idUtente));
        List<Notifica> notifiche = notificatoreRepo.findByUtenteId(idUtente);
        List<NotificaResponse> notificheResponse = new ArrayList<>();
        for (Notifica notifica : notifiche) {
            notificheResponse.add(toNotificaResponse(notifica));
        }
        return notificheResponse;
    }
    //Ottieni tutte le notifiche per un prenotazione
    public List<NotificaResponse> getNotificazioniPerPrenotazione(int idPrenotazione) {
        prenotazioneRepo.findById(idPrenotazione).orElseThrow(() -> new PrenotazioneNonTrovataException(idPrenotazione));
       
        List<Notifica> notifiche = notificatoreRepo.findByPrenotazioneId(idPrenotazione);
        List<NotificaResponse> notificheResponse = new ArrayList<>();
        for (Notifica notifica : notifiche) {
            notificheResponse.add(toNotificaResponse(notifica));
        }
        return notificheResponse;
    }
    //Ottieni tutte le notifiche
    public List<NotificaResponse> getNotificazioni() {
        List<Notifica> notifiche = notificatoreRepo.findAll();
        List<NotificaResponse> notificheResponse = new ArrayList<>();
        for (Notifica notifica : notifiche) {
            notificheResponse.add(toNotificaResponse(notifica));
        }
        return notificheResponse;
    }
    //cancella notiche per utente
    public void cancellaNotifichePerUtente(int idUtente) {
        utenteRepo.findById(idUtente).orElseThrow(() -> new UtenteNonTrovatoException(idUtente));
        notificatoreRepo.deleteByUtenteId(idUtente);
    }
    
    //salva notifica
    NotificaResponse salvaNotifica(Notifica notifica) {
        Notifica notificaDb = notificatoreRepo.save(notifica);
        return toNotificaResponse(notificaDb);
    }
}
