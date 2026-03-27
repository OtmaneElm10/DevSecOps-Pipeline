const params = new URLSearchParams(window.location.search);
const eventId = params.get('id');

const container = document.getElementById('details-container');

// Le backend doit avoir une route @GetMapping("/{id}")
fetch(`http://localhost:8080/api/events/${eventId}`)
    .then(res => res.json())
    .then(event => {
        container.innerHTML = `
            <div class="details-card">
                <h1>${event.title}</h1>
                <p class="description">${event.description}</p>
                
                <div class="info-grid">
                    <p><strong> Lieu :</strong> ${event.lieu}</p>
                    <p><strong> Prix :</strong> ${event.prix} €</p>
                    <p><strong> Capacité :</strong> ${event.capaciteMax} personnes</p>
                    <p><strong> Début :</strong> ${new Date(event.dateDebut).toLocaleString()}</p>
                    <p><strong> Fin :</strong> ${new Date(event.dateFin).toLocaleString()}</p>
                </div>

                <button class="btn-confirm-inscription" onclick="confirmer()">
                    Confirmer mon inscription
                </button>
            </div>
        `;
    })
    .catch(err => {
        container.innerHTML = "<p>Erreur lors du chargement ou événement introuvable.</p>";
    });

function confirmer() {
    alert("Demande d'inscription envoyée au club !");
}

function confirmerInscription(event) {
    console.log("Prix de l'événement :", event.prix);
    if (event.prix > 0) {
        const titleEncoded = encodeURIComponent(event.title);
        window.location.href = `payment.html?id=${event.id}&amount=${event.prix}&title=${titleEncoded}`;
    } else {
        alert("Inscription gratuite confirmée !");
        // Appel fetch POST 
    }
}