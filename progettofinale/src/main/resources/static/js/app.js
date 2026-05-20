/*function loadUtenti() {

    fetch("/api/utenti")
        .then(res => res.json())
        .then(data => {

            const table = document.getElementById("utenti-table-body");
            if (!table) return;

            table.innerHTML = "";

            data.forEach(u => {

                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${u.id}</td>
                    <td>${u.nome}</td>
                    <td>${u.cognome}</td>
                    <td>${u.email}</td>
                    <td>${u.ruolo}</td>
                `;

                table.appendChild(row);
            });
        });
}

function creaUtente(event) {
    event.preventDefault();

    const utente = {
        nome: document.getElementById("utente-nome-input").value,
        cognome: document.getElementById("utente-cognome-input").value,
        email: document.getElementById("utente-email-input").value,
        password: document.getElementById("utente-password-input").value,
        ruolo: document.getElementById("utente-ruolo-select").value
    };

    fetch("/api/utenti", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(utente)
    })
    .then(() => {
        loadUtenti();
    });
}

function loadPrenotazioni() {

    fetch("/api/prenotazione")
        .then(res => res.json())
        .then(data => {

            const table = document.getElementById("prenotazioni-table-body");
            if (!table) return;

            table.innerHTML = "";

            data.forEach(p => {

                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${p.id}</td>
                    <td>${p.utenteId}</td>
                    <td>${p.numeroPersone}</td>
                    <td>${p.dataOra}</td>
                `;

                table.appendChild(row);
            });
        });
}

function creaPrenotazione(event) {
    event.preventDefault();

    const prenotazione = {
        utenteId: document.getElementById("utente-id-input").value,
        numeroPersone: document.getElementById("numero-persone-input").value,
        dataOra: document.getElementById("data-input").value
    };

    fetch("/api/prenotazione", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(prenotazione)
    })
    .then(() => {
        loadPrenotazioni();
    });
}

function loadNotificheUtente() {

    const id = document.getElementById("notifiche-utente-id-input")?.value;

    if (!id) return;

    fetch(`/api/notificatore/utente/${id}`)
        .then(res => res.json())
        .then(data => {

            const table = document.getElementById("notifiche-table-body");
            if (!table) return;

            table.innerHTML = "";

            data.forEach(n => {

                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${n.id}</td>
                    <td>${n.messaggio}</td>
                    <td>${n.utenteId}</td>
                    <td>${n.prenotazioneId}</td>
                    <td>${n.data}</td>
                `;

                table.appendChild(row);
            });
        });
}

function deleteNotifiche(event) {
    event.preventDefault();

    const id = document.getElementById("delete-notifiche-id-input").value;

    fetch(`/api/notificatore/utente/${id}`, {
        method: "DELETE"
    }).then(() => {
        alert("Notifiche eliminate");
    });
}

function registerUtente(event) {
    event.preventDefault();

    const utente = {
        nome: document.getElementById("register-nome-input").value,
        cognome: document.getElementById("register-cognome-input").value,
        email: document.getElementById("register-email-input").value,
        password: document.getElementById("register-password-input").value,
        ruolo: document.getElementById("register-ruolo-select").value
    };

    fetch("/api/utenti", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(utente)
    })
    .then(res => {
        if (res.ok) {
            alert("Registrazione completata");
            window.location.href = "/login";
        } else {
            alert("Errore registrazione");
        }
    });
}

document.addEventListener("DOMContentLoaded", () => {

    // UTENTI
    if (document.getElementById("utenti-table-body")) {
        loadUtenti();

        const form = document.getElementById("crea-utente-form");
        if (form) form.addEventListener("submit", creaUtente);
    }

    // PRENOTAZIONI
    if (document.getElementById("prenotazioni-table-body")) {
        loadPrenotazioni();

        const form = document.getElementById("prenotazione-form");
        if (form) form.addEventListener("submit", creaPrenotazione);
    }

    // NOTIFICHE
    if (document.getElementById("notifiche-table-body")) {

        const formDelete = document.getElementById("delete-notifiche-form");
        if (formDelete) formDelete.addEventListener("submit", deleteNotifiche);

        const formSearch = document.getElementById("notifiche-utente-form");
        if (formSearch) {
            formSearch.addEventListener("submit", (e) => {
                e.preventDefault();
                loadNotificheUtente();
            });
        }
    }
    //registrazione utente
    if (document.getElementById("register-form")) {
        document.getElementById("register-form")
        .addEventListener("submit", registerUtente);
    }

});*/