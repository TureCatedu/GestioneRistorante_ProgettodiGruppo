package com.example.progettofinale.models;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "utenti")
@Inheritance(strategy = InheritanceType.JOINED)
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, length = 50)
    private String cognome;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @OneToMany
    @JoinColumn(name = "idPrenotazione")
    private List<Prenotazione> prenotazione;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;


    // Costruttori, Getter e Setter
    public Utente() {}

    public Utente(String nome, String cognome, String email, String password, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazione;
    }

    public void setPrenotazioni(List<Prenotazione> prenotazione) {
        this.prenotazione = prenotazione;
    }
}