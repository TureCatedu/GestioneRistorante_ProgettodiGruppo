package com.example.progettofinale.models;

public record LoginResponse(
    Integer id,
    String nome,
    String cognome,
    String email,
    Ruolo ruolo
) {}
