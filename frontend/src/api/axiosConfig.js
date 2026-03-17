import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api', // L'adresse de ton Spring Boot
    headers: {
        'Content-Type': 'application/json'
    }
});

export default api;