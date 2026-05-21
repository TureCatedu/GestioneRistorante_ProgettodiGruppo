package com.example.progettofinale.controller;

import com.example.progettofinale.models.LoginRequest;
import com.example.progettofinale.models.LoginResponse;
import com.example.progettofinale.models.Ruolo;
import com.example.progettofinale.models.Utente;
import com.example.progettofinale.services.UtenteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/utenti")
public class UtenteController {
    
    private final UtenteService utenteService;
    private final PasswordEncoder passwordEncoder;

    public UtenteController(UtenteService utenteService, PasswordEncoder passwordEncoder) {
        this.utenteService = utenteService;
        this.passwordEncoder = passwordEncoder;
    }

    // GET: trova utente per ID
    @GetMapping("/{id}") 
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String findById(@PathVariable Integer id, Model model) {

        LoginResponse utente = utenteService.findById(id);

        model.addAttribute("utente", utente);

        return "utente";
    }

    // GET: lista di tutti gli utenti (Solo Admin)
    @GetMapping
    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    public String findAll(Model model) {

        List<LoginResponse> utenti = utenteService.findAll();

        model.addAttribute("utenti", utenti);

        return "utenti";
    }

    // GET: cerca utenti per cognome e nome (Admin e Camerieri)
    @GetMapping("/cognome-nome")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE')")
    public String findByCognomeNome(@RequestParam String cognome,
                                     @RequestParam String nome,
                                     Model model) {

        List<LoginResponse> utenti =
                utenteService.findByCognomeAndNomeIgnoreCase(cognome, nome);

        model.addAttribute("utenti", utenti);

        return "utenti";
    }

    // GET: trova utenti per ruolo (Solo Admin)
    @GetMapping("/ruolo")
    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    public String findByRuolo(@RequestParam Ruolo ruolo,
                              Model model) {

        List<LoginResponse> utenti = utenteService.findByRuolo(ruolo);

        model.addAttribute("utenti", utenti);

        return "utenti";
    }
    // GET: Ottieni il profilo appena loggato 
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String getMioProfilo(Authentication authentication,
                                Model model) {

        LoginResponse utente = utenteService.findByEmail(authentication.getName());

        model.addAttribute("utenteLoggato", utente);

        return "profilo";
    }

    // GET: Effettua il logout dell'utente corrente
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // Se c'è un utente loggato, Spring Security gestisce la pulizia della sessione
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        // Dopo il logout, reindirizziamo l'utente alla pagina di login (o alla home)
        return "redirect:/login"; 
    }
    /* 
    // POST: Login 
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request,
                        Model model) {

         LoginResponse loginResponse = utenteService.findByEmailComplete(request.email()).filter(u -> passwordEncoder.matches(request.password(), u.getPassword())).map(u ->new LoginResponse(
                    u.getId(),
                    u.getNome(),
                    u.getCognome(),
                    u.getEmail(),
                    u.getRuolo()
                )).orElseGet(() -> new LoginResponse(null, null, null, null, null));

        model.addAttribute("utente", loginResponse);

        return "redirect:/";
    }
    */

    // POST: Crea un nuovo utente
    @PostMapping
    public String createUtente(Utente nuovoUtente) {
        String passwordCriptata = passwordEncoder.encode(nuovoUtente.getPassword());
        nuovoUtente.setPassword(passwordCriptata);
        utenteService.save(nuovoUtente);

        return "redirect:/login";
    }
    // PUT: Modifica un utente (Tutti i ruoli)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('AMMINISTRATORE', 'CAMERIERE', 'CLIENTE')")
    public String updateUtente(@PathVariable Integer id,
                               Utente utenteAggiornato) {

        Utente utente = utenteService.findByIdComplete(id).orElse(null);
        if (utente != null) {
            utente.setNome(utenteAggiornato.getNome());
            utente.setCognome(utenteAggiornato.getCognome());
            utente.setEmail(utenteAggiornato.getEmail());
            utente.setRuolo(utenteAggiornato.getRuolo());

            utenteService.save(utente);
        }

        return "redirect:/utenti";
    }
    
    // DELETE: Rimuove un utente per ID (Solo Admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('AMMINISTRATORE')")
    public String deleteUtente(@PathVariable Integer id) {

        utenteService.deleteById(id);

        return "redirect:/utenti";
    }
}