# TP — Création d'une API REST : Gestion d'inscriptions à une course

## Objectif

L'objectif de ce TP est de concevoir et développer une **API REST** permettant de gérer l'inscription de coureurs à différentes courses.

Cette API devra permettre :

* de gérer les **coureurs**
* de gérer les **courses**
* de gérer les **inscriptions à une course**

Les données devront être **persistées dans une base de données PostgreSQL**.

# Cloner le projet

Clonez le projet : 

* Https:
  ```GitBash
  git clone https://github.com/Daphneej91/race-api.git
  ```
ou
* SSH:
  ```GitBash
  git clone git@github.com:Daphneej91/race-api.git
  ```
  
# Lancer le projet

## 1 — Démarrer la base de données

Pour lancer votre base de données SQL et Adminer :

```bash
docker compose up -d
```

(vous devez avoir lancé Docker Desktop au préalable si vous êtes sur Windows)

---

## 2 — Accéder à Adminer

Adminer permet de visualiser la base de données.

URL :

```
http://localhost:8081
```

Paramètres de connexion :

| Champ    | Valeur        |
| -------- |---------------|
| System   | PostgreSQL    |
| Server   | race_postgres |
| Username | race          |
| Password | race          |
| Database | race_db       |

---

## 3 — Lancer l'application

Lancer votre configuration directement sur IntelliJ.

Sinon, depuis votre IDE ou en ligne de commande :

```bash
mvn spring-boot:run
```

L'API sera disponible sur :

```
http://localhost:8080
```

---
### 4 — Test
Lancer Postman et aller en haut à gauche dans les trois petits points. Puis cliquer sur Import et copier coller le fichier postman-export.json present dans le dossier Race-Api.

Vous pouvez ensuite tester les endpoints que vous voulez.

---

# Endpoints implémentés

## Gestion des coureurs

### Lister les coureurs
Retourne la liste de tous les coureurs.
```
GET /runners
```


### Récupérer un coureur

Retourne un coureur spécifique.
```
GET /runners/{id}
```

Réponse possible :
* 200 OK
* 404 Not Found

### Supprimer un coureur

```
DELETE /runners/{id}
```
Réponses :
* 200 OK
* 404 Not Found

### Créer un coureur

```
POST /runners
```

Body :

```json
{
  "firstName": "Alice",
  "lastName": "Martin",
  "email": "alice.martin@example.com",
  "age": 30
}
```

Réponse :
* 201 Created
Si l'email est invalide :
* 400 Bad Request


### Modifier un coureur

```
PUT /runners/{id}
```

Body :

```json
{
  "firstName": "Alice",
  "lastName": "Martin",
  "email": "alice.martin@example.com",
  "age": 31
}
```

Réponses :

* 201 Created
* 404 Not Found

---

# Gestion des courses

### Lister les courses

```
GET /races
```

---

### Récupérer une course

```
GET /races/{id}
```
Réponses :
* 200 OK
* 404 Not Found

### Créer une course

```
POST /races
```

Body :

```json
{
  "name": "Semi-marathon de Paris",
  "date": "2026-06-01",
  "location": "Paris",
  "maxParticipants": 500
}
```
Réponse :
* 201 Created

### Compter le nombre de participants d'une course

GET /races/{raceId}/participants/count

Réponse :

```json
{
  "count": 42
}
```

Si la course n'existe pas :
* 404 Not Found

---

# Gestion des inscriptions

### Inscrire un coureur à une course

```
POST /races/{raceId}/registrations
```

Body :

```json
{
  "runnerId": 1
}
```

Réponse :

* 201 Created
* 404 Not Found
* 409 Conflict

Conflit possible: 
* le coureur est déjà inscrit
* la course est complète

### Lister les participants d'une course

Retourne tous les coureurs inscrits à une course.
```
GET /races/{raceId}/registrations
```

### Lister les courses d'un coureur

Retourne toutes les courses auxquelles un coureur est inscrit.

```
GET /runners/{runnerId}/races
```


# Bonus implémenté
## Filtrer les courses par localisation

Permet de récupérer uniquement les courses ayant lieu dans une ville donnée.
```
GET /races?location=Paris
```

# Codes HTTP attendus

| Code | Signification         |
| ---- | --------------------- |
| 200  | Succès                |
| 201  | Ressource créée       |
| 400  | Requête invalide      |
| 404  | Ressource non trouvée |
| 409  | Conflit               |

---
# Règles métier implémentées

Un coureur ne peut pas être inscrit deux fois à la même course → 409 Conflict

Une course ne peut pas dépasser son nombre maximum de participants → 409 Conflict

Les emails doivent être valides (contiennent @) → 400 Bad Request

Les ressources doivent exister → 404 Not Found


