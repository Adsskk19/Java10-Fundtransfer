# -------- Runtime image --------
FROM eclipse-temurin:17-jre-alpine

# App directory
WORKDIR /app

# Copy the fat jar (finalName must match)
COPY target/*.jar fundtransfer.jar

# Expose port (actual port is controlled by SERVER_PORT)
EXPOSE 8080

# Run Spring Boot
ENTRYPOINT ["java","-jar","/app/fundtransfer.jar"]
