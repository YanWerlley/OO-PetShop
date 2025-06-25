package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.RelatorioConsultasDTO;
import br.com.petshop.application.dto.RelatorioFaturamentoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para ExportacaoServiceImpl
 * 
 * @author Yan Werlley
 */
@ExtendWith(MockitoExtension.class)
class ExportacaoServiceImplTest {
    
    @InjectMocks
    private ExportacaoServiceImpl exportacaoService;
    
    private RelatorioConsultasDTO relatorioConsultas;
    private RelatorioFaturamentoDTO relatorioFaturamento;
    
    @BeforeEach
    void setUp() {
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
    }
    
    @Test
    @DisplayName("Deve gerar PDF de relatório de consultas com sucesso")
    void deveGerarPDFRelatorioConsultasComSucesso() {
        // Act
        ByteArrayOutputStream resultado = exportacaoService.exportarRelatorioConsultasPDF(relatorioConsultas);
        
        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.size() > 0);
        byte[] pdfBytes = resultado.toByteArray();
        
        // Verifica se o arquivo começa com a assinatura de PDF
        assertEquals('%', (char) pdfBytes[0]);
        assertEquals('P', (char) pdfBytes[1]);
        assertEquals('D', (char) pdfBytes[2]);
        assertEquals('F', (char) pdfBytes[3]);
    }
    
    @Test
    @DisplayName("Deve gerar PDF de relatório de faturamento com sucesso")
    void deveGerarPDFRelatorioFaturamentoComSucesso() {
        // Act
        ByteArrayOutputStream resultado = exportacaoService.exportarRelatorioFaturamentoPDF(relatorioFaturamento);
        
        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.size() > 0);
        byte[] pdfBytes = resultado.toByteArray();
        
        // Verifica se o arquivo começa com a assinatura de PDF
        assertEquals('%', (char) pdfBytes[0]);
        assertEquals('P', (char) pdfBytes[1]);
        assertEquals('D', (char) pdfBytes[2]);
        assertEquals('F', (char) pdfBytes[3]);
    }
}
