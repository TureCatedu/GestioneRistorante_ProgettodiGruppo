package com.example.progettofinale.models;

import java.time.LocalDateTime;

public record PrenotazioneResponse (
    Integer id,
    Integer utenteId,
    int numeroPersone,
    LocalDateTime dataOra
){}
