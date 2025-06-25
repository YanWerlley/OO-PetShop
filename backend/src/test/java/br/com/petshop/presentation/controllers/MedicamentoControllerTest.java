package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.MedicamentoDTO;
import br.com.petshop.application.services.MedicamentoService;
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
 * Testes de integração para MedicamentoController
 * 
 * @author Yan Werlley
 */
@WebMvcTest(MedicamentoController.class)
class MedicamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private MedicamentoService medicamentoService;
    
    private MedicamentoDTO medicamentoDTO1;
    private MedicamentoDTO medicamentoDTO2;
    
    @BeforeEach
    void setUp() {
        // Configuração do DTO de medicamento 1
        medicamentoDTO1 = new MedicamentoDTO();
        medicamentoDTO1.setId(1L);
        medicamentoDTO1.setNome("Amoxicilina");
        medicamentoDTO1.setDosagem("50mg");
        medicamentoDTO1.setFrequencia("12/12h");
        medicamentoDTO1.setInstrucoes("Administrar com alimento");
        medicamentoDTO1.setDataInicio(LocalDate.of(2025, 6, 15));
        medicamentoDTO1.setDataFim(LocalDate.of(2025, 6, 25));
        medicamentoDTO1.setObservacoes("Completar o tratamento mesmo com melhora dos sintomas");
        medicamentoDTO1.setConsultaId(2L);
        medicamentoDTO1.setAnimalId(2L);
        medicamentoDTO1.setAnimalNome("Thor");
        
        // Configuração do DTO de medicamento 2
        medicamentoDTO2 = new MedicamentoDTO();
        medicamentoDTO2.setId(2L);
        medicamentoDTO2.setNome("Prednisolona");
        medicamentoDTO2.setDosagem("10mg");
        medicamentoDTO2.setFrequencia("24h");
        medicamentoDTO2.setInstrucoes("Administrar pela manhã");
        medicamentoDTO2.setDataInicio(LocalDate.of(2025, 6, 16));
        medicamentoDTO2.setDataFim(LocalDate.of(2025, 6, 23));
        medicamentoDTO2.setObservacoes("Reduzir a dose gradualmente");
        medicamentoDTO2.setConsultaId(2L);
        medicamentoDTO2.setAnimalId(2L);
        medicamentoDTO2.setAnimalNome("Thor");
    }
    
    @Test
    @DisplayName("Deve retornar todos os medicamentos com sucesso")
    void buscarTodos() throws Exception {
        // Arrange
        List<MedicamentoDTO> medicamentos = Arrays.asList(medicamentoDTO1, medicamentoDTO2);
        when(medicamentoService.buscarTodos()).thenReturn(medicamentos);
        
        // Act & Assert
        mockMvc.perform(get("/api/medicamentos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Amoxicilina")))
                .andExpect(jsonPath("$[1].nome", is("Prednisolona")));
        
        verify(medicamentoService, times(1)).buscarTodos();
    }
    
    @Test
    @DisplayName("Deve retornar lista vazia quando não há medicamentos")
    void buscarTodosVazio() throws Exception {
        // Arrange
        when(medicamentoService.buscarTodos()).thenReturn(Collections.emptyList());
        
        // Act & Assert
        mockMvc.perform(get("/api/medicamentos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
        
        verify(medicamentoService, times(1)).buscarTodos();
    }
    
    @Test
    @DisplayName("Deve retornar um medicamento por ID com sucesso")
    void buscarPorId() throws Exception {
        // Arrange
        when(medicamentoService.buscarPorId(1L)).thenReturn(medicamentoDTO1);
        
        // Act & Assert
        mockMvc.perform(get("/api/medicamentos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Amoxicilina")))
                .andExpect(jsonPath("$.dosagem", is("50mg")));
        
        verify(medicamentoService, times(1)).buscarPorId(1L);
    }
    
    @Test
    @DisplayName("Deve retornar medicamentos por animal com sucesso")
    void buscarPorAnimal() throws Exception {
        // Arrange
        when(medicamentoService.buscarPorAnimal(2L)).thenReturn(Arrays.asList(medicamentoDTO1, medicamentoDTO2));
        
        // Act & Assert
        mockMvc.perform(get("/api/medicamentos/animal/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Amoxicilina")))
                .andExpect(jsonPath("$[0].animalId", is(2)));
        
        verify(medicamentoService, times(1)).buscarPorAnimal(2L);
    }
    
    @Test
    @DisplayName("Deve retornar medicamentos por consulta com sucesso")
    void buscarPorConsulta() throws Exception {
        // Arrange
        when(medicamentoService.buscarPorConsulta(2L)).thenReturn(Arrays.asList(medicamentoDTO1, medicamentoDTO2));
        
        // Act & Assert
        mockMvc.perform(get("/api/medicamentos/consulta/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Amoxicilina")))
                .andExpect(jsonPath("$[0].consultaId", is(2)));
        
        verify(medicamentoService, times(1)).buscarPorConsulta(2L);
    }
    
    @Test
    @DisplayName("Deve retornar medicamentos por nome com sucesso")
    void buscarPorNome() throws Exception {
        // Arrange
        when(medicamentoService.buscarPorNome("Amox")).thenReturn(List.of(medicamentoDTO1));
        
        // Act & Assert
        mockMvc.perform(get("/api/medicamentos/nome/Amox"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Amoxicilina")));
        
        verify(medicamentoService, times(1)).buscarPorNome("Amox");
    }
    
    @Test
    @DisplayName("Deve retornar medicamentos por período de início com sucesso")
    void buscarPorPeriodoInicio() throws Exception {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 6, 15);
        LocalDate fim = LocalDate.of(2025, 6, 20);
        when(medicamentoService.buscarPorPeriodoInicio(inicio, fim)).thenReturn(Arrays.asList(medicamentoDTO1, medicamentoDTO2));
        
        // Act & Assert
        mockMvc.perform(get("/api/medicamentos/periodo-inicio")
                .param("inicio", "2025-06-15")
                .param("fim", "2025-06-20"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Amoxicilina")))
                .andExpect(jsonPath("$[1].nome", is("Prednisolona")));
        
        verify(medicamentoService, times(1)).buscarPorPeriodoInicio(inicio, fim);
    }
    
    @Test
    @DisplayName("Deve retornar medicamentos por período de fim com sucesso")
    void buscarPorPeriodoFim() throws Exception {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 6, 20);
        LocalDate fim = LocalDate.of(2025, 6, 30);
        when(medicamentoService.buscarPorPeriodoFim(inicio, fim)).thenReturn(Arrays.asList(medicamentoDTO1, medicamentoDTO2));
        
        // Act & Assert
        mockMvc.perform(get("/api/medicamentos/periodo-fim")
                .param("inicio", "2025-06-20")
                .param("fim", "2025-06-30"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Amoxicilina")))
                .andExpect(jsonPath("$[1].nome", is("Prednisolona")));
        
        verify(medicamentoService, times(1)).buscarPorPeriodoFim(inicio, fim);
    }
    
    @Test
    @DisplayName("Deve criar um medicamento com sucesso")
    void salvar() throws Exception {
        // Arrange
        MedicamentoDTO novoMedicamento = new MedicamentoDTO();
        novoMedicamento.setNome("Metronidazol");
        novoMedicamento.setDosagem("25mg");
        novoMedicamento.setFrequencia("12/12h");
        novoMedicamento.setInstrucoes("Administrar após alimentação");
        novoMedicamento.setDataInicio(LocalDate.of(2025, 6, 18));
        novoMedicamento.setDataFim(LocalDate.of(2025, 6, 28));
        novoMedicamento.setAnimalId(3L);
        
        when(medicamentoService.salvar(any(MedicamentoDTO.class))).thenAnswer(invocation -> {
            MedicamentoDTO dto = invocation.getArgument(0);
            dto.setId(3L);
            return dto;
        });
        
        // Act & Assert
        mockMvc.perform(post("/api/medicamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoMedicamento)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/medicamentos/3")))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nome", is("Metronidazol")));
        
        verify(medicamentoService, times(1)).salvar(any(MedicamentoDTO.class));
    }
    
    @Test
    @DisplayName("Deve atualizar um medicamento com sucesso")
    void atualizar() throws Exception {
        // Arrange
        medicamentoDTO1.setDosagem("75mg");
        medicamentoDTO1.setObservacoes("Aumentar dose conforme orientação");
        
        when(medicamentoService.atualizar(eq(1L), any(MedicamentoDTO.class))).thenReturn(medicamentoDTO1);
        
        // Act & Assert
        mockMvc.perform(put("/api/medicamentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicamentoDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.dosagem", is("75mg")))
                .andExpect(jsonPath("$.observacoes", is("Aumentar dose conforme orientação")));
        
        verify(medicamentoService, times(1)).atualizar(eq(1L), any(MedicamentoDTO.class));
    }
    
    @Test
    @DisplayName("Deve remover um medicamento com sucesso")
    void remover() throws Exception {
        // Arrange
        doNothing().when(medicamentoService).remover(1L);
        
        // Act & Assert
        mockMvc.perform(delete("/api/medicamentos/1"))
                .andExpect(status().isNoContent());
        
        verify(medicamentoService, times(1)).remover(1L);
    }
}
