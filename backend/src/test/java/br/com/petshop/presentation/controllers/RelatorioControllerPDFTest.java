package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.RelatorioConsultasDTO;
import br.com.petshop.application.dto.RelatorioFaturamentoDTO;
import br.com.petshop.application.services.ExportacaoService;
import br.com.petshop.application.services.RelatorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes unitários para os endpoints de exportação de PDF do RelatorioController
 * 
 * @author Yan Werlley
 */
@ExtendWith(MockitoExtension.class)
class RelatorioControllerPDFTest {

    @Mock
    private RelatorioService relatorioService;
    
    @Mock
    private ExportacaoService exportacaoService;
    
    @InjectMocks
    private RelatorioController relatorioController;
    
    private MockMvc mockMvc;
    
    private RelatorioConsultasDTO relatorioConsultas;
    private RelatorioFaturamentoDTO relatorioFaturamento;
    private ByteArrayOutputStream pdfOutputStream;
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(relatorioController).build();
        
        // Configurar dados de teste para relatório de consultas
        relatorioConsultas = new RelatorioConsultasDTO();
        relatorioConsultas.setDataInicio(LocalDate.now().minusDays(30));
        relatorioConsultas.setDataFim(LocalDate.now());
        relatorioConsultas.setTotalConsultas(50L);
        relatorioConsultas.setDuracaoMediaConsultas(45.5);
        
        Map<Long, Long> consultasPorVeterinario = new HashMap<>();
        consultasPorVeterinario.put(1L, 20L);
        consultasPorVeterinario.put(2L, 30L);
        relatorioConsultas.setConsultasPorVeterinario(consultasPorVeterinario);
        
        Map<String, Long> consultasPorEspecie = new HashMap<>();
        consultasPorEspecie.put("Cachorro", 30L);
        consultasPorEspecie.put("Gato", 15L);
        consultasPorEspecie.put("Pássaro", 5L);
        relatorioConsultas.setConsultasPorEspecie(consultasPorEspecie);
        
        Map<LocalDate, Long> consultasPorDia = new HashMap<>();
        consultasPorDia.put(LocalDate.now().minusDays(1), 5L);
        consultasPorDia.put(LocalDate.now().minusDays(2), 8L);
        consultasPorDia.put(LocalDate.now().minusDays(3), 7L);
        relatorioConsultas.setConsultasPorDia(consultasPorDia);
        
        // Configurar dados de teste para relatório de faturamento
        relatorioFaturamento = new RelatorioFaturamentoDTO();
        relatorioFaturamento.setDataInicio(LocalDate.now().minusDays(30));
        relatorioFaturamento.setDataFim(LocalDate.now());
        relatorioFaturamento.setFaturamentoTotal(new BigDecimal("5000.00"));
        relatorioFaturamento.setTicketMedio(new BigDecimal("100.00"));
        
        Map<String, BigDecimal> faturamentoPorServico = new HashMap<>();
        faturamentoPorServico.put("Consulta", new BigDecimal("2000.00"));
        faturamentoPorServico.put("Exame", new BigDecimal("1500.00"));
        faturamentoPorServico.put("Vacina", new BigDecimal("1500.00"));
        relatorioFaturamento.setFaturamentoPorServico(faturamentoPorServico);
        
        Map<Long, BigDecimal> faturamentoPorVeterinario = new HashMap<>();
        faturamentoPorVeterinario.put(1L, new BigDecimal("2500.00"));
        faturamentoPorVeterinario.put(2L, new BigDecimal("2500.00"));
        relatorioFaturamento.setFaturamentoPorVeterinario(faturamentoPorVeterinario);
        
        Map<LocalDate, BigDecimal> faturamentoPorDia = new HashMap<>();
        faturamentoPorDia.put(LocalDate.now().minusDays(1), new BigDecimal("500.00"));
        faturamentoPorDia.put(LocalDate.now().minusDays(2), new BigDecimal("800.00"));
        faturamentoPorDia.put(LocalDate.now().minusDays(3), new BigDecimal("700.00"));
        relatorioFaturamento.setFaturamentoPorDia(faturamentoPorDia);
        
        // Configurar mock do PDF
        pdfOutputStream = new ByteArrayOutputStream();
        pdfOutputStream.write(new byte[] {'%', 'P', 'D', 'F', '-', '1', '.', '7'});
    }
    
    @Test
    @DisplayName("Deve exportar relatório de consultas em PDF")
    void deveExportarRelatorioConsultasPDF() throws Exception {
        // Arrange
        LocalDate dataInicio = LocalDate.now().minusDays(30);
        LocalDate dataFim = LocalDate.now();
        
        when(relatorioService.gerarRelatorioConsultas(any(LocalDate.class), any(LocalDate.class)))
            .thenReturn(relatorioConsultas);
        when(exportacaoService.exportarRelatorioConsultasPDF(any(RelatorioConsultasDTO.class)))
            .thenReturn(pdfOutputStream);
        
        // Act & Assert
        mockMvc.perform(get("/api/relatorios/consultas/pdf")
                .param("dataInicio", dataInicio.toString())
                .param("dataFim", dataFim.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(header().exists(HttpHeaders.CONTENT_DISPOSITION))
                .andExpect(content().bytes(pdfOutputStream.toByteArray()));
        
        verify(relatorioService).gerarRelatorioConsultas(dataInicio, dataFim);
        verify(exportacaoService).exportarRelatorioConsultasPDF(relatorioConsultas);
    }
    
    @Test
    @DisplayName("Deve exportar relatório de consultas por veterinário em PDF")
    void deveExportarRelatorioConsultasPorVeterinarioPDF() throws Exception {
        // Arrange
        Long veterinarioId = 1L;
        LocalDate dataInicio = LocalDate.now().minusDays(30);
        LocalDate dataFim = LocalDate.now();
        
        when(relatorioService.gerarRelatorioConsultasPorVeterinario(eq(veterinarioId), any(LocalDate.class), any(LocalDate.class)))
            .thenReturn(relatorioConsultas);
        when(exportacaoService.exportarRelatorioConsultasPDF(any(RelatorioConsultasDTO.class)))
            .thenReturn(pdfOutputStream);
        
        // Act & Assert
        mockMvc.perform(get("/api/relatorios/consultas/veterinario/{veterinarioId}/pdf", veterinarioId)
                .param("dataInicio", dataInicio.toString())
                .param("dataFim", dataFim.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(header().exists(HttpHeaders.CONTENT_DISPOSITION))
                .andExpect(content().bytes(pdfOutputStream.toByteArray()));
        
        verify(relatorioService).gerarRelatorioConsultasPorVeterinario(veterinarioId, dataInicio, dataFim);
        verify(exportacaoService).exportarRelatorioConsultasPDF(relatorioConsultas);
    }
    
    @Test
    @DisplayName("Deve exportar relatório de faturamento em PDF")
    void deveExportarRelatorioFaturamentoPDF() throws Exception {
        // Arrange
        LocalDate dataInicio = LocalDate.now().minusDays(30);
        LocalDate dataFim = LocalDate.now();
        
        when(relatorioService.gerarRelatorioFaturamento(any(LocalDate.class), any(LocalDate.class)))
            .thenReturn(relatorioFaturamento);
        when(exportacaoService.exportarRelatorioFaturamentoPDF(any(RelatorioFaturamentoDTO.class)))
            .thenReturn(pdfOutputStream);
        
        // Act & Assert
        mockMvc.perform(get("/api/relatorios/faturamento/pdf")
                .param("dataInicio", dataInicio.toString())
                .param("dataFim", dataFim.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(header().exists(HttpHeaders.CONTENT_DISPOSITION))
                .andExpect(content().bytes(pdfOutputStream.toByteArray()));
        
        verify(relatorioService).gerarRelatorioFaturamento(dataInicio, dataFim);
        verify(exportacaoService).exportarRelatorioFaturamentoPDF(relatorioFaturamento);
    }
}
