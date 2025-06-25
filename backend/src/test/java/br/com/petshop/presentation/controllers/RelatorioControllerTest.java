package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.RelatorioConsultasDTO;
import br.com.petshop.application.dto.RelatorioFaturamentoDTO;
import br.com.petshop.application.services.RelatorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RelatorioControllerTest {

    @Mock
    private RelatorioService relatorioService;

    @InjectMocks
    private RelatorioController relatorioController;

    private RelatorioConsultasDTO relatorioConsultasDTO;
    private RelatorioFaturamentoDTO relatorioFaturamentoDTO;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    @BeforeEach
    void setUp() {
        // Configurar datas
        dataInicio = LocalDate.now().minusDays(30);
        dataFim = LocalDate.now();

        // Configurar relatório de consultas
        relatorioConsultasDTO = new RelatorioConsultasDTO();
        relatorioConsultasDTO.setDataInicio(dataInicio);
        relatorioConsultasDTO.setDataFim(dataFim);
        relatorioConsultasDTO.setTotalConsultas(10L);
        relatorioConsultasDTO.setConsultasPorVeterinario(new HashMap<>());
        relatorioConsultasDTO.setConsultasPorEspecie(new HashMap<>());
        relatorioConsultasDTO.setConsultasPorDia(new HashMap<>());
        
        Map<Long, Long> consultasPorVeterinario = new HashMap<>();
        consultasPorVeterinario.put(1L, 5L);
        consultasPorVeterinario.put(2L, 5L);
        relatorioConsultasDTO.setConsultasPorVeterinario(consultasPorVeterinario);
        
        Map<String, Long> consultasPorEspecie = new HashMap<>();
        consultasPorEspecie.put("Cachorro", 7L);
        consultasPorEspecie.put("Gato", 3L);
        relatorioConsultasDTO.setConsultasPorEspecie(consultasPorEspecie);

        // Configurar relatório de faturamento
        relatorioFaturamentoDTO = new RelatorioFaturamentoDTO();
        relatorioFaturamentoDTO.setDataInicio(dataInicio);
        relatorioFaturamentoDTO.setDataFim(dataFim);
        relatorioFaturamentoDTO.setFaturamentoTotal(new BigDecimal("1500.00"));
        relatorioFaturamentoDTO.setFaturamentoPorServico(new HashMap<>());
        relatorioFaturamentoDTO.setFaturamentoPorVeterinario(new HashMap<>());
        relatorioFaturamentoDTO.setFaturamentoPorDia(new HashMap<>());
        
        Map<String, BigDecimal> faturamentoPorServico = new HashMap<>();
        faturamentoPorServico.put("Consulta", new BigDecimal("500.00"));
        faturamentoPorServico.put("Exames", new BigDecimal("700.00"));
        faturamentoPorServico.put("Medicamentos", new BigDecimal("300.00"));
        relatorioFaturamentoDTO.setFaturamentoPorServico(faturamentoPorServico);
        
        Map<Long, BigDecimal> faturamentoPorVeterinario = new HashMap<>();
        faturamentoPorVeterinario.put(1L, new BigDecimal("800.00"));
        faturamentoPorVeterinario.put(2L, new BigDecimal("700.00"));
        relatorioFaturamentoDTO.setFaturamentoPorVeterinario(faturamentoPorVeterinario);
    }

    @Test
    void gerarRelatorioConsultas_DeveRetornarRelatorioComSucesso() {
        // Arrange
        when(relatorioService.gerarRelatorioConsultas(dataInicio, dataFim)).thenReturn(relatorioConsultasDTO);

        // Act
        ResponseEntity<RelatorioConsultasDTO> response = relatorioController.gerarRelatorioConsultas(dataInicio, dataFim);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(10L, response.getBody().getTotalConsultas());
        assertEquals(dataInicio, response.getBody().getDataInicio());
        assertEquals(dataFim, response.getBody().getDataFim());
        verify(relatorioService).gerarRelatorioConsultas(dataInicio, dataFim);
    }

    @Test
    void gerarRelatorioConsultasPorVeterinario_DeveRetornarRelatorioComSucesso() {
        // Arrange
        when(relatorioService.gerarRelatorioConsultasPorVeterinario(eq(1L), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(relatorioConsultasDTO);

        // Act
        ResponseEntity<RelatorioConsultasDTO> response = relatorioController.gerarRelatorioConsultasPorVeterinario(1L, dataInicio, dataFim);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(10L, response.getBody().getTotalConsultas());
        assertEquals(dataInicio, response.getBody().getDataInicio());
        assertEquals(dataFim, response.getBody().getDataFim());
        verify(relatorioService).gerarRelatorioConsultasPorVeterinario(eq(1L), any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void gerarRelatorioConsultasPorEspecie_DeveRetornarRelatorioComSucesso() {
        // Arrange
        when(relatorioService.gerarRelatorioConsultasPorEspecie(eq("Cachorro"), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(relatorioConsultasDTO);

        // Act
        ResponseEntity<RelatorioConsultasDTO> response = relatorioController.gerarRelatorioConsultasPorEspecie("Cachorro", dataInicio, dataFim);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(10L, response.getBody().getTotalConsultas());
        assertEquals(dataInicio, response.getBody().getDataInicio());
        assertEquals(dataFim, response.getBody().getDataFim());
        verify(relatorioService).gerarRelatorioConsultasPorEspecie(eq("Cachorro"), any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void gerarRelatorioFaturamento_DeveRetornarRelatorioComSucesso() {
        // Arrange
        when(relatorioService.gerarRelatorioFaturamento(dataInicio, dataFim)).thenReturn(relatorioFaturamentoDTO);

        // Act
        ResponseEntity<RelatorioFaturamentoDTO> response = relatorioController.gerarRelatorioFaturamento(dataInicio, dataFim);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("1500.00"), response.getBody().getFaturamentoTotal());
        assertEquals(dataInicio, response.getBody().getDataInicio());
        assertEquals(dataFim, response.getBody().getDataFim());
        verify(relatorioService).gerarRelatorioFaturamento(dataInicio, dataFim);
    }

    @Test
    void gerarRelatorioFaturamentoPorVeterinario_DeveRetornarRelatorioComSucesso() {
        // Arrange
        when(relatorioService.gerarRelatorioFaturamentoPorVeterinario(eq(1L), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(relatorioFaturamentoDTO);

        // Act
        ResponseEntity<RelatorioFaturamentoDTO> response = relatorioController.gerarRelatorioFaturamentoPorVeterinario(1L, dataInicio, dataFim);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("1500.00"), response.getBody().getFaturamentoTotal());
        assertEquals(dataInicio, response.getBody().getDataInicio());
        assertEquals(dataFim, response.getBody().getDataFim());
        verify(relatorioService).gerarRelatorioFaturamentoPorVeterinario(eq(1L), any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void gerarRelatorioFaturamentoPorServico_DeveRetornarRelatorioComSucesso() {
        // Arrange
        when(relatorioService.gerarRelatorioFaturamentoPorServico(eq("Consulta"), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(relatorioFaturamentoDTO);

        // Act
        ResponseEntity<RelatorioFaturamentoDTO> response = relatorioController.gerarRelatorioFaturamentoPorServico("Consulta", dataInicio, dataFim);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("1500.00"), response.getBody().getFaturamentoTotal());
        assertEquals(dataInicio, response.getBody().getDataInicio());
        assertEquals(dataFim, response.getBody().getDataFim());
        verify(relatorioService).gerarRelatorioFaturamentoPorServico(eq("Consulta"), any(LocalDate.class), any(LocalDate.class));
    }
}
