package com.example.progettofinale.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Notifiche")
public class Notifica {
    @Id
    private Integer id;
    //foreign key
    @ManyToOne
    @JoinColumn(name = "prenotazione_id")
    private Prenotazione prenotazione;
    @ManyToMany
    @NotNull

    //descrizione non nulla e max 255 caratteri
    @NotNull
    @Size(max = 255)
    private String descrizione;
    //costruttore jpa
    protected Notifica() {
    }
    //costruttore con parametri
    public Notifica(Prenotazione prenotazione,String descrizione) {
        this.descrizione = descrizione;
        this.prenotazione = prenotazione;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Prenotazione getPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    //to string
    @Override
    public String toString() {
        return "Notifica{" + "id =" + id + ", prenotazione =" + prenotazione.getId() + ", descrizione=" + descrizione + '}';
    }
    
    
}
