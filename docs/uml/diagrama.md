# Diagrama UML - PetShop

Este diagrama UML representa a estrutura do sistema PetShop, mostrando as classes, relacionamentos e a arquitetura do projeto.

<iframe width="768" height="432" src="https://miro.com/app/live-embed/uXjVL-ulNgk=/?moveToViewport=-807,-1733,1737,2333&embedId=919866397376" frameborder="0" scrolling="no" allow="fullscreen; clipboard-read; clipboard-write" allowfullscreen></iframe>

## Descrição do Diagrama

O diagrama UML acima representa a estrutura do sistema PetShop, seguindo os princípios de Orientação a Objetos e a arquitetura MVC (Model-View-Controller). 

### Principais Componentes:

1. **Modelos (Model)**
   - Animal: Representa um animal com atributos como nome, peso, cor, etc.
   - Cliente: Representa um cliente, estende a classe Pessoa
   - Pessoa: Classe abstrata que serve como base para entidades como Cliente
   - Endereco: Representa um endereço
   - Vacina: Representa uma vacina

2. **Controladores (Controller)**
   - ControleAnimal: Gerencia operações CRUD para animais
   - ControleVacina: Gerencia operações CRUD para vacinas

3. **Visões (View)**
   - TelaInicial: Tela principal que dá acesso às outras funcionalidades
   - TelaAnimal: Interface para gerenciamento de animais
   - TelaCadastroAnimal: Interface para cadastro de novos animais
   - TelaEditarAnimal: Interface para edição de animais existentes
   - TelaVacina: Interface para gerenciamento de vacinas

### Relacionamentos:

- Herança: Cliente herda de Pessoa
- Composição: Animal possui uma lista de Vacinas
- Associação: Entre controladores e modelos, e entre visões e controladores

Este diagrama foi criado para o Ponto de Controle 1 do projeto PetShop.
