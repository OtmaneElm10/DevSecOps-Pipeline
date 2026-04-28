const token = localStorage.getItem('token');
const userId = localStorage.getItem('userId');
const container = document.getElementById('reservations-container');

if (!token || !userId) {
    alert("Vous devez être connecté pour voir vos réservations.");
    window.location.href = 'login.html';
}

async function loadReservations() {
    try {
        const response = await fetch(`/api/reservations/user/${userId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const reservations = await response.json();
            displayReservations(reservations);
        } else {
            container.innerHTML = "<p>Erreur lors du chargement de vos réservations.</p>";
        }
    } catch (error) {
        console.error("Erreur réseau :", error);
        container.innerHTML = "<p>Impossible de joindre le serveur.</p>";
    }
}

function displayReservations(reservations) {
    if (!reservations || reservations.length === 0) {
        container.innerHTML = "<p>Vous n'avez aucune réservation.</p>";
        return;
    }

    container.innerHTML = `
        <table class="payments-table">
            <thead>
                <tr>
                    <th>Événement</th>
                    <th>Places</th>
                    <th>Prix total</th>
                    <th>Statut</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                ${reservations.map(res => {
                    const statusLabel = (res.statut || 'EN_ATTENTE_DE_PAIEMENT').toUpperCase();
                    const montant = res.montantAttendu || res.prixTotal || 0;

                    let actionButtons = '';

                    if (statusLabel === 'EN_ATTENTE_DE_PAIEMENT' || statusLabel === 'EN ATTENTE DE PAIEMENT') {
                        actionButtons = `
                            <button class="btn-login"
                                onclick="payReservation(${res.id}, ${montant})"
                                style="padding: 5px; font-size: 12px; margin-right: 5px;">
                                Payer
                            </button>

                            <button class="btn-delete"
                                onclick="cancelReservation(${res.id})"
                                style="padding: 5px; font-size: 12px;">
                                Annuler
                            </button>
                        `;
                    } else if (statusLabel === 'ANNULEE' || statusLabel === 'ANNULÉE') {
                        actionButtons = `<em>Réservation annulée</em>`;
                    } else if (statusLabel === 'PAYEE' || statusLabel === 'PAYÉE') {
                        actionButtons = `<em>Payé</em>`;
                    }

                    return `
                        <tr>
                            <td>${res.eventTitle || 'Événement #' + (res.eventId || '')}</td>
                            <td>${res.nbPlaces || 1}</td>
                            <td>${montant} €</td>
                            <td><strong>${statusLabel}</strong></td>
                            <td>${actionButtons}</td>
                        </tr>
                    `;
                }).join('')}
            </tbody>
        </table>
    `;
}

window.payReservation = function(reservationId, amount) {
    window.location.href = `payment.html?reservationId=${reservationId}&amount=${amount}`;
};

window.cancelReservation = async function(reservationId) {
    if (!confirm("Êtes-vous sûr de vouloir annuler cette réservation ?")) {
        return;
    }

    try {
        const response = await fetch(`/api/reservations/${reservationId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            alert("Réservation annulée !");
            loadReservations();
        } else {
            const errorText = await response.text();
            console.error("Erreur annulation :", response.status, errorText);
            alert("Impossible d'annuler cette réservation.");
        }
    } catch (error) {
        console.error("Erreur réseau :", error);
        alert("Impossible de joindre le serveur.");
    }
};

loadReservations();