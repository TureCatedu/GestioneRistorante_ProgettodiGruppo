package com.example.progettofinale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.progettofinale.models.Prenotazione;
import java.util.List;
import java.time.LocalDateTime;


@Repository
public interface PrenotazioneRepo extends JpaRepository<Prenotazione,Integer>{
    
    List<Prenotazione> findByNomeCliente(String nomeCliente);

    List<Prenotazione> findByNumeroPersone(int numeroPersone);

    List<Prenotazione> findByDataOra(LocalDateTime dataOra);
}
