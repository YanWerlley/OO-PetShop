package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.ClienteDTO;
import br.com.petshop.application.dto.EnderecoDTO;
import br.com.petshop.application.services.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração para ClienteController
 * 
 * @author Yan Werlley
 */
@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private ClienteService clienteService;
    
    private ClienteDTO clienteDTO;
    private EnderecoDTO enderecoDTO;
    
    @BeforeEach
    void setUp() {
        // Configuração do DTO de endereço
        enderecoDTO = EnderecoDTO.builder()
                .id(1L)
                .rua("Rua das Flores")
                .numero("123")
                .complemento("Apto 101")
                .bairro("Centro")
                .cidade("São Paulo")
                .estado("SP")
                .cep("01234-567")
                .build();
        
        // Configuração do DTO de cliente
        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("João Silva");
        clienteDTO.setCpf("123.456.789-01");
        clienteDTO.setTelefone("(11) 98765-4321");
        clienteDTO.setDataNascimento(LocalDate.of(1980, 5, 15));
        clienteDTO.setEmail("joao.silva@email.com");
        clienteDTO.setEndereco(enderecoDTO);
        clienteDTO.setAnimaisIds(List.of(1L, 2L));
    }
    
    @Test
    @DisplayName("Deve retornar todos os clientes com sucesso")
    void buscarTodos() throws Exception {
        // Arrange
        ClienteDTO cliente2 = new ClienteDTO();
        cliente2.setId(2L);
        cliente2.setNome("Maria Oliveira");
        cliente2.setCpf("987.654.321-09");
        cliente2.setTelefone("(21) 91234-5678");
        cliente2.setEmail("maria.oliveira@email.com");
        cliente2.setEndereco(enderecoDTO);
        
        List<ClienteDTO> clientes = Arrays.asList(clienteDTO, cliente2);
        when(clienteService.buscarTodos()).thenReturn(clientes);
        
        // Act & Assert
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("João Silva")))
                .andExpect(jsonPath("$[1].nome", is("Maria Oliveira")));
        
        verify(clienteService, times(1)).buscarTodos();
    }
    
    @Test
    @DisplayName("Deve retornar lista vazia quando não há clientes")
    void buscarTodosVazio() throws Exception {
        // Arrange
        when(clienteService.buscarTodos()).thenReturn(Collections.emptyList());
        
        // Act & Assert
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
        
        verify(clienteService, times(1)).buscarTodos();
    }
    
    @Test
    @DisplayName("Deve retornar um cliente por ID com sucesso")
    void buscarPorId() throws Exception {
        // Arrange
        when(clienteService.buscarPorId(1L)).thenReturn(clienteDTO);
        
        // Act & Assert
        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("João Silva")))
                .andExpect(jsonPath("$.cpf", is("123.456.789-01")))
                .andExpect(jsonPath("$.endereco.rua", is("Rua das Flores")))
                .andExpect(jsonPath("$.animaisIds", hasSize(2)));
        
        verify(clienteService, times(1)).buscarPorId(1L);
    }
    
    @Test
    @DisplayName("Deve retornar clientes por nome com sucesso")
    void buscarPorNome() throws Exception {
        // Arrange
        when(clienteService.buscarPorNome("João")).thenReturn(List.of(clienteDTO));
        
        // Act & Assert
        mockMvc.perform(get("/api/clientes/busca/nome")
                .param("nome", "João"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("João Silva")));
        
        verify(clienteService, times(1)).buscarPorNome("João");
    }
    
    @Test
    @DisplayName("Deve retornar cliente por CPF com sucesso")
    void buscarPorCpf() throws Exception {
        // Arrange
        when(clienteService.buscarPorCpf("123.456.789-01")).thenReturn(clienteDTO);
        
        // Act & Assert
        mockMvc.perform(get("/api/clientes/busca/cpf")
                .param("cpf", "123.456.789-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome", is("João Silva")))
                .andExpect(jsonPath("$.cpf", is("123.456.789-01")));
        
        verify(clienteService, times(1)).buscarPorCpf("123.456.789-01");
    }
    
    @Test
    @DisplayName("Deve retornar cliente por email com sucesso")
    void buscarPorEmail() throws Exception {
        // Arrange
        when(clienteService.buscarPorEmail("joao.silva@email.com")).thenReturn(clienteDTO);
        
        // Act & Assert
        mockMvc.perform(get("/api/clientes/busca/email")
                .param("email", "joao.silva@email.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome", is("João Silva")))
                .andExpect(jsonPath("$.email", is("joao.silva@email.com")));
        
        verify(clienteService, times(1)).buscarPorEmail("joao.silva@email.com");
    }
    
    @Test
    @DisplayName("Deve retornar cliente por telefone com sucesso")
    void buscarPorTelefone() throws Exception {
        // Arrange
        when(clienteService.buscarPorTelefone("(11) 98765-4321")).thenReturn(clienteDTO);
        
        // Act & Assert
        mockMvc.perform(get("/api/clientes/busca/telefone")
                .param("telefone", "(11) 98765-4321"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome", is("João Silva")))
                .andExpect(jsonPath("$.telefone", is("(11) 98765-4321")));
        
        verify(clienteService, times(1)).buscarPorTelefone("(11) 98765-4321");
    }
    
    @Test
    @DisplayName("Deve criar um cliente com sucesso")
    void criar() throws Exception {
        // Arrange
        ClienteDTO novoCliente = new ClienteDTO();
        novoCliente.setNome("Pedro Santos");
        novoCliente.setCpf("111.222.333-44");
        novoCliente.setTelefone("(31) 99876-5432");
        novoCliente.setDataNascimento(LocalDate.of(1990, 3, 25));
        novoCliente.setEmail("pedro.santos@email.com");
        novoCliente.setEndereco(enderecoDTO);
        
        when(clienteService.salvar(any(ClienteDTO.class))).thenAnswer(invocation -> {
            ClienteDTO dto = invocation.getArgument(0);
            dto.setId(3L);
            return dto;
        });
        
        // Act & Assert
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoCliente)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/clientes/3")))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nome", is("Pedro Santos")));
        
        verify(clienteService, times(1)).salvar(any(ClienteDTO.class));
    }
    
    @Test
    @DisplayName("Deve retornar erro de validação ao criar cliente inválido")
    void criarClienteInvalido() throws Exception {
        // Arrange
        ClienteDTO clienteInvalido = new ClienteDTO();
        // Cliente sem nome e outros campos obrigatórios
        
        // Act & Assert
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteInvalido)))
                .andExpect(status().isBadRequest());
        
        verify(clienteService, never()).salvar(any(ClienteDTO.class));
    }
    
    @Test
    @DisplayName("Deve atualizar um cliente com sucesso")
    void atualizar() throws Exception {
        // Arrange
        clienteDTO.setNome("João Silva Atualizado");
        clienteDTO.setTelefone("(11) 99999-8888");
        
        when(clienteService.atualizar(eq(1L), any(ClienteDTO.class))).thenReturn(clienteDTO);
        
        // Act & Assert
        mockMvc.perform(put("/api/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("João Silva Atualizado")))
                .andExpect(jsonPath("$.telefone", is("(11) 99999-8888")));
        
        verify(clienteService, times(1)).atualizar(eq(1L), any(ClienteDTO.class));
    }
    
    @Test
    @DisplayName("Deve remover um cliente com sucesso")
    void remover() throws Exception {
        // Arrange
        doNothing().when(clienteService).remover(1L);
        
        // Act & Assert
        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());
        
        verify(clienteService, times(1)).remover(1L);
    }
    
    @Test
    @DisplayName("Deve adicionar um animal ao cliente com sucesso")
    void adicionarAnimal() throws Exception {
        // Arrange
        when(clienteService.adicionarAnimal(1L, 3L)).thenReturn(clienteDTO);
        
        // Act & Assert
        mockMvc.perform(post("/api/clientes/1/animais/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.animaisIds", hasSize(2)));
        
        verify(clienteService, times(1)).adicionarAnimal(1L, 3L);
    }
    
    @Test
    @DisplayName("Deve remover um animal do cliente com sucesso")
    void removerAnimal() throws Exception {
        // Arrange
        ClienteDTO clienteAtualizado = new ClienteDTO();
        clienteAtualizado.setId(1L);
        clienteAtualizado.setNome("João Silva");
        clienteAtualizado.setCpf("123.456.789-01");
        clienteAtualizado.setAnimaisIds(List.of(2L)); // Apenas o animal 2L permanece
        
        when(clienteService.removerAnimal(1L, 1L)).thenReturn(clienteAtualizado);
        
        // Act & Assert
        mockMvc.perform(delete("/api/clientes/1/animais/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.animaisIds", hasSize(1)))
                .andExpect(jsonPath("$.animaisIds[0]", is(2)));
        
        verify(clienteService, times(1)).removerAnimal(1L, 1L);
    }
}
