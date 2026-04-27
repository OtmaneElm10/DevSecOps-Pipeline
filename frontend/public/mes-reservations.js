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
    if (reservations.length === 0) {
        container.innerHTML = "<p>Vous n'avez aucune réservation.</p>";
        return;
    }

    container.innerHTML = `
        <table class="payments-table">
            <thead>
                <tr>
                    <th>Événement</th>
                    <th>Places</th>
                    <th>Prix Total</th>
                    <th>Statut</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                ${reservations.map(res => {
        let actionButtons = '';
        let statusLabel = (res.statut || 'EN_ATTENTE_DE_PAIEMENT').toUpperCase();

        if (statusLabel === 'EN_ATTENTE_DE_PAIEMENT' || statusLabel === 'EN ATTENTE DE PAIEMENT') {
            actionButtons = `
                            <button class="btn-login" onclick="payReservation(${res.id})" style="padding: 5px; font-size: 12px; margin-right: 5px;">Payer</button>
                            <button class="btn-edit" onclick="modifyReservation(${res.id}, ${res.nbPlaces})" style="padding: 5px; font-size: 12px; margin-right: 5px; background: #f39c12; color: white; border: none; border-radius: 4px; cursor: pointer;">Modifier</button>
                            <button class="btn-delete" onclick="cancelReservation(${res.id})" style="padding: 5px; font-size: 12px;">Annuler</button>
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
                        <td>${res.montantAttendu || res.prixTotal || '0'} €</td>
                        <td><strong>${statusLabel}</strong></td>
                        <td>${actionButtons}</td>
                    </tr>
                    `;
    }).join('')}
            </tbody>
        </table>
    `;
}

window.payReservation = function(reservationId) {
    window.location.href = `payement.html?reservationId=${reservationId}`;
};

window.modifyReservation = async function(reservationId, currentPlaces) {
    const newPlaces = prompt("Combien de places souhaitez-vous réserver au total ?", currentPlaces);

    if (!newPlaces || isNaN(newPlaces) || newPlaces <= 0) return;

    try {
        const response = await fetch(`/api/reservations/${reservationId}`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ nbPlaces: parseInt(newPlaces, 10) })
        });

        if (response.ok) {
            alert("Réservation modifiée ! Le prix total a été recalculé.");
            loadReservations();
        } else {
            alert("Impossible de modifier la réservation.");
        }
    } catch (error) {
        console.error(error);
    }
};

window.cancelReservation = async function(reservationId) {
    if (!confirm("Êtes-vous sûr de vouloir annuler cette réservation ?")) return;

    try {
        const response = await fetch(`/api/reservations/${reservationId}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (response.ok) {
            alert("Réservation annulée !");
            loadReservations();
        } else {
            alert("Impossible d'annuler cette réservation.");
        }
    } catch (error) {
        console.error(error);
    }
};

loadReservations();
