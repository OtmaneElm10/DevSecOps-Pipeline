
const eventList = document.getElementById('event-list');

fetch('/api/events')
    .then(res => res.json())
    .then(events => {
        if (events.length === 0) {
            eventList.innerHTML = "<p>Aucun événement pour le moment.</p>";
            return;
        }

        eventList.innerHTML = events.map(event => `
            <div class="card" data-id="${event.id}">
                <div class="card-content">
                    <h2>${event.title}</h2>
                    <p>${event.description}</p>
                    <p><strong>Lieu :</strong> ${event.lieu}</p>
                </div>
                <button class="btn-register">S'inscrire</button>
            </div>
        `).join('');

        // Clic sur une carte => page détails
        document.querySelectorAll('.card').forEach(card => {
            card.addEventListener('click', () => {
                const id = card.dataset.id;
                window.location.href = `details.html?id=${id}`;
            });
        });
    })
    .catch(err => console.error("Erreur back:", err));

// Affichage du bouton "Create Event" si admin
document.addEventListener('DOMContentLoaded', () => {
    const userRole = localStorage.getItem('role');

    if (userRole === 'ADMIN') {
        const createBtn = document.getElementById('btn-create-event');
        if (createBtn) {
            createBtn.style.display = 'block';
        }
    }
});
