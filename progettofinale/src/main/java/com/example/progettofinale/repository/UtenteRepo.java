package com.example.progettofinale.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.progettofinale.models.Ruolo;
import com.example.progettofinale.models.Utente;

public interface UtenteRepo extends JpaRepository<Utente, Integer>
{
    // Ricerca per email (utile per il login e per garantire l'unicità)
    Optional<Utente> findByEmail(String email);

    // Per il Login: ricerca tramite email (essendo univoca)
    Optional<Utente> findByEmailPassword(String email, String password);

    // Per admin/proprietario: lista di tutti gli utenti di un certo tipo
    List<Utente> findByRuolo(Ruolo ruolo);

    // Ricerca per cognome (utile se volete una funzione di "cerca utente")
    List<Utente> findByCognomeNomeIgnoreCase(String cognome, String nome);
}
    

