# Dockerfile pour le service Gestion des Tables

FROM  openjdk:17-slim
VOLUME /tmp
COPY target/tabbles-service.jar tables-service.jar
LABEL authors="COULIBALY WAOPRON"
ENTRYPOINT ["java", "-jar","/tables-service.jar"]