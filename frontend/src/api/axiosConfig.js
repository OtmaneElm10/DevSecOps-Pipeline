import axios from 'axios';

const api = axios.create({
    // J'ai fais des tests en local donc verifier avec les autres si c'est la bonne adresse 
    // TODO: Mettre IP de la VM plus tard
    baseURL: 'http://192.168.75.61/api', 
    headers: {
        'Content-Type': 'application/json'
    }
});

export default api;