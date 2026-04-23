import { getAllEvents } from './api/eventApi';

const displayEvents = async () => {
    const appDiv = document.getElementById('app'); 
    
    try {
        const events = await getAllEvents();
        
        if (events.length === 0) {
            appDiv.innerHTML = "<p>Aucun événement trouvé. La base de données est vide !</p>";
            return;
        }

        appDiv.innerHTML = events.map(event => `
            <div class="event-card">
                <h2>${event.title}</h2>
                <p>${event.lieu}</p>
                <p>${event.prix} €</p>
            </div>
        `).join('');

    } catch (error) {
        appDiv.innerHTML = "<p style='color:red'>Erreur de connexion au serveur.</p>";
    }
};

displayEvents();