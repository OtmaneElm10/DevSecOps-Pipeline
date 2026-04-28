const token = localStorage.getItem('token');
const role = localStorage.getItem('role');
const container = document.getElementById('dashboard-container');

if (!token || role !== 'ADMIN') {
    alert("Accès refusé. Vous devez être administrateur.");
    window.location.href = "index.html";
}

async function loadDashboard() {
    try {
        const response = await fetch('/api/reservations', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            const errorText = await response.text();
            console.error("Erreur dashboard :", response.status, errorText);
            container.innerHTML = "<p>Impossible de charger les réservations.</p>";
            return;
        }

        const reservations = await response.json();
        displayDashboard(reservations);

    } catch (error) {
        console.error("Erreur réseau :", error);
        container.innerHTML = "<p>Impossible de joindre le serveur.</p>";
    }
}

function displayDashboard(reservations) {
    if (!reservations || reservations.length === 0) {
        container.innerHTML = "<p>Aucune réservation trouvée.</p>";
        return;
    }

    const eventsMap = {};

    reservations.forEach(res => {
        const eventId = res.eventId || 'unknown';
        const eventTitle = res.eventTitle || `Événement #${eventId}`;

        if (!eventsMap[eventId]) {
            eventsMap[eventId] = {
                title: eventTitle,
                reservations: [],
                totalPlaces: 0,
                totalMontant: 0
            };
        }

        eventsMap[eventId].reservations.push(res);
        eventsMap[eventId].totalPlaces += res.nbPlaces || 0;
        eventsMap[eventId].totalMontant += res.montantAttendu || 0;
    });

    container.innerHTML = Object.values(eventsMap).map(event => `
        <div class="admin-event-block">
            <h3>${event.title}</h3>

            <div class="dashboard-stats">
                <p><strong>Réservations :</strong> ${event.reservations.length}</p>
                <p><strong>Places réservées :</strong> ${event.totalPlaces}</p>
                <p><strong>Montant total attendu :</strong> ${event.totalMontant} €</p>
            </div>

            <table class="payments-table">
                <thead>
                    <tr>
                        <th>Utilisateur</th>
                        <th>Places</th>
                        <th>Montant</th>
                        <th>Statut</th>
                    </tr>
                </thead>
                <tbody>
                    ${event.reservations.map(res => `
                        <tr>
                            <td>${res.username || 'Utilisateur #' + (res.userId || '')}</td>
                            <td>${res.nbPlaces || 0}</td>
                            <td>${res.montantAttendu || 0} €</td>
                            <td><strong>${res.statut || '-'}</strong></td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        </div>
    `).join('');
}

loadDashboard();