const params = new URLSearchParams(window.location.search);
const amount = params.get('amount');
const eventId = params.get('id');

document.getElementById('amount-display').innerText = amount;

document.getElementById('payment-form').addEventListener('submit', function(e) {
    e.preventDefault();
    alert(`Paiement de ${amount}€ accepté pour l'événement ${eventId} !`);
    
    // Requete au backend pour confirmer le paiement
    window.location.href = "index.html"; 
});