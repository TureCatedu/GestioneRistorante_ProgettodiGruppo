package com.example.progettofinale.models;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record PrenotazioneRequest (
    @NotEmpty
    @NotNull
    @Positive(message = "l'id utente deve essere un numero positivo")
    Integer utenteId,
    @NotNull
    @Positive(message = "la prenotazione deve avere almeno 1 persona")
    int numeroPersone,
    @NotNull
    @Future(message = "la prenotazione deve avere una data futura")
    LocalDateTime dataOra
){}