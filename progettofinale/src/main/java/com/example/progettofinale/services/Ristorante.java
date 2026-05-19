package com.example.progettofinale.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.progettofinale.errorResponse.PrenotazioneNonTrovataException;
import com.example.progettofinale.errorResponse.UtenteNonTrovatoException;
import com.example.progettofinale.models.Notifica;
import com.example.progettofinale.models.Prenotazione;
import com.example.progettofinale.models.PrenotazioneRequest;
import com.example.progettofinale.models.PrenotazioneResponse;
import com.example.progettofinale.models.Utente;
import com.example.progettofinale.repository.PrenotazioneRepo;
import com.example.progettofinale.repository.UtenteRepo;

//service per gestione prenotazioni
@Service
public class Ristorante implements Subject {
    //attributi per injection
    private final PrenotazioneRepo prenotazioneRepo;
    private final UtenteRepo utenteRepo;
    // Notificatore
    Notificatore notificatore;
    //observers
    List<Observer> observers = new ArrayList<>();
    // costruttore con parametri per l'injection
    public Ristorante(PrenotazioneRepo prenotazioneRepo, Notificatore notificatore, UtenteRepo utenteRepo) {
        this.prenotazioneRepo = prenotazioneRepo;
        this.notificatore = notificatore;
        this.utenteRepo = utenteRepo;
    }
    //metodo per registrare un observer
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    //metodo per rimuovere un observer
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    //metodo per notificare un observer
    @Override
    public void notifyObservers(Notifica notifica) {
        for (Observer o : observers) {
            o.update(notifica);
        }
    }

    // PrenotazioneRequest to Prenotazione
    Prenotazione toPrenotazione(PrenotazioneRequest prenotazioneRequest) {
        Utente utente = utenteRepo.findById(prenotazioneRequest.utenteId()).orElseThrow(() -> new UtenteNonTrovatoException(prenotazioneRequest.utenteId()));
        return new Prenotazione(utente, prenotazioneRequest.numeroPersone(), prenotazioneRequest.dataOra());
    }

    // Prenotazione to NotificaResponse
    PrenotazioneResponse toPrenotazioneResponse(Prenotazione prenotazione) {
        return new PrenotazioneResponse(prenotazione.getId(), prenotazione.getCliente().getId(),prenotazione.getNumeroPersone(), prenotazione.getDataOra());
    }

    // aggiungi prenotazione
    public PrenotazioneResponse aggiungiPrenotazione(PrenotazioneRequest prenotazioneRequest) {
        Prenotazione prenotazione = toPrenotazione(prenotazioneRequest);
        Prenotazione prenotazioneDb = prenotazioneRepo.save(prenotazione);
        notifyObservers(new Notifica(prenotazioneDb, "Prenotazione inserita"));
        return toPrenotazioneResponse(prenotazioneDb);
    }

    // ottieni tutte le prenotazioni
    public List<PrenotazioneResponse> getPrenotazioni() {
        List<Prenotazione> prenotazioni = prenotazioneRepo.findAll();
        List<PrenotazioneResponse> prenotazioniResponse = new ArrayList<>();
        for (Prenotazione prenotazione : prenotazioni) {
            prenotazioniResponse.add(toPrenotazioneResponse(prenotazione));
        }
        return prenotazioniResponse;
    }

    // cerca prenotazione per id
    public PrenotazioneResponse cercaPrenotazione(Integer id) {
        Prenotazione prenotazione = prenotazioneRepo.findById(id).orElseThrow(() -> new PrenotazioneNonTrovataException(id));
        return toPrenotazioneResponse(prenotazione);
    }

    // cerca prenotazione per data
    public List<PrenotazioneResponse> cercaPrenotazionePerData(String dataString) {

        LocalDate data = LocalDate.parse(dataString);
        
        List<Prenotazione> prenotazioni = prenotazioneRepo.findByDataOnly(data);
        List<PrenotazioneResponse> response = new ArrayList<>();
        
        for (Prenotazione p : prenotazioni) {
            response.add(toPrenotazioneResponse(p));
        }
        return response;
    }
    
    //cerca prenotazione per giorno corrente
    public List<PrenotazioneResponse> getPrenotazioniOggi() {
    
        LocalDate oggi = LocalDate.now();

        List<Prenotazione> prenotazioni = prenotazioneRepo.findByDataOnly(oggi);
        List<PrenotazioneResponse> response = new ArrayList<>();

        for (Prenotazione p : prenotazioni) {
            response.add(toPrenotazioneResponse(p));
        }
        return response;
    }

    // elimina prenotazione per id
    public void eliminaPrenotazione(Integer id) {
        Prenotazione prenotazione = prenotazioneRepo.findById(id).orElseThrow(() -> new PrenotazioneNonTrovataException(id));
        prenotazioneRepo.delete(prenotazione);
        notifyObservers(new Notifica(prenotazione, "Prenotazione eliminata"));
    }

    // modifica prenotazione per id
    public PrenotazioneResponse modificaPrenotazione(Integer id, PrenotazioneRequest prenotazioneRequest) {
        Optional<Prenotazione> prenotazione = prenotazioneRepo.findById(id);
        if (prenotazione.isEmpty()) {
            throw new PrenotazioneNonTrovataException(id);
        }
        Utente utente = utenteRepo.findById(prenotazioneRequest.utenteId()).orElseThrow(() -> new UtenteNonTrovatoException(prenotazioneRequest.utenteId()));
        Prenotazione prenotazioneDb = prenotazione.get();
        prenotazioneDb.setCliente(utente);
        prenotazioneDb.setNumeroPersone(prenotazioneRequest.numeroPersone());
        prenotazioneDb.setDataOra(prenotazioneRequest.dataOra());
        Prenotazione updated = prenotazioneRepo.save(prenotazioneDb);
        notifyObservers(new Notifica(updated, "Prenotazione modificata"));
        return toPrenotazioneResponse(updated);
    }

    // cerca prenotazione per nome e cognome cliente
    public List<PrenotazioneResponse> cercaPrenotazionePerNomeCliente(String nome, String cognome) {
        List<Prenotazione> prenotazioni = prenotazioneRepo.findByCliente_NomeAndCliente_Cognome(nome, cognome);
        List<PrenotazioneResponse> prenotazioniResponse = new ArrayList<>();
        for (Prenotazione prenotazione : prenotazioni) {
            prenotazioniResponse.add(toPrenotazioneResponse(prenotazione));
        }
        return prenotazioniResponse;
    }

}
