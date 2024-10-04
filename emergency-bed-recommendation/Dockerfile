# Dockerfile
# Utiliser une image Java 17
FROM eclipse-temurin:17-jdk-alpine

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR généré dans le conteneur
COPY target/emergency-bed-recommendation-1.0.0.jar app.jar

# Exposer le port de l'application
EXPOSE 8085

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
