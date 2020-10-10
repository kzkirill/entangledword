FROM openjdk:8
 ADD target/*.jar entangled-word-app.jar
 EXPOSE 8080
ENTRYPOINT ["java","-jar","entangled-word-app.jar"]