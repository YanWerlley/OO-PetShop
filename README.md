# OO-PetShop

Sistema de Pet Shop utilizando Spring Boot e React, seguindo arquitetura MVC/Clean Architecture como discutido em sala de aula com o professor.

## Documentação

- [Diagrama UML](./docs/uml/diagrama.md)
- [Modelo Conceitual do Banco](./docs/banco/modelo_conceitual.md)
- [Modelo Físico do Banco](./docs/banco/modelo_fisico.sql)
- [Backlog e Histórias de Usuário](./docs/backlog.md)

## Estrutura do Projeto

```
OO-PetShop/
├── backend/           # Backend Spring Boot
│   ├── src/           # Código fonte do backend
│   └── pom.xml        # Dependências Maven
├── frontend/          # Frontend React
│   ├── public/        # Arquivos públicos
│   ├── src/           # Código fonte do frontend
│   └── package.json   # Dependências NPM
└── docs/              # Documentação do projeto
```

## Tecnologias Utilizadas

### Backend
- **Framework**: Spring Boot 3.2.0
- **Banco de Dados**: H2 (dev), PostgreSQL (prod)
- **Validação**: Jakarta Validation
- **Documentação API**: OpenAPI/Swagger
- **Mapeamento**: MapStruct
- **Testes**: JUnit 5, Mockito

### Frontend
- **Framework**: React 18
- **Roteamento**: React Router 6
- **UI Components**: React Bootstrap
- **HTTP Client**: Axios
- **Testes**: Jest, React Testing Library

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

### Método 1: Script de Inicialização

1. Clone o repositório
2. Execute o script `run-app.bat` na raiz do projeto

### Método 2: Execução Manual

1. Clone o repositório

2. Inicie o backend:
   ```bash
   cd backend
   mvn spring-boot:run
   ```

3. Inicie o frontend:
   ```bash
   cd frontend
   npm install
   npm start
   ```

4. Acesse:
   - Backend API: http://localhost:8080
   - Frontend: http://localhost:3000
   - API Docs (Swagger): http://localhost:8080/swagger-ui/index.html

## Padrão de Commits

Este projeto segue o padrão de commits conforme detalhado em [docs/commit-pattern.md](./docs/commit-pattern.md):

- `feat(docs <nome doc>)`: Adição de documentação
- `fix(docs)`: Correção na documentação
- `feat(tests)`: Adição de testes
- `feat(infra)`: Configuração de infraestrutura
