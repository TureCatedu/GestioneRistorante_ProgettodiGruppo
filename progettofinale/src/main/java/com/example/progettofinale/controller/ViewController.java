package com.example.progettofinale.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/profilo")
    public String profilo() {
        return "profilo";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
    @GetMapping("/prenotazioni/nuova")
    public String nuovaPrenotazione() {
        return "nuova-prenotazione";
    }
}