FROM openjdk:8
VOLUME /tmp
WORKDIR /usr/apps/entangled-word-app
COPY entangled-word-webapp-backend/target/*.jar entangled-word-app.jar
ENTRYPOINT ["java","-jar","/usr/apps/entangled-word-app/entangled-word-app.jar"]