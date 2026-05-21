package com.example.progettofinale.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prenotazione")
    private Integer idPrenotazione;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente cliente;

    @NotNull
    @Positive(message = "la prenotazione deve avere almeno 1 persona")
    private int numeroPersone;

    @NotNull
    @Future(message = "la prenotazione deve avere una data futura")
    private LocalDateTime dataOra;

    public Prenotazione() {
    }

    public Prenotazione(Utente cliente, int numeroPersone, LocalDateTime dataOra) {
        this.cliente = cliente;
        this.numeroPersone = numeroPersone;
        this.dataOra = dataOra;
    }

    public Utente getCliente() {
        return this.cliente;
    }

    public void setCliente(Utente cliente) {
        this.cliente = cliente;
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

    public int getId() {
        return this.idPrenotazione;
    }
}