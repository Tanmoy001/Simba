# Build stage
FROM gradle:7.6.1-jdk17 AS build
WORKDIR /app

# Copy entire project including both subprojects
COPY . .

# Build the application and skip tests
RUN gradle :backendcim:build -x test

# Runtime stage
FROM eclipse-temurin:17-jre-jammy

# Set working directory
WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/backendcim/build/libs/backendcim-1.0-SNAPSHOT.jar /app/application.jar

# Expose port
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "/app/application.jar"]