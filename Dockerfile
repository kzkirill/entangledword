FROM openjdk:8
ADD target/entangled-word-webapp-backend-0.0.1-SNAPSHOT.jar entangled-word-app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","entangled-word-app.jar"]