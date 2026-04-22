const API_BASE_URL = "http://localhost:8080/api/users";

/**
 * Récupère les données de profil d'un utilisateur
 */
export const getUserProfile = async (username) => {
    try {
        const response = await fetch(`${API_BASE_URL}/${username}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error("Utilisateur introuvable");
        }

        return await response.json();
    } catch (error) {
        console.error("Erreur getUserProfile:", error);
        throw error;
    }
};
