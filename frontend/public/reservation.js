const params = new URLSearchParams(window.location.search);
const eventId = params.get('eventId');

const token = localStorage.getItem('token');

const container = document.getElementById('reservation-container');
const form = document.getElementById('reservation-form');
const quantityInput = document.getElementById('quantity');
const totalPriceEl = document.getElementById('total-price');

let eventData = null;

if (!eventId) {
    container.innerHTML = "<p>Aucun événement sélectionné.</p>";
} else {
    fetch(`/api/events/${eventId}`)
        .then(res => res.json())
        .then(event => {
            eventData = event;

            container.innerHTML = `
                <h3>${event.title}</h3>
                <p>Prix unitaire : ${event.prix} €</p>
            `;

            form.style.display = 'block';
            updatePrice();
        });
}

function updatePrice() {
    const quantity = parseInt(quantityInput.value, 10);
    const total = quantity * (eventData.prix || 0);
    totalPriceEl.textContent = `Total : ${total} €`;
}

quantityInput.addEventListener('input', updatePrice);

form.addEventListener('submit', async function (e) {
    e.preventDefault();

    const quantity = parseInt(quantityInput.value, 10);

    const reservation = {
        eventId: eventId,
        quantity: quantity
    };

    try {
        const response = await fetch('/api/reservations', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(reservation)
        });

        if (response.ok) {
            alert("Réservation créée !");
            window.location.href = "index.html";
        } else {
            alert("Erreur lors de la réservation");
        }

    } catch (error) {
        console.error(error);
        alert("Erreur réseau");
    }
});