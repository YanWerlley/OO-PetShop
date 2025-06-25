# PetShop PC2 - Frontend

Este é o frontend do sistema de gerenciamento PetShop desenvolvido para o projeto de Programação de Computadores II. O frontend foi construído utilizando React e se comunica com o backend Spring Boot.

## Estrutura do Projeto

```
frontend/
├── public/                # Arquivos públicos
├── src/                   # Código fonte
│   ├── components/        # Componentes React
│   │   ├── animais/       # Componentes relacionados a animais
│   │   ├── clientes/      # Componentes relacionados a clientes
│   │   ├── layout/        # Componentes de layout (Header, Footer)
│   │   ├── pages/         # Páginas principais
│   │   └── vacinas/       # Componentes relacionados a vacinas
│   ├── services/          # Serviços para comunicação com a API
│   ├── App.js             # Componente principal
│   ├── App.css            # Estilos da aplicação
│   ├── index.js           # Ponto de entrada
│   └── index.css          # Estilos globais
└── package.json           # Dependências e scripts
```

## Tecnologias Utilizadas

- React 18
- React Router Dom 6
- React Bootstrap
- Axios
- Bootstrap 5

## Funcionalidades

- **Gestão de Clientes**: Cadastro, edição, listagem e exclusão de clientes.
- **Gestão de Animais**: Cadastro, edição, listagem e exclusão de animais, com suporte para diferentes tipos (cachorro, gato).
- **Gestão de Vacinas**: Controle de vacinas aplicadas, com datas de validade e próximas doses.
- **Pesquisa Avançada**: Filtros de busca para todas as entidades.
- **Interface Responsiva**: Adaptada para diferentes tamanhos de tela.

## Pré-requisitos

- Node.js (versão 14 ou superior)
- NPM ou Yarn

## Instalação

1. Clone o repositório:
   ```
   git clone https://github.com/seu-usuario/OO-PetShop.git
   cd OO-PetShop/frontend
   ```

2. Instale as dependências:
   ```
   npm install
   ```

3. Inicie o servidor de desenvolvimento:
   ```
   npm start
   ```

4. Acesse a aplicação em `http://localhost:3000`

## Integração com o Backend

O frontend está configurado para se comunicar com o backend Spring Boot na porta 8080. Certifique-se de que o backend esteja em execução antes de usar as funcionalidades que dependem da API.

## Scripts Disponíveis

- `npm start`: Inicia o servidor de desenvolvimento
- `npm build`: Cria a versão de produção
- `npm test`: Executa os testes
- `npm eject`: Ejeta a configuração do Create React App

## Autor

Desenvolvido para o projeto de Programação de Computadores II.
