FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

RUN apk add --no-cache maven

RUN mvn clean package -DskipTests

CMD ["sh", "-c", "java -jar target/*.jar"]
