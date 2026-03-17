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