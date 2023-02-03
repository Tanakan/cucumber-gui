FROM adoptopenjdk/maven-openjdk11
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]