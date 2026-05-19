package com.example.progettofinale.models;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Prenotazione{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPrenotazione; 
    
    @NotEmpty
    @NotNull
    @Size(min = 5, max = 20, message = "il nome utente è obbligatorio")
    private String nomeCliente;
    
    @NotNull
    @Positive(message = "la prenotazione deve avere almeno 1 persona")
    private int numeroPersone;

    @NotNull
    @Future(message = "la prenotazione deve avere una data futura")
    private LocalDateTime dataOra;

    public Prenotazione() {
    }

    public Prenotazione(String nomeCliente, int numeroPersone, LocalDateTime dataOra) {
        this.nomeCliente = nomeCliente;
        this.numeroPersone = numeroPersone;
        this.dataOra = dataOra;
    }
    
    public String getNomeCliente() {
        return this.nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
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

}