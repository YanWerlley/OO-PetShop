#!/bin/bash
set -e

# Esperar o MySQL iniciar
echo "Aguardando o MySQL iniciar..."
sleep 10

# Executar os testes (incluindo os ignorados)
mvn test -Dtest=HelloWorldControllerTest -Dmaven.test.failure.ignore=true

# Iniciar a aplicação
echo "Iniciando a aplicação Spring Boot..."
java -jar app.jar
