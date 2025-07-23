FROM openjdk:17-jdk-slim

# Copy the JAR file (adjust name if different)
COPY build/libs/secure-login-app-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8080

# Start the app
CMD ["java", "-jar", "app.jar"]
