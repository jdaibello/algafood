FROM --platform=linux/amd64 eclipse-temurin:17.0.8_7-jre

WORKDIR /app

COPY target/*.jar /app/api.jar
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]