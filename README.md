# G17 — Application de gestion d'événements participatifs

Application web de gestion de tournois et d'événements pour un club sportif.

---

## Outils et framework

### Back-End
| Outil | Rôle |
|---|---|
| Java | Langage principal |
| Spring Boot | Framework applicatif et création des API REST |
| Maven | Gestion des dépendances et build |
| JUnit + Mockito | Tests unitaires |

### Front-End
| Outil | Rôle |
|---|---|
| HTML / CSS / JavaScript | Architecture standard |
| Fetch API | Appels vers le back-end |

### Infrastructure
| Outil | Rôle |
|---|---|
| GitLab CI | Pipeline CI/CD |
| SonarQube | Analyse qualité du code |
| Nginx | Reverse proxy sur la VM |
| MySQL | Base de données de production |

---

## Guide : Lancer le projet en local

### 1. Cloner le dépôt

```bash
git clone https://gitlab.com/g17/projettransversal2025-mif10.git
cd projettransversal2025-mif10
```

### 2. Lancer le back-end

```bash
cd backend
mvn spring-boot:run
```

### 3. Lancer le front-end

Vérifiez que le port **3000 est libre** sur votre machine avant de lancer.

```bash
cd frontend/public
python -m http.server 3000
```
Le front-end est disponible sur `http://localhost:3000`.

### 4. Tester l'application complète

Les deux commandes doivent tourner en parallèle dans deux terminaux distincts.

---

## Lien pour la VM de démo

L'application est déployée et accessible à l'adresse suivante :

```
http://192.168.75.61
```
