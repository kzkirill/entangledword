FROM openjdk:8
VOLUME /tmp
WORKDIR /usr/apps/entangled-word-app
ADD target/entangled-word-webapp-backend-0.0.1-SNAPSHOT.jar entangled-word-app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","entangled-word-app.jar"]