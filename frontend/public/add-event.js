
document.getElementById('event-form').addEventListener('submit', function(e) {
    e.preventDefault();

    const newEvent = {
        title: document.getElementById('title').value,
        description: document.getElementById('description').value,
        lieu: document.getElementById('lieu').value,
        prix: parseFloat(document.getElementById('prix').value),
        capaciteMax: parseInt(document.getElementById('capaciteMax').value),
        dateDebut: document.getElementById('dateDebut').value,
        dateFin: document.getElementById('dateFin').value
    };

    console.log("Données à envoyer au back :", newEvent);
    alert("Événement prêt à être envoyé au Backend !");
            
    // fetch('http://localhost:8080/api/events', { method: 'POST', ... })
});