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
            container.innerHTML = `
                <div class="details-card">
                    
                    <img src="${getEventImage(event.type)}" class="details-image" alt="${event.type}">

                    <h1>${event.title}</h1>
                    <p class="description">${event.description || ''}</p>

                    <div class="info-grid">
                        <p><strong>Lieu :</strong> ${event.lieu}</p>
                        <p><strong>Prix :</strong> ${event.prix} €</p>
                        <p><strong>Capacité :</strong> ${event.capaciteMax} personnes</p>
                        <p><strong>Début :</strong> ${new Date(event.dateDebut).toLocaleString()}</p>
                        <p><strong>Fin :</strong> ${new Date(event.dateFin).toLocaleString()}</p>
                    </div>

                    <button class="btn-login" id="btn-reserver">
                        Réserver
                    </button>
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