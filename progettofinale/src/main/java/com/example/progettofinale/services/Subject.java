package com.example.progettofinale.services;

import com.example.progettofinale.models.Notifica;

//interfaccia subject

public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(Notifica notifica);
    
}
