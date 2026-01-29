
FROM eclipse-temurin:25
WORKDIR /app

COPY src ./src
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn/

RUN ./mvnw dependency:go-offline -B
RUN ./mvnw clean package

RUN mv target/*.jar app.jar
RUN rm -rf target src .mvn mvnw pom.xml

ENTRYPOINT ["java", "-jar", "app.jar"]

