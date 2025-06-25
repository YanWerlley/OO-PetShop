package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.AnimalDTO;
import br.com.petshop.application.services.AnimalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração para AnimalController
 * 
 * @author Yan Werlley
 */
@WebMvcTest(AnimalController.class)
class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private AnimalService animalService;
    
    private AnimalDTO cachorroDTO;
    private AnimalDTO gatoDTO;
    
    @BeforeEach
    void setUp() {
        // Configuração do DTO de cachorro
        cachorroDTO = new AnimalDTO();
        cachorroDTO.setId(1L);
        cachorroDTO.setNome("Rex");
        cachorroDTO.setPeso(15.5);
        cachorroDTO.setCor("Marrom");
        cachorroDTO.setPossueDoenca(false);
        cachorroDTO.setTipo("CACHORRO");
        cachorroDTO.setProprietarioId(1L);
        cachorroDTO.setRaca("Labrador");
        cachorroDTO.setAdestrado(true);
        
        // Configuração do DTO de gato
        gatoDTO = new AnimalDTO();
        gatoDTO.setId(2L);
        gatoDTO.setNome("Luna");
        gatoDTO.setPeso(4.3);
        gatoDTO.setCor("Branco");
        gatoDTO.setPossueDoenca(false);
        gatoDTO.setTipo("GATO");
        gatoDTO.setProprietarioId(1L);
        gatoDTO.setRaca("Siamês");
        gatoDTO.setCastrado(true);
    }
    
    @Test
    @DisplayName("Deve retornar todos os animais com sucesso")
    void buscarTodos() throws Exception {
        // Arrange
        List<AnimalDTO> animais = Arrays.asList(cachorroDTO, gatoDTO);
        when(animalService.buscarTodos()).thenReturn(animais);
        
        // Act & Assert
        mockMvc.perform(get("/api/animais"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Rex")))
                .andExpect(jsonPath("$[0].tipo", is("CACHORRO")))
                .andExpect(jsonPath("$[1].nome", is("Luna")))
                .andExpect(jsonPath("$[1].tipo", is("GATO")));
        
        verify(animalService, times(1)).buscarTodos();
    }
    
    @Test
    @DisplayName("Deve retornar lista vazia quando não há animais")
    void buscarTodosVazio() throws Exception {
        // Arrange
        when(animalService.buscarTodos()).thenReturn(Collections.emptyList());
        
        // Act & Assert
        mockMvc.perform(get("/api/animais"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
        
        verify(animalService, times(1)).buscarTodos();
    }
    
    @Test
    @DisplayName("Deve retornar um animal por ID com sucesso")
    void buscarPorId() throws Exception {
        // Arrange
        when(animalService.buscarPorId(1L)).thenReturn(cachorroDTO);
        
        // Act & Assert
        mockMvc.perform(get("/api/animais/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Rex")))
                .andExpect(jsonPath("$.tipo", is("CACHORRO")))
                .andExpect(jsonPath("$.adestrado", is(true)));
        
        verify(animalService, times(1)).buscarPorId(1L);
    }
    
    @Test
    @DisplayName("Deve retornar animais por nome com sucesso")
    void buscarPorNome() throws Exception {
        // Arrange
        when(animalService.buscarPorNome("Rex")).thenReturn(List.of(cachorroDTO));
        
        // Act & Assert
        mockMvc.perform(get("/api/animais/busca/nome")
                .param("nome", "Rex"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Rex")));
        
        verify(animalService, times(1)).buscarPorNome("Rex");
    }
    
    @Test
    @DisplayName("Deve retornar animais com doença com sucesso")
    void buscarComDoenca() throws Exception {
        // Arrange
        cachorroDTO.setPossueDoenca(true);
        when(animalService.buscarComDoenca()).thenReturn(List.of(cachorroDTO));
        
        // Act & Assert
        mockMvc.perform(get("/api/animais/busca/doenca"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Rex")))
                .andExpect(jsonPath("$[0].possueDoenca", is(true)));
        
        verify(animalService, times(1)).buscarComDoenca();
    }
    
    @Test
    @DisplayName("Deve retornar animais por proprietário com sucesso")
    void buscarPorProprietario() throws Exception {
        // Arrange
        List<AnimalDTO> animais = Arrays.asList(cachorroDTO, gatoDTO);
        when(animalService.buscarPorProprietario(1L)).thenReturn(animais);
        
        // Act & Assert
        mockMvc.perform(get("/api/animais/busca/proprietario/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].proprietarioId", is(1)))
                .andExpect(jsonPath("$[1].proprietarioId", is(1)));
        
        verify(animalService, times(1)).buscarPorProprietario(1L);
    }
    
    @Test
    @DisplayName("Deve criar um animal com sucesso")
    void criar() throws Exception {
        // Arrange
        AnimalDTO novoAnimal = new AnimalDTO();
        novoAnimal.setNome("Thor");
        novoAnimal.setPeso(20.0);
        novoAnimal.setCor("Preto");
        novoAnimal.setPossueDoenca(false);
        novoAnimal.setTipo("CACHORRO");
        novoAnimal.setProprietarioId(1L);
        novoAnimal.setRaca("Pastor Alemão");
        novoAnimal.setAdestrado(false);
        
        when(animalService.salvar(any(AnimalDTO.class))).thenAnswer(invocation -> {
            AnimalDTO dto = invocation.getArgument(0);
            dto.setId(3L);
            return dto;
        });
        
        // Act & Assert
        mockMvc.perform(post("/api/animais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoAnimal)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/animais/3")))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nome", is("Thor")))
                .andExpect(jsonPath("$.tipo", is("CACHORRO")));
        
        verify(animalService, times(1)).salvar(any(AnimalDTO.class));
    }
    
    @Test
    @DisplayName("Deve retornar erro de validação ao criar animal inválido")
    void criarAnimalInvalido() throws Exception {
        // Arrange
        AnimalDTO animalInvalido = new AnimalDTO();
        // Animal sem nome e outros campos obrigatórios
        
        // Act & Assert
        mockMvc.perform(post("/api/animais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(animalInvalido)))
                .andExpect(status().isBadRequest());
        
        verify(animalService, never()).salvar(any(AnimalDTO.class));
    }
    
    @Test
    @DisplayName("Deve atualizar um animal com sucesso")
    void atualizar() throws Exception {
        // Arrange
        cachorroDTO.setNome("Rex Atualizado");
        cachorroDTO.setCor("Preto e Marrom");
        
        when(animalService.atualizar(eq(1L), any(AnimalDTO.class))).thenReturn(cachorroDTO);
        
        // Act & Assert
        mockMvc.perform(put("/api/animais/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cachorroDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Rex Atualizado")))
                .andExpect(jsonPath("$.cor", is("Preto e Marrom")));
        
        verify(animalService, times(1)).atualizar(eq(1L), any(AnimalDTO.class));
    }
    
    @Test
    @DisplayName("Deve remover um animal com sucesso")
    void remover() throws Exception {
        // Arrange
        doNothing().when(animalService).remover(1L);
        
        // Act & Assert
        mockMvc.perform(delete("/api/animais/1"))
                .andExpect(status().isNoContent());
        
        verify(animalService, times(1)).remover(1L);
    }
}
