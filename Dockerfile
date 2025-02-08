# Use a minimal Java runtime for the final image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Add a volume to hold the application data
VOLUME /tmp

# Expose the default Spring Boot port
EXPOSE 8080

# Set active profile to production by default
ENV SPRING_PROFILES_ACTIVE=prod

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Run the application directly using Maven
CMD ["mvn", "spring-boot:run"]
