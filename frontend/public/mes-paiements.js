const token = localStorage.getItem('token');
const userId = localStorage.getItem('userId');
const paymentsContainer = document.getElementById('payments-container');

if (!token || !userId) {
    alert("Vous devez être connecté pour voir vos paiements.");
    window.location.href = 'login.html';
}

async function loadPayments() {
    try {
        const response = await fetch(`/api/paiements/user/${userId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const payments = await response.json();
            displayPayments(payments);
        } else {
            const err = await response.text();
            console.error("Erreur serveur :", err);
            paymentsContainer.innerHTML = "<p>Erreur lors du chargement des paiements.</p>";
        }
    } catch (error) {
        console.error("Erreur réseau :", error);
        paymentsContainer.innerHTML = "<p>Impossible de joindre le serveur.</p>";
    }
}

function displayPayments(payments) {
    if (!payments || payments.length === 0) {
        paymentsContainer.innerHTML = "<p>Vous n'avez effectué aucun paiement pour le moment.</p>";
        return;
    }

    paymentsContainer.innerHTML = `
        <table class="payments-table">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Montant</th>
                    <th>Statut</th>
                    <th>Réservation</th>
                </tr>
            </thead>
            <tbody>
                ${payments.map(p => `
                    <tr>
                        <td>${p.datePaiement ? new Date(p.datePaiement).toLocaleDateString() : '-'}</td>
                        <td>${p.montant} €</td>
                        <td>
                            <span class="status-${p.statut.toLowerCase()}">
                                ${p.statut}
                            </span>
                        </td>
                        <td>#${p.reservationId}</td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;
}

loadPayments();