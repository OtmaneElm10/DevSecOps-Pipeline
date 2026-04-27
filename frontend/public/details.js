const params = new URLSearchParams(window.location.search);
const eventId = params.get('id');

const container = document.getElementById('details-container');

if (!eventId) {
    container.innerHTML = "<p>Aucun événement sélectionné. Retourne à l'accueil et clique sur une carte.</p>";
} else {
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
                        Réserver
                    </button>
                </div>
            `;

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
    window.location.href = `reservation.html?eventId=${event.id}`;
}