
const params = new URLSearchParams(window.location.search);
const eventId = params.get('id');

const container = document.getElementById('details-container');

if (!eventId) {
    container.innerHTML = "<p>Aucun événement sélectionné. Retourne à l'accueil et clique sur une carte.</p>";
} else {
    // URL RELATIVE (fonctionne peu importe le host : localhost, 192.168.x.x, etc.)
    fetch(`/api/events/${eventId}`)
        .then(res => {
            if (!res.ok) throw new Error("Événement introuvable (status " + res.status + ")");
            return res.json();
        })
        .then(event => {
            container.innerHTML = `
                <div class="details-card">
                    <h1>${event.title}</h1>
                    <p class="description">${event.description}</p>

                    <div class="info-grid">
                        <p><strong>📍 Lieu :</strong> ${event.lieu}</p>
                        <p><strong>💶 Prix :</strong> ${event.prix} €</p>
                        <p><strong>👥 Capacité :</strong> ${event.capaciteMax} personnes</p>
                        <p><strong>📅 Début :</strong> ${new Date(event.dateDebut).toLocaleString()}</p>
                        <p><strong>📅 Fin :</strong> ${new Date(event.dateFin).toLocaleString()}</p>
                    </div>

                    <button class="btn-confirm-inscription" id="btn-confirmer">
                        Confirmer mon inscription
                    </button>
                </div>
            `;

            // On attache le gestionnaire après injection HTML
            document.getElementById('btn-confirmer').addEventListener('click', () => {
                confirmerInscription(event);
            });
        })
        .catch(err => {
            console.error(err);
            container.innerHTML = "<p>Erreur lors du chargement ou événement introuvable.</p>";
        });
}

function confirmerInscription(event) {
    console.log("Prix de l'événement :", event.prix);
    if (event.prix > 0) {
        const titleEncoded = encodeURIComponent(event.title);
        window.location.href = `payment.html?id=${event.id}&amount=${event.prix}&title=${titleEncoded}`;
    } else {
        alert("Inscription gratuite confirmée !");
        // TODO : appel fetch POST vers /api/events/{id}/inscription si besoin
    }
}
