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
            // Ici on fait le lien avec le registerDTO
            body: JSON.stringify({ username, email, password })
        });

        if (!response.ok) {
            throw new Error("Erreur lors de l'inscription");
        }

        return await response.json(); 
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
            // Ici on fait le lien avec le loginDTO
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) {
            throw new Error("Identifiants incorrects ou utilisateur introuvable");
        }

        return await response.json(); 
    } catch (error) {
        console.error("Erreur Login:", error);
        throw error;
    }
};