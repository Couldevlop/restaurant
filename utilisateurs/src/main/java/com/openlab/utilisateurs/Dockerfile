

# Dockerfile pour le service utilisateur

FROM openjdk:17-jdk-slim:latest
VOLUME /tmp
LABEL authors="ADMIN"
COPY target/utilisateurs.jar utilisateurs.jar

ENTRYPOINT ["java", "-jar","/utilisateurs.jar"]