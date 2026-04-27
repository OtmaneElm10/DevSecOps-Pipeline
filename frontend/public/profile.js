const container = document.getElementById('profile-container');

const token = localStorage.getItem('token');
const userId = localStorage.getItem('userId');
const username = localStorage.getItem('username');
const email = localStorage.getItem('email');
const role = localStorage.getItem('role');

// Vérification connexion
if (!token) {
    alert("Vous devez être connecté.");
    window.location.href = "login.html";
}

// Avatar dynamique
const avatar = document.getElementById('avatar');
avatar.src = `https://ui-avatars.com/api/?name=${username}&background=0D8ABC&color=fff&size=128`;

// Badge rôle (petit bonus visuel)
const roleColor = role === 'ADMIN' ? '#e74c3c' : '#3498db';

// Affichage des infos
container.innerHTML = `
    <p><strong>ID :</strong> ${userId}</p>
    <p><strong>Nom d'utilisateur :</strong> ${username}</p>
    <p><strong>Email :</strong> ${email}</p>
    <p>
        <strong>Rôle :</strong> 
        <span style="color:white; background:${roleColor}; padding:4px 8px; border-radius:5px;">
            ${role}
        </span>
    </p>
`;

// Déconnexion
document.getElementById('logout-btn').addEventListener('click', () => {
    localStorage.clear();
    alert("Déconnexion réussie");
    window.location.href = "index.html";
});