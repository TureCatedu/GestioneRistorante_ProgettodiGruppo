package com.example.progettofinale.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.progettofinale.models.Ruolo;
import com.example.progettofinale.models.Utente;

@Repository
public interface UtenteRepo extends JpaRepository<Utente, Integer>
{
    // Ricerca per email (utile per il login e per garantire l'unicità)
    Optional<Utente> findByEmail(String email);

    // Per il Login: ricerca tramite email (essendo univoca)
    Optional<Utente> findByEmailAndPassword(String email, String password);

    // Per admin/proprietario: lista di tutti gli utenti di un certo tipo
    List<Utente> findByRuolo(Ruolo ruolo);

    // Ricerca per cognome (utile se volete una funzione di "cerca utente")
    List<Utente> findByCognomeAndNomeIgnoreCase(String cognome, String nome);
}
    

