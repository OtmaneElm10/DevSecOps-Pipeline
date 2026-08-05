import axios from 'axios';

const api = axios.create({
    // J'ai fais des tests en local donc verifier avec les autres si c'est la bonne adresse 
    // TODO: Mettre IP de la VM plus tard
    baseURL: '/api',
    headers: {
        'Content-Type': 'application/json'
    }
});

export default api;
