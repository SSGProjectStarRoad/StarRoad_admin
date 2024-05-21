# Use the official OpenJDK base image
FROM openjdk:11-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container at /app
COPY target/starroad-admin.jar /app

# Make port 8082 available to the world outside this container
EXPOSE 8082

# Run the JAR file
CMD ["java", "-jar", "starroad-admin.jar"]
