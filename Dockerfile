# Use a base image with Java and Maven installed
FROM maven:3.8.4-openjdk-17-slim AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project file
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Use a lightweight base image with JRE only
FROM openjdk:17-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file built in the previous stage
COPY --from=builder /app/target/bacxiu-cafe.jar .

# Expose the port your app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "bacxiu-cafe.jar"]
