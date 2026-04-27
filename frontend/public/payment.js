const params = new URLSearchParams(window.location.search);

const reservationId = params.get('reservationId');
const amount = params.get('amount');
const token = localStorage.getItem('token');

const amountDisplay = document.getElementById('amount-display');
const paymentForm = document.getElementById('payment-form');

if (!token) {
    alert("Vous devez être connecté pour payer.");
    window.location.href = "login.html";
}

if (!reservationId) {
    alert("Réservation introuvable.");
    window.location.href = "mes-reservations.html";
}

amountDisplay.innerText = amount || 0;

paymentForm.addEventListener('submit', async function(e) {
    e.preventDefault();

    try {
        const response = await fetch(`/api/paiements/reservation/${reservationId}/pay`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            alert(`Paiement de ${amount || 0}€ effectué avec succès !`);
            window.location.href = "mes-reservations.html";
        } else {
            const errorText = await response.text();
            console.error("Erreur paiement :", response.status, errorText);
            alert("Erreur lors du paiement.");
        }
    } catch (error) {
        console.error("Erreur réseau :", error);
        alert("Impossible de joindre le serveur.");
    }
});