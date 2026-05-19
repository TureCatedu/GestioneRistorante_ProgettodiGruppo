package com.example.progettofinale.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.progettofinale.models.Notifica;
import com.example.progettofinale.models.NotificaResponse;
import com.example.progettofinale.models.NotificaRequest;
import com.example.progettofinale.repository.PrenotazioneRepo;

@Service
public class Ristorante implements Subject {
    private final  PrenotazioneRepo prenotazioneRepo;
    //costruttore con parametri per l'injection
    public Ristorante(PrenotazioneRepo prenotazioneRepo) {
        this.prenotazioneRepo = prenotazioneRepo;
    }
    List<Observer> observers = new ArrayList<>();
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    @Override
    public void notifyObservers(Notifica notifica) {
        for (Observer o : observers) {
            o.update(notifica);
        }
    }
    //Notifica to notificaResponse
    public NotificaResponse notifica(Notifica notifica) {
        return new NotificaResponse(notifica.getId(), notifica.getPrenotazione(), notifica.getDescrizione());
    }

    //notificaRequest to notifica
    public Notifica notifica(NotificaRequest notificaRequest) {
        return new Notifica(notificaRequest.prenotazione().getId(), notificaRequest.prenotazione());
    }


    
}
