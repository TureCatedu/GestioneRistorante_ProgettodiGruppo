package com.example.progettofinale.models;
import java.time.LocalDateTime;

public class Prenotazione{
    private String nomeCliente;
    private int numeroPersone;
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