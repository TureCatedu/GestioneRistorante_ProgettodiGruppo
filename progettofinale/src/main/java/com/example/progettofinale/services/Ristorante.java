package com.example.progettofinale.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.progettofinale.errorResponse.PrenotazioneNonTrovataException;
import com.example.progettofinale.models.Notifica;
import com.example.progettofinale.models.Prenotazione;
import com.example.progettofinale.models.PrenotazioneRequest;
import com.example.progettofinale.models.PrenotazioneResponse;
import com.example.progettofinale.repository.PrenotazioneRepo;

@Service
public class Ristorante implements Subject {
    private final PrenotazioneRepo prenotazioneRepo;
    Notificatore notificatore;

    // costruttore con parametri per l'injection
    public Ristorante(PrenotazioneRepo prenotazioneRepo, Notificatore notificatore) {
        this.prenotazioneRepo = prenotazioneRepo;
        this.notificatore = notificatore;
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

    // PrenotazioneRequest to Prenotazione
    Prenotazione prenotazione(PrenotazioneRequest prenotazioneRequest) {
        return new Prenotazione(prenotazioneRequest.nomeCliente(), prenotazioneRequest.numeroPersone(),
                prenotazioneRequest.dataOra());
    }

    // Prenotazione to NotificaResponse
    PrenotazioneResponse prenotazione(Prenotazione prenotazione) {
        return new PrenotazioneResponse(prenotazione.getId(), prenotazione.getNomeCliente(),
                prenotazione.getNumeroPersone(), prenotazione.getDataOra());
    }

    // aggiungi prenotazione
    public PrenotazioneResponse aggiungiPrenotazione(PrenotazioneRequest prenotazioneRequest) {
        Prenotazione prenotazione = prenotazione(prenotazioneRequest);
        Prenotazione prenotazioneDb = prenotazioneRepo.save(prenotazione);
        notifyObservers(new Notifica(prenotazioneDb, "Prenotazione inserita"));
        return prenotazione(prenotazioneDb);
    }

    // ottieni tutte prenotazioni
    public List<PrenotazioneResponse> getPrenotazioni() {
        List<Prenotazione> prenotazioni = prenotazioneRepo.findAll();
        List<PrenotazioneResponse> prenotazioniResponse = new ArrayList<>();
        for (Prenotazione prenotazione : prenotazioni) {
            prenotazioniResponse.add(prenotazione(prenotazione));
        }
        return prenotazioniResponse;
    }

    // cerca prenotazione
    public PrenotazioneResponse cercaPrenotazione(Integer id) {
        Prenotazione prenotazione = prenotazioneRepo.findById(id).orElseThrow(() -> new PrenotazioneNonTrovataException(id));
        return prenotazione(prenotazione);
    }

    // cerca prenotazione per data
    public List<PrenotazioneResponse> cercaPrenotazionePerData(String dataString) {

        LocalDate data = LocalDate.parse(dataString);
        
        List<Prenotazione> prenotazioni = prenotazioneRepo.findByDataOnly(data);
        List<PrenotazioneResponse> response = new ArrayList<>();
        
        for (Prenotazione p : prenotazioni) {
            response.add(prenotazione(p));
        }
        return response;
    }
    
    //cerca prenotazione per giorno corrente
    public List<PrenotazioneResponse> getPrenotazioniOggi() {
    
        LocalDate oggi = LocalDate.now();

        List<Prenotazione> prenotazioni = prenotazioneRepo.findByDataOnly(oggi);
        List<PrenotazioneResponse> response = new ArrayList<>();

        for (Prenotazione p : prenotazioni) {
            response.add(prenotazione(p));
        }
        return response;
    }

    // elimina prenotazione
    public void eliminaPrenotazione(Integer id) {
        Prenotazione prenotazione = prenotazioneRepo.findById(id).orElseThrow(() -> new PrenotazioneNonTrovataException(id));
        prenotazioneRepo.delete(prenotazione);
        notifyObservers(new Notifica(prenotazione, "Prenotazione eliminata"));
    }

    // modifica prenotazione
    public PrenotazioneResponse modificaPrenotazione(Integer id, PrenotazioneRequest prenotazioneRequest) {
        Optional<Prenotazione> prenotazione = prenotazioneRepo.findById(id);
        if (prenotazione.isEmpty()) {
            throw new PrenotazioneNonTrovataException(id);
        }
        Prenotazione prenotazioneDb = prenotazione.get();
        prenotazioneDb.setNomeCliente(prenotazioneRequest.nomeCliente());
        prenotazioneDb.setNumeroPersone(prenotazioneRequest.numeroPersone());
        prenotazioneDb.setDataOra(prenotazioneRequest.dataOra());
        Prenotazione updated = prenotazioneRepo.save(prenotazioneDb);
        notifyObservers(new Notifica(updated, "Prenotazione modificata"));
        return prenotazione(updated);
    }

    // cerca per nome e cognome cliente
    public List<PrenotazioneResponse> cercaPrenotazionePerNomeCliente(String nome, String cognome) {
        List<Prenotazione> prenotazioni = prenotazioneRepo.findByCliente_NomeAndCliente_Cognome(nome, cognome);
        List<PrenotazioneResponse> prenotazioniResponse = new ArrayList<>();
        for (Prenotazione prenotazione : prenotazioni) {
            prenotazioniResponse.add(prenotazione(prenotazione));
        }
        return prenotazioniResponse;
    }

}
