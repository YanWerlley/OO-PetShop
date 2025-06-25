package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.ExameDTO;
import br.com.petshop.application.services.ExameService;
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
 * Testes de integração para ExameController
 * 
 * @author Yan Werlley
 */
@WebMvcTest(ExameController.class)
class ExameControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private ExameService exameService;
    
    private ExameDTO exameDTO1;
    private ExameDTO exameDTO2;
    
    @BeforeEach
    void setUp() {
        // Configuração do DTO de exame 1
        exameDTO1 = new ExameDTO();
        exameDTO1.setId(1L);
        exameDTO1.setNome("Hemograma Completo");
        exameDTO1.setTipo("Sangue");
        exameDTO1.setResultado("Normal");
        exameDTO1.setDataRealizacao(LocalDate.of(2025, 6, 15));
        exameDTO1.setDataResultado(LocalDate.of(2025, 6, 16));
        exameDTO1.setObservacoes("Todos os parâmetros dentro da normalidade");
        exameDTO1.setConsultaId(1L);
        exameDTO1.setAnimalId(1L);
        exameDTO1.setAnimalNome("Rex");
        
        // Configuração do DTO de exame 2
        exameDTO2 = new ExameDTO();
        exameDTO2.setId(2L);
        exameDTO2.setNome("Raio-X Torácico");
        exameDTO2.setTipo("Imagem");
        exameDTO2.setResultado("Normal");
        exameDTO2.setDataRealizacao(LocalDate.of(2025, 6, 16));
        exameDTO2.setDataResultado(LocalDate.of(2025, 6, 16));
        exameDTO2.setObservacoes("Sem alterações visíveis");
        exameDTO2.setConsultaId(2L);
        exameDTO2.setAnimalId(2L);
        exameDTO2.setAnimalNome("Thor");
    }
    
    @Test
    @DisplayName("Deve retornar todos os exames com sucesso")
    void buscarTodos() throws Exception {
        // Arrange
        List<ExameDTO> exames = Arrays.asList(exameDTO1, exameDTO2);
        when(exameService.buscarTodos()).thenReturn(exames);
        
        // Act & Assert
        mockMvc.perform(get("/api/exames"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Hemograma Completo")))
                .andExpect(jsonPath("$[1].nome", is("Raio-X Torácico")));
        
        verify(exameService, times(1)).buscarTodos();
    }
    
    @Test
    @DisplayName("Deve retornar lista vazia quando não há exames")
    void buscarTodosVazio() throws Exception {
        // Arrange
        when(exameService.buscarTodos()).thenReturn(Collections.emptyList());
        
        // Act & Assert
        mockMvc.perform(get("/api/exames"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
        
        verify(exameService, times(1)).buscarTodos();
    }
    
    @Test
    @DisplayName("Deve retornar um exame por ID com sucesso")
    void buscarPorId() throws Exception {
        // Arrange
        when(exameService.buscarPorId(1L)).thenReturn(exameDTO1);
        
        // Act & Assert
        mockMvc.perform(get("/api/exames/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Hemograma Completo")))
                .andExpect(jsonPath("$.tipo", is("Sangue")));
        
        verify(exameService, times(1)).buscarPorId(1L);
    }
    
    @Test
    @DisplayName("Deve retornar exames por animal com sucesso")
    void buscarPorAnimal() throws Exception {
        // Arrange
        when(exameService.buscarPorAnimal(1L)).thenReturn(List.of(exameDTO1));
        
        // Act & Assert
        mockMvc.perform(get("/api/exames/animal/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Hemograma Completo")))
                .andExpect(jsonPath("$[0].animalId", is(1)));
        
        verify(exameService, times(1)).buscarPorAnimal(1L);
    }
    
    @Test
    @DisplayName("Deve retornar exames por consulta com sucesso")
    void buscarPorConsulta() throws Exception {
        // Arrange
        when(exameService.buscarPorConsulta(1L)).thenReturn(List.of(exameDTO1));
        
        // Act & Assert
        mockMvc.perform(get("/api/exames/consulta/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Hemograma Completo")))
                .andExpect(jsonPath("$[0].consultaId", is(1)));
        
        verify(exameService, times(1)).buscarPorConsulta(1L);
    }
    
    @Test
    @DisplayName("Deve retornar exames por tipo com sucesso")
    void buscarPorTipo() throws Exception {
        // Arrange
        when(exameService.buscarPorTipo("Sangue")).thenReturn(List.of(exameDTO1));
        
        // Act & Assert
        mockMvc.perform(get("/api/exames/tipo/Sangue"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Hemograma Completo")))
                .andExpect(jsonPath("$[0].tipo", is("Sangue")));
        
        verify(exameService, times(1)).buscarPorTipo("Sangue");
    }
    
    @Test
    @DisplayName("Deve retornar exames por período com sucesso")
    void buscarPorPeriodo() throws Exception {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 6, 15);
        LocalDate fim = LocalDate.of(2025, 6, 20);
        when(exameService.buscarPorPeriodo(inicio, fim)).thenReturn(Arrays.asList(exameDTO1, exameDTO2));
        
        // Act & Assert
        mockMvc.perform(get("/api/exames/periodo")
                .param("inicio", "2025-06-15")
                .param("fim", "2025-06-20"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Hemograma Completo")))
                .andExpect(jsonPath("$[1].nome", is("Raio-X Torácico")));
        
        verify(exameService, times(1)).buscarPorPeriodo(inicio, fim);
    }
    
    @Test
    @DisplayName("Deve retornar exames por nome com sucesso")
    void buscarPorNome() throws Exception {
        // Arrange
        when(exameService.buscarPorNome("Hemograma")).thenReturn(List.of(exameDTO1));
        
        // Act & Assert
        mockMvc.perform(get("/api/exames/nome/Hemograma"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Hemograma Completo")));
        
        verify(exameService, times(1)).buscarPorNome("Hemograma");
    }
    
    @Test
    @DisplayName("Deve criar um exame com sucesso")
    void salvar() throws Exception {
        // Arrange
        ExameDTO novoExame = new ExameDTO();
        novoExame.setNome("Ultrassom Abdominal");
        novoExame.setTipo("Imagem");
        novoExame.setDataRealizacao(LocalDate.of(2025, 6, 18));
        novoExame.setAnimalId(3L);
        
        when(exameService.salvar(any(ExameDTO.class))).thenAnswer(invocation -> {
            ExameDTO dto = invocation.getArgument(0);
            dto.setId(3L);
            return dto;
        });
        
        // Act & Assert
        mockMvc.perform(post("/api/exames")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoExame)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/exames/3")))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nome", is("Ultrassom Abdominal")));
        
        verify(exameService, times(1)).salvar(any(ExameDTO.class));
    }
    
    @Test
    @DisplayName("Deve atualizar um exame com sucesso")
    void atualizar() throws Exception {
        // Arrange
        exameDTO1.setResultado("Alterado");
        exameDTO1.setObservacoes("Alterações nos níveis de glicose");
        
        when(exameService.atualizar(eq(1L), any(ExameDTO.class))).thenReturn(exameDTO1);
        
        // Act & Assert
        mockMvc.perform(put("/api/exames/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exameDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.resultado", is("Alterado")))
                .andExpect(jsonPath("$.observacoes", is("Alterações nos níveis de glicose")));
        
        verify(exameService, times(1)).atualizar(eq(1L), any(ExameDTO.class));
    }
    
    @Test
    @DisplayName("Deve remover um exame com sucesso")
    void remover() throws Exception {
        // Arrange
        doNothing().when(exameService).remover(1L);
        
        // Act & Assert
        mockMvc.perform(delete("/api/exames/1"))
                .andExpect(status().isNoContent());
        
        verify(exameService, times(1)).remover(1L);
    }
}
