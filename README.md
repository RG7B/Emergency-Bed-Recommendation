"# Emergency Bed Recommendation System"

Table des matières :

Description
Fonctionnalités
Architecture
Prérequis
Installation
Utilisation
API Endpoints
Tests
Technologies utilisées

Description :

L'Emergency Bed Recommendation System est une application web qui permet de recommander le meilleur hôpital pour un patient en situation d'urgence, en fonction de sa localisation géographique et de la gravité de ses symptômes. Le système utilise un modèle de machine learning pour prédire l'hôpital le plus approprié.

Fonctionnalités :

Recommandation d'hôpital : Prédiction de l'hôpital le plus adapté en fonction de la localisation du patient et de la gravité de ses symptômes.
Interface utilisateur : Une interface web intuitive pour saisir les informations du patient.
API RESTful : Un endpoint API pour l'intégration avec d'autres systèmes.
Modèle de machine learning : Utilisation d'un modèle d'apprentissage automatique pour améliorer les recommandations.
Gestion des erreurs : Gestion robuste des exceptions et affichage de messages d'erreur conviviaux.

Architecture :

L'application suit une architecture MVC (Modèle-Vue-Contrôleur) avec les composants suivants :

Front-end : Utilisation de Thymeleaf pour les templates HTML.
Back-end : Spring Boot pour la logique métier et les services RESTful.
Machine Learning : Weka pour le modèle de prédiction.
Base de données : Utilisation d'un repository en mémoire pour les données des hôpitaux.

Prérequis :

Java 17 ou supérieur
Maven 3.6 ou supérieur
Git (facultatif)
Un IDE (Eclipse, IntelliJ IDEA, etc.)

Installation :

1.Cloner le repository

git clone https://github.com/RG7B/emergency-bed-recommendation.git

2.Accéder au répertoire du projet

cd emergency-bed-recommendation

3.Construire le projet avec Maven

mvn clean install

Utilisation :

1.Démarrer l'application

mvn spring-boot:run
L'application démarre par défaut sur le port 8092.

2.Accéder à l'interface web

Ouvrez votre navigateur et rendez-vous sur :

http://localhost:8092/

3.Utiliser l'application

Saisissez l'ID du patient.
Indiquez la latitude et la longitude du patient.
Décrivez les symptômes du patient.
Cliquez sur Trouver un lit disponible pour obtenir la recommandation.

API Endpoints :

POST /api/v1/recommend-bed

Description : Recommande un hôpital en fonction des données du patient.

Corps de la requête :

{
  "patientId": "12345",
  "latitude": 48.8566,
  "longitude": 2.3522,
  "symptoms": "Douleurs thoraciques"
}

Réponse :

{
  "hospitalId": "1",
  "hospitalName": "Hôpital A"
}

Tests :

Tests unitaires et d'intégration

Exécuter les tests

mvn test
Résultats des tests

Tous les tests doivent passer avec succès.

Tests de performance :

Outils recommandés : JMeter, Gatling.

Objectif : Vérifier la capacité du back-end à gérer une charge élevée.

Technologies utilisées :

Java 17

Spring Boot 3.1.4

Thymeleaf

Weka 3.8.6

Maven

JUnit 5

Mockito
