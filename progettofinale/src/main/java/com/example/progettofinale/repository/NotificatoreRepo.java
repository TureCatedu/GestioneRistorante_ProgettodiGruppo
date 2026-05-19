package com.example.progettofinale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.progettofinale.models.Notifica;

@Repository
public interface NotificatoreRepo extends JpaRepository<Notifica, Integer>{
    
}
