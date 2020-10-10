FROM openjdk:8
ADD entangled-word-webapp-backend/target/entangled-word-webapp-backend-0.0.1-SNAPSHOT.jar entangled-word-app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","entangled-word-app.jar"]