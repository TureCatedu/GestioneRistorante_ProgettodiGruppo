# Gestione Ristorante - Progetto di Gruppo

Un'applicazione web full-stack per la gestione di prenotazioni in un ristorante, sviluppata con **Spring Boot 4.0.6** e database **H2**. Il sistema implementa un'architettura MVC con autenticazione e autorizzazione basate su ruoli.

---

## 📋 Descrizione del Progetto

L'applicazione permette di gestire:
- **Utenti** (Amministratore, Cameriere, Cliente)
- **Prenotazioni** con validazione delle date future
- **Notifiche** tramite pattern Observer
- **Autenticazione e autorizzazione** con Spring Security

### Funzionalità Principali

✅ Registrazione e login di utenti  
✅ Gestione prenotazioni con controllo proprietà  
✅ Sistema di notifiche in tempo reale  
✅ Gestione utenti con ruoli (solo Amministratore)  
✅ Visualizzazione del profilo personale  
✅ Gestione globale degli errori  
✅ Interfaccia web intuitiva con CSS personalizzato  

---

## 🛠️ Stack Tecnologico

### Backend
- **Java 25**
- **Spring Boot 4.0.6**
  - Spring Data JPA
  - Spring Security
  - Spring Web MVC
  - Thymeleaf
  - Validation
- **Database**: H2 (in-memory/file-based)
- **Build Tool**: Maven

### Frontend
- **Thymeleaf** (template engine)
- **HTML5**
- **CSS3** personalizzato
- **Spring Security Thymeleaf Dialect** (per autorizzazione nel template)

---

## 📁 Struttura del Progetto

```
progettofinale/
├── src/
│   ├── main/
│   │   ├── java/com/example/progettofinale/
│   │   │   ├── ProgettofinaleApplication.java
│   │   │   ├── controller/              # Gestione richieste HTTP
│   │   │   │   ├── PrenotazioneController.java
│   │   │   │   ├── UtenteController.java
│   │   │   │   ├── NotificatoreController.java
│   │   │   │   ├── ViewController.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── models/                  # Entità JPA e DTO
│   │   │   │   ├── Utente.java
│   │   │   │   ├── Prenotazione.java
│   │   │   │   ├── Notifica.java
│   │   │   │   ├── Ruolo.java
│   │   │   │   ├── LoginRequest/Response.java
│   │   │   │   └── PrenotazioneRequest/Response.java
│   │   │   ├── repository/              # Data Access Layer
│   │   │   │   ├── UtenteRepo.java
│   │   │   │   ├── PrenotazioneRepo.java
│   │   │   │   └── NotificaRepo.java
│   │   │   ├── services/                # Logica di business
│   │   │   │   ├── RistoranteFacade.java
│   │   │   │   ├── Notificatore.java (Observer)
│   │   │   │   └── Observer.java
│   │   │   └── errorResponse/           # Gestione errori globali
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       ├── NotificaNonTrovataException.java
│   │   │       ├── ErrorResponse.java
│   │   │       ├── PrenotazioneNonTrovataException.java
│   │   │       └── UtenteNonTrovatoException.java
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── templates/               # Thymeleaf templates
│   │   │   │   ├── index.html
│   │   │   │   ├── login.html
│   │   │   │   ├── register.html
│   │   │   │   ├── prenotazioni.html
│   │   │   │   ├── nuova_prenotazione.html
│   │   │   │   ├── prenotazione.html
│   │   │   │   ├── utenti.html
│   │   │   │   ├── utente.html
│   │   │   │   ├── profilo.html
│   │   │   │   ├── notifiche.html
│   │   │   │   └── error.html
│   │   │   └── static/
│   │   │       └── css/
│   │   │           └── style.css
│   └── test/                            # Test unitari
├── h2-db/                               # Database file
├── pom.xml
└── target/
```

---

## 👥 Contribuenti e Ruoli

| Nome | Ruolo | Responsabilità |
|------|-------|-----------------|
| **Mauro Bilardo** | Backend Lead | Modelli (Entity/DTO), Risoluzione errori, Repository e Data Access, Facade |
| **Tommaso Ciccotti** | Frontend & Service | Frontend (Template/CSS/Thymeleaf), Services, Pattern Observer, DTO Management, Eccezioni custom e gestione errori, Facade |
| **Andrea Catudella** | Error Handling & Design | Gestione Errori Globali, Controller API, Styling CSS, Gestione Autenticazione e Permessi |

---

## 🚀 Come Avviare il Progetto

### Prerequisiti
- **Java 25** installato
- **Maven** 3.6+
- **Git**

### Installazione e Esecuzione

1. **Clonare il repository**
   ```bash
   git clone https://github.com/TureCatedu/GestioneRistorante_ProgettodiGruppo.git
   cd GestioneRistorante_ProgettodiGruppo
   ```

2. **Installare le dipendenze**
   ```bash
   cd progettofinale
   mvn clean install
   ```

3. **Avviare l'applicazione**
   ```bash
   mvn spring-boot:run
   ```
   L'applicazione sarà disponibile su `http://localhost:8080`

4. **Accesso H2 Console** (per debugging del database)
   - URL: `http://localhost:8080/h2-console`
   - Driver: `org.h2.Driver`
   - URL di connessione: `jdbc:h2:file:./h2-db/ristorantedb`
   - Username: `sa`
   - Password: (vuota)

---

## 🔐 Autenticazione e Ruoli

Il sistema implementa tre ruoli principali:

| Ruolo | Permessi |
|-------|----------|
| **AMMINISTRATORE** | Gestione utenti, visualizzazione tutte prenotazioni |
| **CAMERIERE** | Visualizzazione prenotazioni, inserimento notifiche |
| **CLIENTE** | Visualizzazione proprie prenotazioni, creazione prenotazioni |

### Flusso di Autenticazione

1. **Registrazione** → Creazione nuovo utente con ruolo CLIENTE
2. **Login** → Validazione credenziali tramite Spring Security
3. **Autorizzazione** → Controllo permessi basato su ruoli (`@PreAuthorize`)
4. **Proprietà Risorsa** → Verifica che il cliente acceda solo a proprie prenotazioni

---

## 🔌 API Principali

### Utenti
- `GET /utenti` → Lista di tutti gli utenti (solo AMMINISTRATORE)
- `GET /utenti/{id}` → Dettagli utente
- `GET /utenti/me` → Profilo dell'utente autenticato
- `POST /register` → Registrazione nuovo utente
- `POST /login` → Login utente

### Prenotazioni
- `GET /prenotazioni` → Lista prenotazioni (filtrate per ruolo)
- `GET /prenotazioni/{id}` → Dettagli prenotazione
- `POST /prenotazioni` → Creazione nuova prenotazione
- `PUT /prenotazioni/{id}` → Modifica prenotazione
- `DELETE /prenotazioni/{id}` → Eliminazione prenotazione

### Notifiche
- `GET /notificatore/utente/me` → Notifiche dell'utente autenticato
- `POST /notificatore` → Invio notifica (Observer Pattern)

---

## 🎨 Caratteristiche UI/UX

- **Responsive Design**: Interfaccia adattabile su desktop e mobile
- **Controllo Accesso Frontend**: Uso di `sec:authorize` nel Thymeleaf per nascondere elementi basati su ruoli
- **Validazione Lato Server**: Controllo date future, numero persone positivo
- **Gestione Globale Errori**: Risposta HTTP strutturata per tutti gli errori
- **Pagina Errore Personalizzata**: `error.html` per eccezioni non gestite

---

## 📚 Dipendenze Principali

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-h2console</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
</dependency>
```

---

## ⚙️ Configurazione

File di configurazione principale: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:h2:file:./h2-db/ristorantedb
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
spring.thymeleaf.prefix=classpath:/templates/
```

---

## 🐛 Gestione Errori

Il progetto implementa una gestione globale degli errori tramite `@ControllerAdvice`:

- **AccessDeniedException** (403) → Accesso negato per permessi insufficienti
- **PrenotazioneNonTrovataException** (404) → Prenotazione non trovata
- **UtenteNonTrovatoException** (404) → Utente non trovato
- **NotificaNonTrovataException** (404) → Notifica non trovata
- **Exception** (500) → Errore generico

Tutte le risposte di errore aprono la pagina di errore con timestamp e messaggio descrittivo.

---

## 🔍 Pattern Implementati

- **MVC (Model-View-Controller)** → Separazione tra logica, visualizzazione e controllo
- **DAO (Data Access Object)** → Repository per accesso dati tramite JPA
- **Facade** → `RistoranteFacade` per operazioni aggregate
- **Observer** → Notificatore per propagazione notifiche
- **DTO (Data Transfer Object)** → Classi di trasferimento dati tra layer

---

## 📝 Note di Sviluppo

- Le password sono gestite da Spring Security (encoding automatico)
- L'autorizzazione è implementata sia nel controller (`@PreAuthorize`) che nel template (Thymeleaf dialect)
- Validazione delle date future: le prenotazioni non possono essere nel passato
- Numero persone minimo: 1 persona per prenotazione

---

Progetto accademico - Corso Masamune

---
