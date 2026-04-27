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
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const payments = await response.json();
            displayPayments(payments);
        } else {
            paymentsContainer.innerHTML = "<p>Erreur lors du chargement des paiements.</p>";
        }
    } catch (error) {
        console.error("Erreur réseau :", error);
        paymentsContainer.innerHTML = "<p>Impossible de joindre le serveur.</p>";
    }
}

function displayPayments(payments) {
    if (payments.length === 0) {
        paymentsContainer.innerHTML = "<p>Vous n'avez effectué aucun paiement pour le moment.</p>";
        return;
    }

    paymentsContainer.innerHTML = `
        <table class="payments-table">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Événement</th>
                    <th>Montant</th>
                    <th>Statut</th>
                </tr>
            </thead>
            <tbody>
                ${payments.map(p => `
                    <tr>
                        <td>${new Date(p.paymentDate).toLocaleDateString()}</td>
                        <td>${p.eventTitle || 'Événement'}</td>
                        <td>${p.amount} €</td>
                        <td><span class="status-${p.status.toLowerCase()}">${p.status}</span></td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;
}

loadPayments();
