# OO-PetShop

Sistema de Pet Shop utilizando Spring Boot, seguindo arquitetura MVC/Clean Architecture como discutido em sala de aula com o professor.

## Documentação

- [Diagrama UML](./docs/uml/diagrama.md)
- [Backlog e Histórias de Usuário](./docs/backlog.md)

## Tecnologias Utilizadas

- **Backend**: Spring Boot
- **Banco de Dados**: MySQL
- **Documentação**: Thymeleaf (HTML)
- **Containerização**: Docker
- **Testes**: JUnit 5

## Testes

O projeto inclui testes unitários para os componentes principais, todos marcados com a anotação `@Disabled` para o Ponto de Controle 1, conforme solicitado. Os testes serão implementados nas próximas etapas do desenvolvimento.

### Estrutura de Testes
- `HelloWorldControllerTest`: Testes para o controlador básico

## Qualidade de Código

O projeto utiliza ferramentas de análise estática de código (linters) para garantir a qualidade e consistência do código:

### Checkstyle
Verifica se o código segue padrões de estilo consistentes.

Para executar a verificação:
```bash
mvn checkstyle:check
```

### PMD
Analisa o código para identificar possíveis problemas, como código duplicado, complexidade excessiva e boas práticas.

Para executar a análise:
```bash
mvn pmd:check
```

As configurações personalizadas para estas ferramentas estão nos arquivos `checkstyle.xml` e `pmd-ruleset.xml` na pasta do backend.

## Como Executar

1. Clone o repositório
2. Execute o comando:
docker-compose up -d


3. Acesse:
   - Backend API: http://localhost:8080
   - Documentação do Projeto: http://localhost:8080/docs
   - Backlog do Projeto: http://localhost:8080/docs/backlog
   - Diagrama UML: http://localhost:8080/docs/uml
   - PHPMyAdmin (Gerenciamento do Banco): http://localhost:8081

## Padrão de Commits

Este projeto segue o padrão de commits conforme detalhado em [docs/commit-pattern.md](./docs/commit-pattern.md):

- `feat(docs <nome doc>)`: Adição de documentação
- `fix(docs)`: Correção na documentação
- `feat(tests)`: Adição de testes
- `feat(infra)`: Configuração de infraestrutura
