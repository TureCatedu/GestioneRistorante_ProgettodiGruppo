package com.example.progettofinale.models;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotificaRequest(
    @NotNull(message = "la notifica deve essere associata ad una prenotazione")
    Prenotazione prenotazione,
    @NotNull(message = "la notifica deve avere una descrizione")
    @Size(max = 255, message = "la descrizione della notifica deve essere lunga almeno 1 carattere")
    String descrizione
){}