# Build stage
FROM gradle:8.7-jdk17-alpine AS build
WORKDIR /workspace

# Copy entire project
COPY . .

# Build Scala library first
RUN cd spring-scala-project/scala-lib-cim && \
    gradle clean build publishToMavenLocal

# Build Spring Boot application
RUN cd spring-scala-project/backendcim && \
    gradle clean build -x test

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
ARG JAR_FILE=spring-scala-project/backendcim/build/libs/*.jar
COPY --from=build /workspace/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]