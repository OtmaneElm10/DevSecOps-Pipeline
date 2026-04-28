const params = new URLSearchParams(window.location.search);
const eventId = params.get('id');

const container = document.getElementById('details-container');

function getEventImage(type) {
    if (type === 'MATCH') return 'images/match.jpg';
    if (type === 'TOURNOI') return 'images/tournoi.jpg';
    if (type === 'STAGE') return 'images/stage.jpg';
    if (type === 'SOIREE') return 'images/soiree.jpg';
    return 'images/default.jpg';
}

if (!eventId) {
    container.innerHTML = "<p>Aucun événement sélectionné.</p>";
} else {
    fetch(`/api/events/${eventId}`)
        .then(res => {
            if (!res.ok) throw new Error("Événement introuvable");
            return res.json();
        })
        .then(event => {

            const inscrits = event.nbInscrits || 0;
            const max = event.capaciteMax || 1;
            const pourcentage = Math.min((inscrits / max) * 100, 100);

            container.innerHTML = `
                <div class="details-card">

                    <div class="details-hero">
                        <img src="${getEventImage(event.type)}" class="details-image">
                        <div class="details-overlay">
                            <h1>${event.title}</h1>
                            <p class="details-type">${event.type}</p>
                        </div>
                    </div>

                    <div class="details-body">

                        <p class="description">${event.description || ''}</p>

                        <div class="info-grid">
                            <p><strong>📍 Lieu :</strong> ${event.lieu}</p>
                            <p><strong>💶 Prix :</strong> ${event.prix} €</p>
                            <p><strong>👥 Capacité :</strong> ${event.capaciteMax}</p>
                            <p><strong>📅 Début :</strong> ${new Date(event.dateDebut).toLocaleString()}</p>
                            <p><strong>📅 Fin :</strong> ${new Date(event.dateFin).toLocaleString()}</p>
                        </div>

                        <div class="progress-section">
                            <div class="participant-label">
                                <span>Participants</span>
                                <span>${inscrits} / ${max}</span>
                            </div>
                            <div class="progress-container">
                                <div class="progress-bar" style="width:${pourcentage}%"></div>
                            </div>
                        </div>

                        <button class="btn-reserve-big" id="btn-reserver">
                            Réserver maintenant
                        </button>

                    </div>
                </div>
            `;

            document.getElementById('btn-reserver').addEventListener('click', () => {
                window.location.href = `reservation.html?eventId=${event.id}`;
            });
        })
        .catch(err => {
            console.error(err);
            container.innerHTML = "<p>Erreur lors du chargement.</p>";
        });
}