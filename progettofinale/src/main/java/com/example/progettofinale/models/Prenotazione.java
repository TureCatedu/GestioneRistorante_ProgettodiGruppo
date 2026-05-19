package com.example.progettofinale.models;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Prenotazione{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPrenotazione; 
    
    @NotNull
    @Valid
    private Utente nomeCliente;
    
    @NotNull
    @Positive(message = "la prenotazione deve avere almeno 1 persona")
    private int numeroPersone;

    @NotNull
    @Future(message = "la prenotazione deve avere una data futura")
    private LocalDateTime dataOra;

    public Prenotazione() {
    }

    public Prenotazione(Utente nomeCliente, int numeroPersone, LocalDateTime dataOra) {
        this.nomeCliente = nomeCliente;
        this.numeroPersone = numeroPersone;
        this.dataOra = dataOra;
    }
    
    public Utente getNomeCliente() {
        return this.nomeCliente;
    }

    public void setNomeCliente(Utente nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getNumeroPersone() {
        return this.numeroPersone;
    }

    public void setNumeroPersone(int numeroPersone) {
        this.numeroPersone = numeroPersone;
    }

    public LocalDateTime getDataOra() {
        return this.dataOra;
    }

    public void setDataOra(LocalDateTime dataOra) {
        this.dataOra = dataOra;
    }

    public int getId(){
        return this.idPrenotazione;
    }
}