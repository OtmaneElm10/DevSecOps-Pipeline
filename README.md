# G17 app

Application web de gestion d'événements participatifs.

## Stack technique
- **Back-end** : Java Spring Boot
- **Front-end** : JavaScript (Vanilla JS)
- **Base de données** : MySQL / PostgreSQL
- **Déploiement** : VM Linux + Nginx

## Pour lancer le projet

### Back-end
```bash
cd backend
mvn spring-boot:run
```

### Front-end

**vérifiez que le port 3000 est bien libre sur votre ordi!!!!**


```bash
cd frontend
python -m http.server 3000
```

**Pour avoir acces à la page front il suffit de cliquer sur /public ou directement vous placez dans le repertoire public avant d'utiliser la commande python**

**Pour tester avec le backend il faut lancer spring-boot sur un terminal et python sur un autre en simultané et normalement les evenements apparaissent**


## Déploiement

- **A voir**
