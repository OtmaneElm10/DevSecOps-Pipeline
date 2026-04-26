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

document.getElementById('create-event-form').addEventListener('submit', async function (e) {
    e.preventDefault();

    const type = document.getElementById('eventType').value;

    const newEvent = {
        title: document.getElementById('title').value,
        description: document.getElementById('description').value,
        lieu: document.getElementById('lieu').value,
        dateDebut: document.getElementById('dateDebut').value,
        dateFin: document.getElementById('dateFin').value,
        capaciteMax: parseInt(document.getElementById('capaciteMax').value, 10),
        prix: parseFloat(document.getElementById('prix').value),
        type: type
    };

    console.log("Données prêtes à être envoyées :", newEvent);

    try {
        const response = await fetch('/api/events', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newEvent)
        });

        if (response.ok) {
            alert("Événement créé avec succès !");
            window.location.href = "index.html";
        } else {
            let errorMessage = "Erreur du serveur. L'événement n'a pas pu être créé.";

            try {
                const errorData = await response.json();
                if (errorData.message) {
                    errorMessage = errorData.message;
                }
            } catch (jsonError) {
                // Pas de JSON exploitable, on garde le message générique
            }

            alert(errorMessage);
        }
    } catch (error) {
        console.error("Erreur réseau :", error);
        alert("Impossible de joindre le serveur.");
    }
});