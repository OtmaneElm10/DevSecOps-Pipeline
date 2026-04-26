const API_BASE_URL = "http://localhost:8080/api/auth";

/**
 * Envoie les données d'inscription au Backend
 */
export const register = async (username, email, password) => {
    try {
        const response = await fetch(`${API_BASE_URL}/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, email, password })
        });

        if (!response.ok) {
            throw new Error("Erreur lors de l'inscription");
        }

        const data = await response.json();

        localStorage.setItem("token", data.token);
        localStorage.setItem("userId", data.id);
        localStorage.setItem("username", data.username);
        localStorage.setItem("email", data.email);
        localStorage.setItem("role", data.role);

        return data;
    } catch (error) {
        console.error("Erreur Register:", error);
        throw error;
    }
};

/**
 * Envoie les identifiants de connexion au Backend
 */
export const login = async (username, password) => {
    try {
        const response = await fetch(`${API_BASE_URL}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) {
            throw new Error("Identifiants incorrects ou utilisateur introuvable");
        }

        const data = await response.json();

        localStorage.setItem("token", data.token);
        localStorage.setItem("userId", data.id);
        localStorage.setItem("username", data.username);
        localStorage.setItem("email", data.email);
        localStorage.setItem("role", data.role);

        return data;
    } catch (error) {
        console.error("Erreur Login:", error);
        throw error;
    }
};