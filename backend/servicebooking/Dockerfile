# ---------- Stage 1: Build the application ----------
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

RUN apk update && apk add --no-cache bash curl tar git ca-certificates && update-ca-certificates

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Copy the source code
COPY src ./src

COPY mvnw .
RUN chmod +x mvnw
# Package the application
RUN ./mvnw clean package -DskipTests

# ---------- Stage 2: Run the app ----------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/servicebooking-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]