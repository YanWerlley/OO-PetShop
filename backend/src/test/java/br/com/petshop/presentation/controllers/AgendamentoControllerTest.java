package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.AgendamentoDTO;
import br.com.petshop.application.services.AgendamentoService;
import br.com.petshop.domain.entities.Agendamento;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração para o AgendamentoController
 * 
 * @author Yan Werlley
 */
@WebMvcTest(AgendamentoController.class)
public class AgendamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AgendamentoService agendamentoService;

    @Test
    @DisplayName("Deve criar um agendamento com sucesso")
    void criar() throws Exception {
        AgendamentoDTO novoAgendamento = new AgendamentoDTO();
        novoAgendamento.setDataHora(LocalDateTime.now().plusDays(1));
        novoAgendamento.setObservacoes("Consulta de rotina");
        novoAgendamento.setAnimalId(1L);
        novoAgendamento.setVeterinarioId(2L);
        novoAgendamento.setStatus(Agendamento.StatusAgendamento.AGENDADO);

        when(agendamentoService.salvar(any(AgendamentoDTO.class))).thenAnswer(invocation -> {
            AgendamentoDTO dto = invocation.getArgument(0);
            dto.setId(1L);
            return dto;
        });

        mockMvc.perform(post("/api/agendamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoAgendamento)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/agendamentos/1")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.animalId", is(1)))
                .andExpect(jsonPath("$.veterinarioId", is(2)))
                .andExpect(jsonPath("$.status", is("AGENDADO")));

        verify(agendamentoService, times(1)).salvar(any(AgendamentoDTO.class));
    }

    @Test
    @DisplayName("Deve buscar um agendamento pelo ID")
    void buscarPorId() throws Exception {
        AgendamentoDTO agendamento = new AgendamentoDTO();
        agendamento.setId(1L);
        agendamento.setDataHora(LocalDateTime.now().plusDays(1));
        agendamento.setObservacoes("Consulta de rotina");
        agendamento.setAnimalId(1L);
        agendamento.setAnimalNome("Rex");
        agendamento.setVeterinarioId(2L);
        agendamento.setVeterinarioNome("Dr. João");
        agendamento.setStatus(Agendamento.StatusAgendamento.AGENDADO);

        when(agendamentoService.buscarPorId(1L)).thenReturn(agendamento);

        mockMvc.perform(get("/api/agendamentos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.animalId", is(1)))
                .andExpect(jsonPath("$.animalNome", is("Rex")))
                .andExpect(jsonPath("$.veterinarioId", is(2)))
                .andExpect(jsonPath("$.veterinarioNome", is("Dr. João")))
                .andExpect(jsonPath("$.status", is("AGENDADO")));

        verify(agendamentoService, times(1)).buscarPorId(1L);
    }

    @Test
    @DisplayName("Deve listar todos os agendamentos")
    void listarTodos() throws Exception {
        AgendamentoDTO agendamento1 = new AgendamentoDTO();
        agendamento1.setId(1L);
        agendamento1.setDataHora(LocalDateTime.now().plusDays(1));
        agendamento1.setAnimalId(1L);
        agendamento1.setVeterinarioId(2L);
        agendamento1.setStatus(Agendamento.StatusAgendamento.AGENDADO);

        AgendamentoDTO agendamento2 = new AgendamentoDTO();
        agendamento2.setId(2L);
        agendamento2.setDataHora(LocalDateTime.now().plusDays(2));
        agendamento2.setAnimalId(3L);
        agendamento2.setVeterinarioId(2L);
        agendamento2.setStatus(Agendamento.StatusAgendamento.CONFIRMADO);

        List<AgendamentoDTO> agendamentos = Arrays.asList(agendamento1, agendamento2);

        when(agendamentoService.listarTodos()).thenReturn(agendamentos);

        mockMvc.perform(get("/api/agendamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].status", is("AGENDADO")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].status", is("CONFIRMADO")));

        verify(agendamentoService, times(1)).listarTodos();
    }

    @Test
    @DisplayName("Deve atualizar um agendamento")
    void atualizar() throws Exception {
        AgendamentoDTO agendamentoAtualizado = new AgendamentoDTO();
        agendamentoAtualizado.setId(1L);
        agendamentoAtualizado.setDataHora(LocalDateTime.now().plusDays(3));
        agendamentoAtualizado.setObservacoes("Consulta remarcada");
        agendamentoAtualizado.setAnimalId(1L);
        agendamentoAtualizado.setVeterinarioId(2L);
        agendamentoAtualizado.setStatus(Agendamento.StatusAgendamento.CONFIRMADO);

        when(agendamentoService.atualizar(eq(1L), any(AgendamentoDTO.class))).thenReturn(agendamentoAtualizado);

        mockMvc.perform(put("/api/agendamentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agendamentoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.observacoes", is("Consulta remarcada")))
                .andExpect(jsonPath("$.status", is("CONFIRMADO")));

        verify(agendamentoService, times(1)).atualizar(eq(1L), any(AgendamentoDTO.class));
    }

    @Test
    @DisplayName("Deve excluir um agendamento")
    void excluir() throws Exception {
        doNothing().when(agendamentoService).excluir(1L);

        mockMvc.perform(delete("/api/agendamentos/1"))
                .andExpect(status().isNoContent());

        verify(agendamentoService, times(1)).excluir(1L);
    }

    @Test
    @DisplayName("Deve buscar agendamentos por animal")
    void buscarPorAnimal() throws Exception {
        AgendamentoDTO agendamento = new AgendamentoDTO();
        agendamento.setId(1L);
        agendamento.setAnimalId(1L);
        agendamento.setAnimalNome("Rex");
        agendamento.setStatus(Agendamento.StatusAgendamento.AGENDADO);

        List<AgendamentoDTO> agendamentos = Arrays.asList(agendamento);

        when(agendamentoService.buscarPorAnimal(1L)).thenReturn(agendamentos);

        mockMvc.perform(get("/api/agendamentos/animal/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].animalId", is(1)))
                .andExpect(jsonPath("$[0].animalNome", is("Rex")));

        verify(agendamentoService, times(1)).buscarPorAnimal(1L);
    }

    @Test
    @DisplayName("Deve buscar agendamentos por veterinário")
    void buscarPorVeterinario() throws Exception {
        AgendamentoDTO agendamento = new AgendamentoDTO();
        agendamento.setId(1L);
        agendamento.setVeterinarioId(2L);
        agendamento.setVeterinarioNome("Dr. João");
        agendamento.setStatus(Agendamento.StatusAgendamento.AGENDADO);

        List<AgendamentoDTO> agendamentos = Arrays.asList(agendamento);

        when(agendamentoService.buscarPorVeterinario(2L)).thenReturn(agendamentos);

        mockMvc.perform(get("/api/agendamentos/veterinario/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].veterinarioId", is(2)))
                .andExpect(jsonPath("$[0].veterinarioNome", is("Dr. João")));

        verify(agendamentoService, times(1)).buscarPorVeterinario(2L);
    }

    @Test
    @DisplayName("Deve buscar agendamentos por status")
    void buscarPorStatus() throws Exception {
        AgendamentoDTO agendamento = new AgendamentoDTO();
        agendamento.setId(1L);
        agendamento.setStatus(Agendamento.StatusAgendamento.CONFIRMADO);

        List<AgendamentoDTO> agendamentos = Arrays.asList(agendamento);

        when(agendamentoService.buscarPorStatus(Agendamento.StatusAgendamento.CONFIRMADO)).thenReturn(agendamentos);

        mockMvc.perform(get("/api/agendamentos/status/CONFIRMADO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].status", is("CONFIRMADO")));

        verify(agendamentoService, times(1)).buscarPorStatus(Agendamento.StatusAgendamento.CONFIRMADO);
    }

    @Test
    @DisplayName("Deve alterar o status de um agendamento")
    void alterarStatus() throws Exception {
        AgendamentoDTO agendamento = new AgendamentoDTO();
        agendamento.setId(1L);
        agendamento.setStatus(Agendamento.StatusAgendamento.CONCLUIDO);

        when(agendamentoService.alterarStatus(1L, Agendamento.StatusAgendamento.CONCLUIDO)).thenReturn(agendamento);

        mockMvc.perform(patch("/api/agendamentos/1/status")
                .param("status", "CONCLUIDO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("CONCLUIDO")));

        verify(agendamentoService, times(1)).alterarStatus(1L, Agendamento.StatusAgendamento.CONCLUIDO);
    }
}
