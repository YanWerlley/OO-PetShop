#!/bin/bash
set -e

echo "Iniciando execução dos testes..."

# Executar os testes com geração de relatório de cobertura
echo "Executando testes unitários e de integração..."
mvn clean test jacoco:report

# Verificar se os testes passaram
TEST_EXIT_CODE=$?

if [ $TEST_EXIT_CODE -eq 0 ]; then
  echo "✅ Todos os testes foram executados com sucesso!"
else
  echo "❌ Alguns testes falharam. Verifique o relatório para mais detalhes."
fi

echo "Relatório de cobertura gerado em: /app/target/site/jacoco/index.html"

# Manter o container rodando para inspeção
echo "Testes concluídos. O container continuará em execução para inspeção dos resultados."
echo "Para sair, pressione Ctrl+C"
tail -f /dev/null
