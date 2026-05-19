package com.example.progettofinale.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.progettofinale.models.Notifica;

@Repository
public interface NotificatoreRepo extends JpaRepository<Notifica, Integer>{

    //trova notifiche per utente
    List<Notifica> findByUtenteId(int idUtente);
    //trova notifiche per prenotazione
    List<Notifica> findByPrenotazioneId(int idPrenotazione);
    //cancella notifiche per utente
    void deleteByUtenteId(int idUtente);
    
}
