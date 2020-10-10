FROM openjdk:8
ADD entangled-word-webapp-backend/target/entangled-word.jar entangled-word.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","entangled-word.jar"]