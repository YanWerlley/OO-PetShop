package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.VacinaDTO;
import br.com.petshop.application.services.VacinaService;
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
 * Testes de integração para VacinaController
 * 
 * @author Yan Werlley
 */
@WebMvcTest(VacinaController.class)
class VacinaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private VacinaService vacinaService;
    
    private VacinaDTO vacinaDTO1;
    private VacinaDTO vacinaDTO2;
    
    @BeforeEach
    void setUp() {
        // Configuração do DTO de vacina 1
        vacinaDTO1 = new VacinaDTO();
        vacinaDTO1.setId(1L);
        vacinaDTO1.setNome("V8");
        vacinaDTO1.setDataAplicacao(LocalDate.of(2025, 1, 15));
        vacinaDTO1.setLote("L123456");
        vacinaDTO1.setDataValidade(LocalDate.of(2026, 1, 15));
        vacinaDTO1.setDataProximaDose(LocalDate.of(2025, 7, 15));
        vacinaDTO1.setAnimalId(1L);
        
        // Configuração do DTO de vacina 2
        vacinaDTO2 = new VacinaDTO();
        vacinaDTO2.setId(2L);
        vacinaDTO2.setNome("Antirrábica");
        vacinaDTO2.setDataAplicacao(LocalDate.of(2025, 2, 10));
        vacinaDTO2.setLote("L789012");
        vacinaDTO2.setDataValidade(LocalDate.of(2026, 2, 10));
        vacinaDTO2.setAnimalId(1L);
    }
    
    @Test
    @DisplayName("Deve retornar todas as vacinas com sucesso")
    void buscarTodas() throws Exception {
        // Arrange
        List<VacinaDTO> vacinas = Arrays.asList(vacinaDTO1, vacinaDTO2);
        when(vacinaService.buscarTodas()).thenReturn(vacinas);
        
        // Act & Assert
        mockMvc.perform(get("/api/vacinas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("V8")))
                .andExpect(jsonPath("$[1].nome", is("Antirrábica")));
        
        verify(vacinaService, times(1)).buscarTodas();
    }
    
    @Test
    @DisplayName("Deve retornar lista vazia quando não há vacinas")
    void buscarTodasVazio() throws Exception {
        // Arrange
        when(vacinaService.buscarTodas()).thenReturn(Collections.emptyList());
        
        // Act & Assert
        mockMvc.perform(get("/api/vacinas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
        
        verify(vacinaService, times(1)).buscarTodas();
    }
    
    @Test
    @DisplayName("Deve retornar uma vacina por ID com sucesso")
    void buscarPorId() throws Exception {
        // Arrange
        when(vacinaService.buscarPorId(1L)).thenReturn(vacinaDTO1);
        
        // Act & Assert
        mockMvc.perform(get("/api/vacinas/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("V8")))
                .andExpect(jsonPath("$.lote", is("L123456")));
        
        verify(vacinaService, times(1)).buscarPorId(1L);
    }
    
    @Test
    @DisplayName("Deve retornar vacinas por animal com sucesso")
    void buscarPorAnimal() throws Exception {
        // Arrange
        List<VacinaDTO> vacinas = Arrays.asList(vacinaDTO1, vacinaDTO2);
        when(vacinaService.buscarPorAnimal(1L)).thenReturn(vacinas);
        
        // Act & Assert
        mockMvc.perform(get("/api/vacinas/busca/animal/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].animalId", is(1)))
                .andExpect(jsonPath("$[1].animalId", is(1)));
        
        verify(vacinaService, times(1)).buscarPorAnimal(1L);
    }
    
    @Test
    @DisplayName("Deve retornar vacinas por nome com sucesso")
    void buscarPorNome() throws Exception {
        // Arrange
        when(vacinaService.buscarPorNome("V8")).thenReturn(List.of(vacinaDTO1));
        
        // Act & Assert
        mockMvc.perform(get("/api/vacinas/busca/nome")
                .param("nome", "V8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("V8")));
        
        verify(vacinaService, times(1)).buscarPorNome("V8");
    }
    
    @Test
    @DisplayName("Deve retornar vacinas por período de aplicação com sucesso")
    void buscarPorPeriodoAplicacao() throws Exception {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 31);
        when(vacinaService.buscarPorPeriodoAplicacao(inicio, fim)).thenReturn(List.of(vacinaDTO1));
        
        // Act & Assert
        mockMvc.perform(get("/api/vacinas/busca/aplicacao")
                .param("inicio", "2025-01-01")
                .param("fim", "2025-01-31"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("V8")))
                .andExpect(jsonPath("$[0].dataAplicacao", is("2025-01-15")));
        
        verify(vacinaService, times(1)).buscarPorPeriodoAplicacao(inicio, fim);
    }
    
    @Test
    @DisplayName("Deve retornar vacinas vencidas com sucesso")
    void buscarVencidas() throws Exception {
        // Arrange
        LocalDate dataReferencia = LocalDate.of(2026, 2, 15);
        when(vacinaService.buscarVencidas(dataReferencia)).thenReturn(Arrays.asList(vacinaDTO1, vacinaDTO2));
        
        // Act & Assert
        mockMvc.perform(get("/api/vacinas/busca/vencidas")
                .param("dataReferencia", "2026-02-15"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
        
        verify(vacinaService, times(1)).buscarVencidas(dataReferencia);
    }
    
    @Test
    @DisplayName("Deve retornar vacinas com próximas doses em um período com sucesso")
    void buscarProximasDoses() throws Exception {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 7, 1);
        LocalDate fim = LocalDate.of(2025, 7, 31);
        when(vacinaService.buscarProximasDoses(inicio, fim)).thenReturn(List.of(vacinaDTO1));
        
        // Act & Assert
        mockMvc.perform(get("/api/vacinas/busca/proximas-doses")
                .param("inicio", "2025-07-01")
                .param("fim", "2025-07-31"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("V8")))
                .andExpect(jsonPath("$[0].dataProximaDose", is("2025-07-15")));
        
        verify(vacinaService, times(1)).buscarProximasDoses(inicio, fim);
    }
    
    @Test
    @DisplayName("Deve criar uma vacina com sucesso")
    void criar() throws Exception {
        // Arrange
        VacinaDTO novaVacina = new VacinaDTO();
        novaVacina.setNome("V10");
        novaVacina.setDataAplicacao(LocalDate.of(2025, 3, 20));
        novaVacina.setLote("L999999");
        novaVacina.setDataValidade(LocalDate.of(2026, 3, 20));
        novaVacina.setAnimalId(1L);
        
        when(vacinaService.salvar(any(VacinaDTO.class))).thenAnswer(invocation -> {
            VacinaDTO dto = invocation.getArgument(0);
            dto.setId(3L);
            return dto;
        });
        
        // Act & Assert
        mockMvc.perform(post("/api/vacinas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novaVacina)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/vacinas/3")))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nome", is("V10")));
        
        verify(vacinaService, times(1)).salvar(any(VacinaDTO.class));
    }
    
    @Test
    @DisplayName("Deve retornar erro de validação ao criar vacina inválida")
    void criarVacinaInvalida() throws Exception {
        // Arrange
        VacinaDTO vacinaInvalida = new VacinaDTO();
        // Vacina sem nome e outros campos obrigatórios
        
        // Act & Assert
        mockMvc.perform(post("/api/vacinas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacinaInvalida)))
                .andExpect(status().isBadRequest());
        
        verify(vacinaService, never()).salvar(any(VacinaDTO.class));
    }
    
    @Test
    @DisplayName("Deve atualizar uma vacina com sucesso")
    void atualizar() throws Exception {
        // Arrange
        vacinaDTO1.setNome("V8 Atualizada");
        vacinaDTO1.setLote("L123456-A");
        
        when(vacinaService.atualizar(eq(1L), any(VacinaDTO.class))).thenReturn(vacinaDTO1);
        
        // Act & Assert
        mockMvc.perform(put("/api/vacinas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacinaDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("V8 Atualizada")))
                .andExpect(jsonPath("$.lote", is("L123456-A")));
        
        verify(vacinaService, times(1)).atualizar(eq(1L), any(VacinaDTO.class));
    }
    
    @Test
    @DisplayName("Deve remover uma vacina com sucesso")
    void remover() throws Exception {
        // Arrange
        doNothing().when(vacinaService).remover(1L);
        
        // Act & Assert
        mockMvc.perform(delete("/api/vacinas/1"))
                .andExpect(status().isNoContent());
        
        verify(vacinaService, times(1)).remover(1L);
    }
}
