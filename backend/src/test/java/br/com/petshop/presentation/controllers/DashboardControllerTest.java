package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.DashboardDTO;
import br.com.petshop.application.services.DashboardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testes de integração para o DashboardController
 * 
 * @author Yan Werlley
 */
@WebMvcTest(DashboardController.class)
public class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DashboardService dashboardService;

    private DashboardDTO dashboardDTO;

    @BeforeEach
    void setUp() {
        // Configurar dashboard
        dashboardDTO = new DashboardDTO();
        dashboardDTO.setTotalConsultas(100L);
        dashboardDTO.setTotalAnimais(150L);
        dashboardDTO.setTotalClientes(50L);
        dashboardDTO.setTotalVeterinarios(10L);
        dashboardDTO.setFaturamentoTotal(new BigDecimal("15000.00"));
        
        // Configurar dados de consultas por mês
        Map<String, Long> consultasPorMes = new HashMap<>();
        consultasPorMes.put("01/2025", 20L);
        consultasPorMes.put("02/2025", 25L);
        consultasPorMes.put("03/2025", 30L);
        dashboardDTO.setConsultasPorMes(consultasPorMes);
        
        // Configurar dados de faturamento por mês
        Map<String, BigDecimal> faturamentoPorMes = new HashMap<>();
        faturamentoPorMes.put("01/2025", new BigDecimal("3000.00"));
        faturamentoPorMes.put("02/2025", new BigDecimal("4500.00"));
        faturamentoPorMes.put("03/2025", new BigDecimal("7500.00"));
        dashboardDTO.setFaturamentoPorMes(faturamentoPorMes);
        
        // Configurar dados de consultas por espécie
        Map<String, Long> consultasPorEspecie = new HashMap<>();
        consultasPorEspecie.put("Cachorro", 70L);
        consultasPorEspecie.put("Gato", 25L);
        consultasPorEspecie.put("Outros", 5L);
        dashboardDTO.setConsultasPorEspecie(consultasPorEspecie);
        
        // Configurar dados de consultas por status
        Map<String, Long> consultasPorStatus = new HashMap<>();
        consultasPorStatus.put("AGENDADA", 30L);
        consultasPorStatus.put("CONCLUIDA", 60L);
        consultasPorStatus.put("CANCELADA", 10L);
        dashboardDTO.setConsultasPorStatus(consultasPorStatus);
        
        // Configurar dados de agendamentos
        dashboardDTO.setAgendamentosHoje(5L);
        dashboardDTO.setAgendamentosProximos(15L);
        
        // Configurar lista de próximos agendamentos
        List<DashboardDTO.AgendamentoResumoDTO> proximosAgendamentos = new ArrayList<>();
        proximosAgendamentos.add(new DashboardDTO.AgendamentoResumoDTO(1L, "28/06/2025 10:00", "Rex", "João Silva", "Dr. Ana", "AGENDADO"));
        proximosAgendamentos.add(new DashboardDTO.AgendamentoResumoDTO(2L, "28/06/2025 14:30", "Luna", "Maria Oliveira", "Dr. Carlos", "CONFIRMADO"));
        dashboardDTO.setProximosAgendamentos(proximosAgendamentos);
    }

    @Test
    @DisplayName("Deve obter os dados do dashboard com sucesso")
    void obterDadosDashboard() throws Exception {
        when(dashboardService.obterDadosDashboard()).thenReturn(dashboardDTO);

        mockMvc.perform(get("/api/dashboard")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalConsultas", is(100)))
                .andExpect(jsonPath("$.totalAnimais", is(150)))
                .andExpect(jsonPath("$.totalClientes", is(50)))
                .andExpect(jsonPath("$.totalVeterinarios", is(10)))
                .andExpect(jsonPath("$.faturamentoTotal", is(15000.00)))
                .andExpect(jsonPath("$.agendamentosHoje", is(5)))
                .andExpect(jsonPath("$.agendamentosProximos", is(15)));
    }

    @Test
    @DisplayName("Deve obter os dados do dashboard por período com sucesso")
    void obterDadosDashboardPorPeriodo() throws Exception {
        LocalDate dataInicio = LocalDate.of(2025, 1, 1);
        LocalDate dataFim = LocalDate.of(2025, 3, 31);
        
        when(dashboardService.obterDadosDashboardPorPeriodo(dataInicio, dataFim)).thenReturn(dashboardDTO);

        mockMvc.perform(get("/api/dashboard/periodo")
                .param("dataInicio", "2025-01-01")
                .param("dataFim", "2025-03-31")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalConsultas", is(100)))
                .andExpect(jsonPath("$.totalAnimais", is(150)))
                .andExpect(jsonPath("$.consultasPorMes.01/2025", is(20)))
                .andExpect(jsonPath("$.consultasPorMes.02/2025", is(25)))
                .andExpect(jsonPath("$.consultasPorMes.03/2025", is(30)));
    }
}
