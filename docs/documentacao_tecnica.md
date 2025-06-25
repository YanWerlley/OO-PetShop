# Documentação Técnica - Sistema PetShop

## 1. Visão Geral do Sistema

O sistema PetShop é uma aplicação completa para gerenciamento de clínicas veterinárias e pet shops, oferecendo funcionalidades para cadastro de clientes e seus animais, agendamento de consultas, registro de vacinas, exames e medicamentos, além de controle de acesso baseado em perfis de usuário.

### 1.1 Arquitetura

O sistema segue uma arquitetura em camadas, implementada com Spring Boot:

- **Apresentação**: Controllers REST que expõem endpoints para o frontend
- **Aplicação**: Serviços que implementam a lógica de negócio
- **Domínio**: Entidades e repositórios que representam o modelo de dados
- **Infraestrutura**: Configurações de segurança, banco de dados e utilitários

### 1.2 Tecnologias Utilizadas

- **Backend**: Java 17, Spring Boot 3.x, Spring Data JPA, Spring Security
- **Segurança**: JWT (JSON Web Token)
- **Banco de Dados**: H2 (desenvolvimento), PostgreSQL (produção)
- **Documentação**: Swagger/OpenAPI
- **Testes**: JUnit 5, Mockito

## 2. Modelo de Domínio

### 2.1 Entidades Principais

- **Cliente**: Representa os tutores dos animais
- **Animal**: Representa os pets atendidos pela clínica
- **Agendamento**: Representa as marcações de consultas
- **Consulta**: Representa os atendimentos veterinários realizados
- **Vacina**: Representa as vacinas aplicadas nos animais
- **Exame**: Representa os exames realizados durante as consultas
- **Medicamento**: Representa os medicamentos prescritos durante as consultas
- **Usuario**: Representa os usuários do sistema (veterinários, recepcionistas, etc.)
- **Perfil**: Representa os perfis de acesso (ADMIN, VETERINARIO, RECEPCIONISTA, CLIENTE)

### 2.2 Relacionamentos

- Um **Cliente** pode ter vários **Animais**
- Um **Animal** pode ter vários **Agendamentos**, **Vacinas**, **Exames** e **Medicamentos**
- Um **Agendamento** está associado a um **Animal** e pode gerar uma **Consulta**
- Uma **Consulta** pode ter vários **Exames** e **Medicamentos**
- Um **Usuario** pode ter vários **Perfis**

## 3. Camada de Persistência

### 3.1 Repositórios

Todos os repositórios estendem `JpaRepository` do Spring Data JPA, fornecendo operações CRUD básicas. Adicionalmente, implementam métodos de consulta específicos:

- **ClienteRepository**: Busca por nome, email, CPF
- **AnimalRepository**: Busca por nome, espécie, raça, cliente
- **AgendamentoRepository**: Busca por data, status, animal
- **ConsultaRepository**: Busca por animal, veterinário, status, período
- **VacinaRepository**: Busca por animal, nome, período, vencidas, próximas doses
- **ExameRepository**: Busca por animal, consulta, tipo, nome, período
- **MedicamentoRepository**: Busca por animal, consulta, nome, período

### 3.2 Exemplo de Consultas Personalizadas

```java
// ExameRepository
List<Exame> findByAnimalId(Long animalId);
List<Exame> findByConsultaId(Long consultaId);
List<Exame> findByTipo(String tipo);
List<Exame> findByNomeContainingIgnoreCase(String nome);
List<Exame> findByDataRealizacaoBetween(LocalDate inicio, LocalDate fim);

// MedicamentoRepository
List<Medicamento> findByAnimalId(Long animalId);
List<Medicamento> findByConsultaId(Long consultaId);
List<Medicamento> findByNomeContainingIgnoreCase(String nome);
List<Medicamento> findByDataInicioBetween(LocalDate inicio, LocalDate fim);
List<Medicamento> findByDataFimBetween(LocalDate inicio, LocalDate fim);
```

## 4. Camada de Serviço

### 4.1 Interfaces de Serviço

As interfaces de serviço definem os contratos para as operações de negócio:

- **ClienteService**: Gerenciamento de clientes
- **AnimalService**: Gerenciamento de animais
- **AgendamentoService**: Gerenciamento de agendamentos
- **ConsultaService**: Gerenciamento de consultas
- **VacinaService**: Gerenciamento de vacinas
- **ExameService**: Gerenciamento de exames
- **MedicamentoService**: Gerenciamento de medicamentos
- **UsuarioService**: Gerenciamento de usuários

### 4.2 Implementações de Serviço

As implementações de serviço contêm a lógica de negócio e realizam:

- Conversão entre entidades e DTOs
- Validações de regras de negócio
- Chamadas aos repositórios
- Tratamento de exceções

Exemplo de implementação:

```java
@Service
public class ExameServiceImpl implements ExameService {
    
    private final ExameRepository exameRepository;
    private final AnimalRepository animalRepository;
    private final ConsultaRepository consultaRepository;
    
    // Construtor com injeção de dependências
    
    @Override
    public ExameDTO salvar(ExameDTO exameDTO) {
        // Validação
        Animal animal = animalRepository.findById(exameDTO.getAnimalId())
            .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado"));
            
        Consulta consulta = null;
        if (exameDTO.getConsultaId() != null) {
            consulta = consultaRepository.findById(exameDTO.getConsultaId())
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada"));
        }
        
        // Conversão DTO -> Entidade
        Exame exame = new Exame();
        exame.setNome(exameDTO.getNome());
        exame.setTipo(exameDTO.getTipo());
        exame.setDataRealizacao(exameDTO.getDataRealizacao());
        exame.setDataResultado(exameDTO.getDataResultado());
        exame.setResultado(exameDTO.getResultado());
        exame.setObservacoes(exameDTO.getObservacoes());
        exame.setAnimal(animal);
        exame.setConsulta(consulta);
        
        // Persistência
        exame = exameRepository.save(exame);
        
        // Conversão Entidade -> DTO
        return converterParaDTO(exame);
    }
    
    // Outros métodos de implementação...
    
    private ExameDTO converterParaDTO(Exame exame) {
        ExameDTO dto = new ExameDTO();
        dto.setId(exame.getId());
        dto.setNome(exame.getNome());
        dto.setTipo(exame.getTipo());
        dto.setDataRealizacao(exame.getDataRealizacao());
        dto.setDataResultado(exame.getDataResultado());
        dto.setResultado(exame.getResultado());
        dto.setObservacoes(exame.getObservacoes());
        dto.setAnimalId(exame.getAnimal().getId());
        dto.setAnimalNome(exame.getAnimal().getNome());
        
        if (exame.getConsulta() != null) {
            dto.setConsultaId(exame.getConsulta().getId());
        }
        
        return dto;
    }
}
```

## 5. Camada de Apresentação

### 5.1 Controllers REST

Os controllers expõem endpoints REST para interação com o frontend:

- **ClienteController**: `/api/clientes`
- **AnimalController**: `/api/animais`
- **AgendamentoController**: `/api/agendamentos`
- **ConsultaController**: `/api/consultas`
- **VacinaController**: `/api/vacinas`
- **ExameController**: `/api/exames`
- **MedicamentoController**: `/api/medicamentos`
- **AuthController**: `/api/auth`

### 5.2 Exemplo de Controller

```java
@RestController
@RequestMapping("/api/exames")
@PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIO')")
public class ExameController {

    private final ExameService exameService;
    
    public ExameController(ExameService exameService) {
        this.exameService = exameService;
    }
    
    @GetMapping
    public ResponseEntity<List<ExameDTO>> buscarTodos() {
        return ResponseEntity.ok(exameService.buscarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ExameDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(exameService.buscarPorId(id));
    }
    
    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<ExameDTO>> buscarPorAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(exameService.buscarPorAnimal(animalId));
    }
    
    @PostMapping
    public ResponseEntity<ExameDTO> salvar(@Valid @RequestBody ExameDTO exameDTO) {
        ExameDTO exame = exameService.salvar(exameDTO);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(exame.getId())
            .toUri();
        return ResponseEntity.created(location).body(exame);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ExameDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ExameDTO exameDTO) {
        return ResponseEntity.ok(exameService.atualizar(id, exameDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        exameService.remover(id);
        return ResponseEntity.noContent().build();
    }
    
    // Outros endpoints específicos...
}
```

## 6. Segurança

### 6.1 Configuração de Segurança

O sistema utiliza Spring Security com JWT para autenticação e autorização:

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final JwtUtils jwtUtils;
    
    // Configuração de segurança...
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> 
                auth.requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/public/**").permitAll()
                    .anyRequest().authenticated()
            );
        
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    // Outros beans de configuração...
}
```

### 6.2 Perfis de Acesso

O sistema define os seguintes perfis:

- **ADMIN**: Acesso total ao sistema
- **VETERINARIO**: Acesso às funcionalidades clínicas (consultas, exames, medicamentos)
- **RECEPCIONISTA**: Acesso ao cadastro de clientes, animais e agendamentos
- **CLIENTE**: Acesso limitado às suas próprias informações

## 7. Testes

### 7.1 Testes Unitários

Os testes unitários verificam o comportamento das classes de serviço isoladamente, utilizando mocks para as dependências.

### 7.2 Testes de Integração

Os testes de integração verificam a interação entre os controllers e os serviços, utilizando `@WebMvcTest` para testar os endpoints REST.

Exemplo de teste de integração:

```java
@WebMvcTest(ExameController.class)
class ExameControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private ExameService exameService;
    
    @Test
    @DisplayName("Deve retornar todos os exames com sucesso")
    void buscarTodos() throws Exception {
        // Arrange
        List<ExameDTO> exames = Arrays.asList(/* DTOs de teste */);
        when(exameService.buscarTodos()).thenReturn(exames);
        
        // Act & Assert
        mockMvc.perform(get("/api/exames"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
        
        verify(exameService, times(1)).buscarTodos();
    }
    
    // Outros testes...
}
```

## 8. Inicialização do Banco de Dados

O sistema utiliza scripts SQL para inicializar o banco de dados com dados de exemplo:

- `schema.sql`: Define a estrutura do banco de dados
- `import.sql`: Insere dados iniciais para testes

## 9. Próximos Passos

- Implementação de testes end-to-end
- Integração com serviços externos (envio de e-mail, SMS)
- Implementação de relatórios avançados
- Desenvolvimento de aplicativo móvel para clientes

## 10. Considerações Finais

O sistema PetShop foi desenvolvido seguindo boas práticas de desenvolvimento de software, com foco em:

- Arquitetura em camadas
- Separação de responsabilidades
- Código limpo e testável
- Segurança robusta
- Documentação abrangente

A estrutura modular permite a fácil extensão do sistema para novas funcionalidades no futuro.
