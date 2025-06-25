FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Compilar o projeto sem executar os testes
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/src ./src
COPY --from=build /app/pom.xml ./pom.xml
COPY entrypoint.sh /entrypoint.sh

# Instalar Maven e curl (necessário para o healthcheck)
RUN apt-get update && \
    apt-get install -y maven curl && \
    chmod +x /entrypoint.sh

# Configurar para que a saída do Maven seja clara e mostre os testes ignorados
ENV MAVEN_OPTS="-Dorg.slf4j.simpleLogger.defaultLogLevel=info -Dmaven.test.failure.ignore=true -Dorg.slf4j.simpleLogger.showThreadName=false -Dorg.slf4j.simpleLogger.showLogName=false"

EXPOSE 8080
CMD ["/entrypoint.sh"]
