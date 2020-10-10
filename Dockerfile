FROM openjdk:8
COPY build/libs/entangled-word-webapp-backend-0.0.1-SNAPSHOT.jar /bin
VOLUME /bin
EXPOSE 8080
ENTRYPOINT ["java","-jar","./bin/entangled-word-app.jar"]