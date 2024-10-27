# Utiliser une image Java 17 JDK pour construire l'application
FROM maven:3.8.6-openjdk-17 as builder

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier pom.xml et télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier tout le code source
COPY src ./src

# Compiler et packager l'application
RUN mvn clean package -DskipTests

# Image finale pour exécuter l'application
FROM openjdk:17-jdk-slim

# Créer un répertoire pour l'application
WORKDIR /app

# Copier le fichier .jar généré depuis l'étape de construction
COPY --from=builder /app/target/menu-service.jar /app/menu-service.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "/app/menu-service.jar"]
