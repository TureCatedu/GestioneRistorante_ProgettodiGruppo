package com.example.progettofinale.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.progettofinale.models.Ruolo;
import com.example.progettofinale.models.Utente;

public interface UtenteRepo extends JpaRepository<Utente, Integer>
{
    // Per il Login: ricerca tramite email (essendo univoca)
    Optional<Utente> findByEmailPassword(String email, String password);

    // Per admin/proprietario: lista di tutti gli utenti di un certo tipo
    List<Utente> findByRuolo(Ruolo ruolo);

    // Ricerca per cognome (utile se volete una funzione di "cerca utente")
    List<Utente> findByCognomeNomeIgnoreCase(String cognome, String nome);
}
    

