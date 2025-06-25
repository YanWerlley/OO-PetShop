package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.ConsultaDTO;
import br.com.petshop.application.services.ConsultaService;
import br.com.petshop.domain.entities.Consulta;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração para ConsultaController
 * 
 * @author Yan Werlley
 */

@WebMvcTest(ConsultaController.class)
public class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private ConsultaService consultaService;

    private ConsultaDTO consultaDTO;
    private Page<ConsultaDTO> consultaPage;

    @BeforeEach
    void setUp() {
        // Configurar objetos para testes
        consultaDTO = new ConsultaDTO();
        consultaDTO.setId(1L);
        consultaDTO.setAgendamentoId(1L);
        consultaDTO.setDataHoraInicio(LocalDateTime.now());
        consultaDTO.setDiagnostico("Diagnóstico de teste");
        consultaDTO.setObservacoes("Observações de teste");

        consultaPage = new PageImpl<>(List.of(consultaDTO));
    }

    @Test
    @DisplayName("Deve iniciar uma consulta com sucesso")
    void iniciarConsulta_DeveRetornarConsultaIniciada() throws Exception {
        // Arrange
        when(consultaService.iniciarConsulta(1L)).thenReturn(consultaDTO);

        // Act & Assert
        mockMvc.perform(post("/api/consultas/iniciar/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.diagnostico", is("Diagnóstico de teste")));
                
        verify(consultaService).iniciarConsulta(1L);
    }

    @Test
    @DisplayName("Deve finalizar uma consulta com sucesso")
    void finalizarConsulta_DeveRetornarConsultaFinalizada() throws Exception {
        // Arrange
        consultaDTO.setDataHoraFim(LocalDateTime.now());
        when(consultaService.finalizarConsulta(eq(1L), any(ConsultaDTO.class))).thenReturn(consultaDTO);

        // Act & Assert
        mockMvc.perform(put("/api/consultas/finalizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(consultaDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.diagnostico", is("Diagnóstico de teste")));
                
        verify(consultaService).finalizarConsulta(eq(1L), any(ConsultaDTO.class));
    }

    @Test
    @DisplayName("Deve buscar uma consulta por ID com sucesso")
    void buscarConsultaPorId_DeveRetornarConsulta() throws Exception {
        // Arrange
        when(consultaService.buscarConsultaPorId(1L)).thenReturn(consultaDTO);

        // Act & Assert
        mockMvc.perform(get("/api/consultas/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.diagnostico", is("Diagnóstico de teste")));
                
        verify(consultaService).buscarConsultaPorId(1L);
    }

    @Test
    @DisplayName("Deve listar consultas paginadas com sucesso")
    void listarConsultas_DeveRetornarPaginaDeConsultas() throws Exception {
        // Arrange
        when(consultaService.listarConsultas(any(Pageable.class))).thenReturn(consultaPage);

        // Act & Assert
        mockMvc.perform(get("/api/consultas")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.totalElements", is(1)));
                
        verify(consultaService).listarConsultas(any(Pageable.class));
    }

    @Test
    @DisplayName("Deve adicionar exame à consulta com sucesso")
    void adicionarExame_DeveRetornarConsultaAtualizada() throws Exception {
        // Arrange
        when(consultaService.adicionarExame(1L, 1L)).thenReturn(consultaDTO);

        // Act & Assert
        mockMvc.perform(post("/api/consultas/1/exames/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.diagnostico", is("Diagnóstico de teste")));
                
        verify(consultaService).adicionarExame(1L, 1L);
    }

    @Test
    @DisplayName("Deve adicionar medicamento à consulta com sucesso")
    void adicionarMedicamento_DeveRetornarConsultaAtualizada() throws Exception {
        // Arrange
        when(consultaService.adicionarMedicamento(1L, 1L)).thenReturn(consultaDTO);

        // Act & Assert
        mockMvc.perform(post("/api/consultas/1/medicamentos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.diagnostico", is("Diagnóstico de teste")));
                
        verify(consultaService).adicionarMedicamento(1L, 1L);
    }

    @Test
    @DisplayName("Deve remover exame da consulta com sucesso")
    void removerExame_DeveRetornarConsultaAtualizada() throws Exception {
        // Arrange
        when(consultaService.removerExame(1L, 1L)).thenReturn(consultaDTO);

        // Act & Assert
        mockMvc.perform(delete("/api/consultas/1/exames/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.diagnostico", is("Diagnóstico de teste")));
                
        verify(consultaService).removerExame(1L, 1L);
    }

    @Test
    @DisplayName("Deve remover medicamento da consulta com sucesso")
    void removerMedicamento_DeveRetornarConsultaAtualizada() throws Exception {
        // Arrange
        when(consultaService.removerMedicamento(1L, 1L)).thenReturn(consultaDTO);

        // Act & Assert
        mockMvc.perform(delete("/api/consultas/1/medicamentos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.diagnostico", is("Diagnóstico de teste")));
                
        verify(consultaService).removerMedicamento(1L, 1L);
    }
    
    @Test
    @DisplayName("Deve buscar consultas por animal com sucesso")
    void buscarPorAnimal_DeveRetornarConsultasDoAnimal() throws Exception {
        // Arrange
        List<ConsultaDTO> consultas = List.of(consultaDTO);
        when(consultaService.buscarPorAnimal(1L)).thenReturn(consultas);

        // Act & Assert
        mockMvc.perform(get("/api/consultas/animal/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
                
        verify(consultaService).buscarPorAnimal(1L);
    }
    
    @Test
    @DisplayName("Deve buscar consultas por veterinário com sucesso")
    void buscarPorVeterinario_DeveRetornarConsultasDoVeterinario() throws Exception {
        // Arrange
        List<ConsultaDTO> consultas = List.of(consultaDTO);
        when(consultaService.buscarPorVeterinario(1L)).thenReturn(consultas);

        // Act & Assert
        mockMvc.perform(get("/api/consultas/veterinario/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
                
        verify(consultaService).buscarPorVeterinario(1L);
    }
    
    @Test
    @DisplayName("Deve buscar consultas por status com sucesso")
    void buscarPorStatus_DeveRetornarConsultasComStatus() throws Exception {
        // Arrange
        List<ConsultaDTO> consultas = List.of(consultaDTO);
        when(consultaService.buscarPorStatus(any(Consulta.StatusConsulta.class))).thenReturn(consultas);

        // Act & Assert
        mockMvc.perform(get("/api/consultas/status/EM_ANDAMENTO"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
                
        verify(consultaService).buscarPorStatus(any(Consulta.StatusConsulta.class));
    }
    
    @Test
    @DisplayName("Deve buscar consultas por período com sucesso")
    void buscarPorPeriodo_DeveRetornarConsultasNoPeriodo() throws Exception {
        // Arrange
        List<ConsultaDTO> consultas = List.of(consultaDTO);
        when(consultaService.buscarPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(consultas);

        // Act & Assert
        mockMvc.perform(get("/api/consultas/periodo")
                .param("inicio", "2025-06-15T10:00:00")
                .param("fim", "2025-06-20T18:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
                
        verify(consultaService).buscarPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class));
    }
}
