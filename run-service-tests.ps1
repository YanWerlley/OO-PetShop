# Script PowerShell para executar testes específicos de serviço no Docker Compose

Write-Host "Iniciando execução dos testes de serviço..." -ForegroundColor Green

# Executa o container de testes com comando específico para os testes de serviço
docker-compose run --rm backend-tests ./mvnw test -Dtest=MedicamentoServiceImplTest,ConsultaServiceImplTest,ExameServiceImplTest

Write-Host "Testes concluídos!" -ForegroundColor Green
