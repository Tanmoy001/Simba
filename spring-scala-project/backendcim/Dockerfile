# Stage 1: Build the entire project
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# Copy the entire project
COPY . .

# Grant execute permissions to the Gradle wrapper
RUN chmod +x ./gradlew

# Build only the backendcim project, which will also build the scala-lib-cim dependency
RUN ./gradlew backendcim:bootJar

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/backendcim/build/libs/*.jar backendcim.jar

# Set the entry point to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "backendcim.jar"]