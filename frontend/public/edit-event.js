const token = localStorage.getItem('token');
const role = localStorage.getItem('role');

if (!token || role !== 'ADMIN') {
    alert("Accès refusé. Vous devez être administrateur.");
    window.location.href = 'index.html';
}

const urlParams = new URLSearchParams(window.location.search);
const eventId = urlParams.get('id');

if (!eventId) {
    alert("Aucun événement spécifié !");
    window.location.href = "index.html";
}

const eventTypeSelect = document.getElementById('eventType');

eventTypeSelect.addEventListener('change', function () {
    const selectedType = this.value;
    const allDynamicSections = document.querySelectorAll('.dynamic-section');

    allDynamicSections.forEach(section => {
        section.style.display = 'none';
    });

    if (selectedType === 'MATCH') {
        document.getElementById('dynamic-match').style.display = 'block';
    } else if (selectedType === 'TOURNOI') {
        document.getElementById('dynamic-tournoi').style.display = 'block';
    }
});

async function loadEventData() {
    try {
        const response = await fetch(`/api/events/${eventId}`);

        if (response.ok) {
            const event = await response.json();

            document.getElementById('title').value = event.title || '';
            document.getElementById('description').value = event.description || '';
            document.getElementById('lieu').value = event.lieu || '';
            document.getElementById('dateDebut').value = event.dateDebut || '';
            document.getElementById('dateFin').value = event.dateFin || '';
            document.getElementById('capaciteMax').value = event.capaciteMax || '';
            document.getElementById('prix').value = event.prix || 0;

            if (event.type) {
                eventTypeSelect.value = event.type;
                eventTypeSelect.dispatchEvent(new Event('change'));
            }
        } else {
            alert("Impossible de charger l'événement.");
            window.location.href = "index.html";
        }
    } catch (error) {
        console.error("Erreur lors du chargement :", error);
        alert("Erreur de connexion au serveur.");
    }
}

loadEventData();

document.getElementById('edit-event-form').addEventListener('submit', async function (e) {
    e.preventDefault();

    const type = document.getElementById('eventType').value;

    const updatedEvent = {
        title: document.getElementById('title').value,
        description: document.getElementById('description').value,
        lieu: document.getElementById('lieu').value,
        dateDebut: document.getElementById('dateDebut').value,
        dateFin: document.getElementById('dateFin').value,
        capaciteMax: parseInt(document.getElementById('capaciteMax').value, 10),
        prix: parseFloat(document.getElementById('prix').value),
        type: type
    };

    try {
        const response = await fetch(`/api/events/${eventId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(updatedEvent)
        });

        if (response.ok) {
            alert("Événement modifié avec succès !");
            window.location.href = "index.html";
        } else if (response.status === 401) {
            alert("Vous devez être connecté.");
            window.location.href = "login.html";
        } else if (response.status === 403) {
            alert("Accès refusé. Vous devez être administrateur.");
            window.location.href = "index.html";
        } else {
            let errorMessage = "Erreur du serveur. L'événement n'a pas pu être modifié.";

            try {
                const errorData = await response.json();
                if (errorData.message) {
                    errorMessage = errorData.message;
                }
            } catch (jsonError) {}

            alert(errorMessage);
        }
    } catch (error) {
        console.error("Erreur réseau :", error);
        alert("Impossible de joindre le serveur.");
    }
});