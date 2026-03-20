const eventList = document.getElementById('event-list');

fetch('http://localhost:8081/api/events')
    .then(res => res.json())
    .then(event => {
        if (event.length === 0) {
            eventList.innerHTML = "<p>Aucun événement pour le moment.</p>";
            return;
        }

        eventList.innerHTML = event.map(event => `
            <div class="card">
                <div class="card-content">
                    <h2>${event.title}</h2>
                    <p>${event.description}</p>
                    <p><strong>Lieu :</strong> ${event.lieu}</p>
                </div>
                <button class="btn-register">S'inscrire</button>
            </div>
        `).join('');
    })
    .catch(err => console.error("Erreur back:", err));