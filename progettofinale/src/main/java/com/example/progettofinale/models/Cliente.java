package com.example.progettofinale.models;

import jakarta.persistence.*;

@Entity
@Table(name = "clienti")
public class Cliente extends Utente {

    private String numeroTelefono;
    
    public Cliente() {
        super();
    }

    public Cliente(String nome, String cognome, String email, String password, String numeroTelefono, Ruolo ruolo) {
        super(nome, cognome, email, password, ruolo);
        this.numeroTelefono = numeroTelefono;
    }

    // Getters e Setters
    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

}