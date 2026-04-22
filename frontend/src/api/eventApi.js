import api from './axiosConfig';

export const getAllEvents = async () => {
    try {
        const response = await api.get('/events');
        return response.data; 
    } catch (error) {
        console.error("Erreur lors du fetch des events :", error);
        throw error;
    }
};

// Récupérer un événement par son ID
export const getEventById = async (id) => {
    try {
        const response = await api.get(`/events/${id}`);
        return response.data;
    } catch (error) {
        console.error(`Erreur fetch event ${id} :`, error);
        throw error;
    }
};

// Créer un nouvel événement (POST)
export const createEvent = async (eventData) => {
    try {
        const response = await api.post('/events', eventData);
        return response.data;
    } catch (error) {
        console.error("Erreur création event :", error);
        throw error;
    }
};