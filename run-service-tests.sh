#!/bin/bash

# Script para executar testes específicos de serviço no Docker Compose

echo "Iniciando execução dos testes de serviço..."

# Executa o container de testes com comando específico para os testes de serviço
docker-compose run --rm backend-tests ./mvnw test -Dtest=MedicamentoServiceImplTest,ConsultaServiceImplTest,ExameServiceImplTest

echo "Testes concluídos!"
