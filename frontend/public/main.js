const eventList = document.getElementById('event-list');
const token = localStorage.getItem('token');
const userRole = localStorage.getItem('role');

function loadEvents(type = '') {
    let url = '/api/events';

    if (type !== '') {
        url += `?type=${type}`;
    }

    fetch(url)
        .then(res => res.json())
        .then(events => {
            if (events.length === 0) {
                eventList.innerHTML = "<p>Aucun événement pour ce filtre.</p>";
                return;
            }

            eventList.innerHTML = events.map(event => {
                const inscrits = event.nbInscrits || 0;
                const max = event.capaciteMax || 1;
                const pourcentage = Math.min((inscrits / max) * 100, 100);

                let adminButtons = '';

                if (userRole === 'ADMIN') {
                    adminButtons = `
                        <div class="admin-actions">
                            <button class="btn-admin btn-edit"
                                onclick="event.stopPropagation(); window.location.href='edit-event.html?id=${event.id}'">
                                Modifier
                            </button>

                            <button class="btn-admin btn-delete"
                                onclick="event.stopPropagation(); deleteEvent(${event.id})">
                                Supprimer
                            </button>
                        </div>
                    `;
                }

                return `
                    <div class="card" data-id="${event.id}">
                        <div class="card-content">
                            <h2>${event.title}</h2>
                            <p>${event.description || ''}</p>
                            <p><strong>Lieu :</strong> ${event.lieu}</p>

                            <div class="progress-section">
                                <div class="participant-label">
                                    <span>Participants</span>
                                    <span>${inscrits} / ${max}</span>
                                </div>
                                <div class="progress-container">
                                    <div class="progress-bar" style="width: ${pourcentage}%"></div>
                                </div>
                            </div>
                        </div>

                        <button class="btn-register"
                            onclick="event.stopPropagation(); window.location.href='details.html?id=${event.id}'">
                            S'inscrire
                        </button>

                        ${adminButtons}
                    </div>
                `;
            }).join('');

            document.querySelectorAll('.card').forEach(card => {
                card.addEventListener('click', () => {
                    const id = card.dataset.id;
                    window.location.href = `details.html?id=${id}`;
                });
            });
        })
        .catch(err => console.error("Erreur back:", err));
}

loadEvents();

document.querySelectorAll('.filter-btn').forEach(btn => {
    btn.addEventListener('click', e => {
        document.querySelectorAll('.filter-btn').forEach(b => {
            b.classList.remove('active');
        });

        e.target.classList.add('active');

        const type = e.target.dataset.type;
        loadEvents(type);
    });
});

function deleteEvent(eventId) {
    if (!confirm("Êtes-vous sûr de vouloir supprimer cet événement ?")) {
        return;
    }

    const token = localStorage.getItem('token');

    fetch(`/api/events/${eventId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                alert("Événement supprimé avec succès !");
                loadEvents();
            } else {
                alert("Action refusée : Vous n'avez pas les droits ou votre session a expiré.");
            }
        })
        .catch(error => {
            console.error("Erreur:", error);
        });
}

document.addEventListener('DOMContentLoaded', () => {
    const createBtn = document.getElementById('btn-create-event');

    if (createBtn && userRole === 'ADMIN') {
        createBtn.style.display = 'block';
    }

    const loginLink = document.getElementById('link-login');
    const signupLink = document.getElementById('link-signup');
    const logoutBtn = document.getElementById('btn-logout');

    if (token) {
        if (loginLink) loginLink.style.display = 'none';
        if (signupLink) signupLink.style.display = 'none';
        if (logoutBtn) logoutBtn.style.display = 'inline-block';
    } else {
        if (loginLink) loginLink.style.display = 'inline-block';
        if (signupLink) signupLink.style.display = 'inline-block';
        if (logoutBtn) logoutBtn.style.display = 'none';
    }
});

window.logout = function() {
    if (!confirm("Voulez-vous vraiment vous déconnecter ?")) return;

    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('userId');
    localStorage.removeItem('username');
    localStorage.removeItem('email');

    window.location.reload();
};