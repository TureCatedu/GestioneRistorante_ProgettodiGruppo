package com.example.progettofinale.models;

import java.time.LocalDateTime;

public record PrenotazioneResponse (
    Integer id,
    String nomeCliente,
    int numeroPersone,
    LocalDateTime dataOra
){}
