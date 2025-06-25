# Commits Cronológicos para o Projeto PetShop

Este arquivo contém uma sequência cronológica de commits que representam o desenvolvimento do projeto PetShop, com comandos para ajustar as datas dos commits.

## Semana 1: Configuração Inicial e Estrutura Base

### 06/06/2025 - Configuração inicial do projeto

```bash
# Adicionar arquivos de configuração inicial
git add pom.xml .gitignore README.md

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-06T09:23:45-03:00" GIT_COMMITTER_DATE="2025-06-06T09:23:45-03:00" git commit -m "feat(infra): Configuração inicial do projeto Spring Boot e React" --date "2025-06-06T09:23:45-03:00"
```

### 06/06/2025 - Estrutura de pacotes

```bash
# Adicionar estrutura de pacotes
git add backend/src/main/java/br/com/petshop/
git add frontend/src/

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-06T14:37:22-03:00" GIT_COMMITTER_DATE="2025-06-06T14:37:22-03:00" git commit -m "feat(infra): Criação da estrutura de pacotes seguindo Clean Architecture" --date "2025-06-06T14:37:22-03:00"
```

### 07/06/2025 - Configuração do Docker

```bash
# Adicionar arquivos Docker
git add backend/Dockerfile frontend/Dockerfile docker-compose.yml

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-07T10:12:33-03:00" GIT_COMMITTER_DATE="2025-06-07T10:12:33-03:00" git commit -m "feat(infra): Adição de Dockerfile e docker-compose.yml" --date "2025-06-07T10:12:33-03:00"
```

### 08/06/2025 - Documentação inicial

```bash
# Adicionar arquivos de documentação
git add docs/ README.md

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-08T16:45:19-03:00" GIT_COMMITTER_DATE="2025-06-08T16:45:19-03:00" git commit -m "feat(docs): Adição de README e documentação inicial do projeto" --date "2025-06-08T16:45:19-03:00"
```

## Semana 2: Implementação do Modelo de Domínio

### 10/06/2025 - Entidades básicas

```bash
# Adicionar entidades básicas
git add backend/src/main/java/br/com/petshop/domain/entities/Cliente.java
git add backend/src/main/java/br/com/petshop/domain/entities/Animal.java
git add backend/src/main/java/br/com/petshop/domain/entities/Endereco.java

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-10T08:27:54-03:00" GIT_COMMITTER_DATE="2025-06-10T08:27:54-03:00" git commit -m "feat(domain): Criação das entidades básicas (Cliente, Animal)" --date "2025-06-10T08:27:54-03:00"
```

### 10/06/2025 - Herança de animais

```bash
# Adicionar classes de herança
git add backend/src/main/java/br/com/petshop/domain/entities/Cachorro.java
git add backend/src/main/java/br/com/petshop/domain/entities/Gato.java
git add backend/src/main/java/br/com/petshop/domain/entities/Animal.java

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-10T15:42:11-03:00" GIT_COMMITTER_DATE="2025-06-10T15:42:11-03:00" git commit -m "feat(domain): Implementação da hierarquia de herança para Animal (Cachorro, Gato)" --date "2025-06-10T15:42:11-03:00"
```

### 11/06/2025 - Repositórios

```bash
# Adicionar interfaces de repositório
git add backend/src/main/java/br/com/petshop/domain/repositories/AnimalRepository.java
git add backend/src/main/java/br/com/petshop/domain/repositories/ClienteRepository.java
git add backend/src/main/java/br/com/petshop/domain/repositories/EnderecoRepository.java

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-11T09:33:27-03:00" GIT_COMMITTER_DATE="2025-06-11T09:33:27-03:00" git commit -m "feat(domain): Criação das interfaces de repositório" --date "2025-06-11T09:33:27-03:00"
```

### 12/06/2025 - DTOs

```bash
# Adicionar DTOs
git add backend/src/main/java/br/com/petshop/application/dto/AnimalDTO.java
git add backend/src/main/java/br/com/petshop/application/dto/ClienteDTO.java
git add backend/src/main/java/br/com/petshop/application/dto/EnderecoDTO.java

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-12T14:18:42-03:00" GIT_COMMITTER_DATE="2025-06-12T14:18:42-03:00" git commit -m "feat(application): Criação dos DTOs para transferência de dados" --date "2025-06-12T14:18:42-03:00"
```

### 13/06/2025 - Serviços

```bash
# Adicionar interfaces e implementações de serviços
git add backend/src/main/java/br/com/petshop/application/services/AnimalService.java
git add backend/src/main/java/br/com/petshop/application/services/ClienteService.java
git add backend/src/main/java/br/com/petshop/application/services/impl/AnimalServiceImpl.java
git add backend/src/main/java/br/com/petshop/application/services/impl/ClienteServiceImpl.java

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-13T11:05:38-03:00" GIT_COMMITTER_DATE="2025-06-13T11:05:38-03:00" git commit -m "feat(application): Implementação dos serviços básicos (ClienteService, AnimalService)" --date "2025-06-13T11:05:38-03:00"
```

## Semana 3: Implementação da API REST e Segurança

### 15/06/2025 - Controllers básicos

```bash
# Adicionar controllers REST
git add backend/src/main/java/br/com/petshop/presentation/controllers/AnimalController.java
git add backend/src/main/java/br/com/petshop/presentation/controllers/ClienteController.java
git add backend/src/main/java/br/com/petshop/presentation/controllers/VacinaController.java

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-15T10:22:15-03:00" GIT_COMMITTER_DATE="2025-06-15T10:22:15-03:00" git commit -m "feat(presentation): Criação dos controllers REST básicos" --date "2025-06-15T10:22:15-03:00"
```

### 16/06/2025 - Entidades de segurança

```bash
# Adicionar entidades de segurança
git add backend/src/main/java/br/com/petshop/domain/entities/Usuario.java
git add backend/src/main/java/br/com/petshop/domain/entities/Perfil.java
git add backend/src/main/java/br/com/petshop/domain/repositories/UsuarioRepository.java
git add backend/src/main/java/br/com/petshop/domain/repositories/PerfilRepository.java

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-16T09:47:33-03:00" GIT_COMMITTER_DATE="2025-06-16T09:47:33-03:00" git commit -m "feat(domain): Adição das entidades Usuario e Perfil" --date "2025-06-16T09:47:33-03:00"
```

### 17/06/2025 - Configuração de segurança

```bash
# Adicionar configurações de segurança
git add backend/src/main/java/br/com/petshop/infrastructure/security/SecurityConfig.java
git add backend/src/main/java/br/com/petshop/infrastructure/security/UserDetailsServiceImpl.java
git add backend/src/main/resources/application.properties

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-17T14:29:51-03:00" GIT_COMMITTER_DATE="2025-06-17T14:29:51-03:00" git commit -m "feat(infrastructure): Configuração do Spring Security" --date "2025-06-17T14:29:51-03:00"
```

### 18/06/2025 - Autenticação JWT

```bash
# Adicionar implementação JWT
git add backend/src/main/java/br/com/petshop/infrastructure/security/jwt/JwtTokenProvider.java
git add backend/src/main/java/br/com/petshop/infrastructure/security/jwt/JwtAuthenticationFilter.java
git add backend/src/main/java/br/com/petshop/presentation/controllers/AuthController.java
git add backend/src/main/java/br/com/petshop/application/dto/AuthRequestDTO.java
git add backend/src/main/java/br/com/petshop/application/dto/AuthResponseDTO.java

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-18T11:38:27-03:00" GIT_COMMITTER_DATE="2025-06-18T11:38:27-03:00" git commit -m "feat(infrastructure): Implementação da autenticação JWT" --date "2025-06-18T11:38:27-03:00"
```

### 19/06/2025 - Frontend básico

```bash
# Adicionar componentes do frontend
git add frontend/src/components/
git add frontend/src/services/
git add frontend/src/App.js
git add frontend/src/index.js

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-19T16:14:55-03:00" GIT_COMMITTER_DATE="2025-06-19T16:14:55-03:00" git commit -m "feat(frontend): Implementação dos componentes básicos do frontend" --date "2025-06-19T16:14:55-03:00"
```

## Semana 4: Funcionalidades Avançadas e Testes

### 20/06/2025 - Entidades de consulta

```bash
GIT_AUTHOR_DATE="2025-06-20T09:12:48-03:00" GIT_COMMITTER_DATE="2025-06-20T09:12:48-03:00" git commit -m "feat(domain): Adição das entidades Consulta, Exame e Medicamento" --date "2025-06-20T09:12:48-03:00"
```

### 21/06/2025 - Serviços de consulta

```bash
GIT_AUTHOR_DATE="2025-06-21T10:45:22-03:00" GIT_COMMITTER_DATE="2025-06-21T10:45:22-03:00" git commit -m "feat(application): Implementação dos serviços de Consulta e Agendamento" --date "2025-06-21T10:45:22-03:00"
```

### 22/06/2025 - Controllers avançados

```bash
# Adicionar controllers avançados
git add backend/src/main/java/br/com/petshop/presentation/controllers/ConsultaController.java
git add backend/src/main/java/br/com/petshop/presentation/controllers/ExameController.java
git add backend/src/main/java/br/com/petshop/presentation/controllers/MedicamentoController.java

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-22T14:33:19-03:00" GIT_COMMITTER_DATE="2025-06-22T14:33:19-03:00" git commit -m "feat(presentation): Adição dos controllers para Consulta, Exame e Medicamento" --date "2025-06-22T14:33:19-03:00"
```

### 23/06/2025 - Testes unitários

```bash
# Adicionar testes unitários
git add backend/src/test/java/br/com/petshop/application/services/
git add backend/src/test/java/br/com/petshop/domain/entities/
git add backend/src/test/resources/application-test.properties

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-23T09:27:41-03:00" GIT_COMMITTER_DATE="2025-06-23T09:27:41-03:00" git commit -m "feat(tests): Implementação de testes unitários para serviços" --date "2025-06-23T09:27:41-03:00"
```

### 24/06/2025 - Testes de integração

```bash
# Adicionar testes de integração
git add backend/src/test/java/br/com/petshop/presentation/controllers/
git add backend/src/test/java/br/com/petshop/infrastructure/

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-24T11:18:36-03:00" GIT_COMMITTER_DATE="2025-06-24T11:18:36-03:00" git commit -m "feat(tests): Implementação de testes de integração para controllers" --date "2025-06-24T11:18:36-03:00"
```

### 25/06/2025 - Script de migração

```bash
# Adicionar script de migração
git add backend/src/main/resources/import.sql
git add backend/src/main/resources/schema.sql

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-25T15:42:33-03:00" GIT_COMMITTER_DATE="2025-06-25T15:42:33-03:00" git commit -m "feat(infra): Criação do script de migração do banco de dados" --date "2025-06-25T15:42:33-03:00"
```

### 27/06/2025 - Documentação técnica

```bash
# Adicionar documentação técnica
git add docs/documentacao_tecnica.md
git add backend/entrypoint.sh
git add backend/Dockerfile
git add backend/src/main/resources/application-test-docker.properties

# Commit com data específica
GIT_AUTHOR_DATE="2025-06-27T11:18:45-03:00" GIT_COMMITTER_DATE="2025-06-27T11:18:45-03:00" git commit -m "feat(docs): Adição da documentação técnica completa" --date "2025-06-27T11:18:45-03:00"
```

## Como usar estes comandos

1. Certifique-se de estar no diretório raiz do projeto (`d:\Facul\OO-PetShop`)
2. Faça as alterações necessárias nos arquivos
3. Adicione os arquivos específicos usando os comandos `git add` listados para cada commit
4. Execute o comando de commit com a data desejada
5. Repita o processo para cada commit na sequência cronológica

Exemplo de fluxo completo para um commit:

```bash
# Faça suas alterações nos arquivos de configuração inicial
# Adicione os arquivos específicos
git add pom.xml .gitignore README.md

# Execute o commit com a data específica
GIT_AUTHOR_DATE="2025-06-06T09:23:45-03:00" GIT_COMMITTER_DATE="2025-06-06T09:23:45-03:00" git commit -m "feat(infra): Configuração inicial do projeto Spring Boot e React" --date "2025-06-06T09:23:45-03:00"
