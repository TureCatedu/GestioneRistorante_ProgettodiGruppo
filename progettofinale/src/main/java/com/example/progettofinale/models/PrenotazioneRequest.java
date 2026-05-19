package com.example.progettofinale.models;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PrenotazioneRequest (
    @NotEmpty
    @NotNull
    @Size(min = 5, max = 20, message = "il nome utente è obbligatorio")
    Utente utente,
    @NotNull
    @Positive(message = "la prenotazione deve avere almeno 1 persona")
    int numeroPersone,
    @NotNull
    @Future(message = "la prenotazione deve avere una data futura")
    LocalDateTime dataOra
){}