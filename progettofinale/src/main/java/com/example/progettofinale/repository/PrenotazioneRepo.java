package com.example.progettofinale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.progettofinale.models.Prenotazione;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Repository
public interface PrenotazioneRepo extends JpaRepository<Prenotazione,Integer>{
    
    List<Prenotazione> findByCliente_NomeAndCliente_Cognome(String nome, String cognome);

    List<Prenotazione> findByNumeroPersone(int numeroPersone);

    List<Prenotazione> findByDataOra(LocalDateTime dataOra);

    @Query("SELECT p FROM Prenotazione p WHERE FUNCTION('DATE', p.dataOra) = :data")
    List<Prenotazione> findByDataOnly(@Param("data") LocalDate data);

    
}
