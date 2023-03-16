FROM openjdk:17
WORKDIR /parking_app
COPY build/libs/*.jar parking_app.jar
EXPOSE 8080
CMD ["java", "-jar", "parking_app.jar"]
