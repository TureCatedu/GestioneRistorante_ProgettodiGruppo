package com.example.progettofinale.models;

import java.time.LocalDateTime;

public record PrenotazioneResponse (
    Integer id,
    Utente utente,
    int numeroPersone,
    LocalDateTime dataOra
){}
