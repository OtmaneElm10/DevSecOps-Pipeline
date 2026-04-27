const params = new URLSearchParams(window.location.search);
const eventId = params.get('eventId');

const token = localStorage.getItem('token');
const userId = localStorage.getItem('userId');

const container = document.getElementById('reservation-container');
const form = document.getElementById('reservation-form');
const quantityInput = document.getElementById('quantity');
const totalPriceEl = document.getElementById('total-price');

let eventData = null;

if (!token || !userId) {
    alert("Vous devez être connecté pour réserver.");
    window.location.href = "login.html";
}

if (!eventId) {
    container.innerHTML = "<p>Aucun événement sélectionné.</p>";
    form.style.display = "none";
} else {
    fetch(`/api/events/${eventId}`)
        .then(res => {
            if (!res.ok) {
                throw new Error("Événement introuvable");
            }
            return res.json();
        })
        .then(event => {
            eventData = event;

            container.innerHTML = `
                <h3>${event.title}</h3>
                <p>${event.description || ''}</p>
                <p><strong>Prix unitaire :</strong> ${event.prix} €</p>
            `;

            form.style.display = "block";
            updatePrice();
        })
        .catch(error => {
            console.error(error);
            container.innerHTML = "<p>Erreur lors du chargement de l'événement.</p>";
            form.style.display = "none";
        });
}

function updatePrice() {
    if (!eventData) {
        return;
    }

    const quantity = parseInt(quantityInput.value, 10) || 1;
    const total = quantity * (eventData.prix || 0);

    totalPriceEl.textContent = `Total : ${total} €`;
}

quantityInput.addEventListener('input', updatePrice);

form.addEventListener('submit', async function (e) {
    e.preventDefault();

    const quantity = parseInt(quantityInput.value, 10);

    if (!quantity || quantity <= 0) {
        alert("Le nombre de places doit être supérieur à 0.");
        return;
    }

    const reservation = {
        nbPlaces: quantity,
        dateCreation: new Date().toISOString().split('T')[0],
        montantAttendu: quantity * (eventData.prix || 0),
        user: {
            id: parseInt(userId, 10)
        },
        event: {
            id: parseInt(eventId, 10)
        }
    };

    console.log("RESERVATION ENVOYÉE :", reservation);

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
            alert("Réservation créée, en attente de paiement.");
            window.location.href = "index.html";
        } else if (response.status === 401) {
            alert("Vous devez être connecté.");
            window.location.href = "login.html";
        } else if (response.status === 403) {
            alert("Accès refusé.");
        } else {
            const errorText = await response.text();
            console.error("Erreur backend :", response.status, errorText);
            alert("Erreur lors de la réservation.");
        }

    } catch (error) {
        console.error("Erreur réseau :", error);
        alert("Impossible de joindre le serveur.");
    }
});