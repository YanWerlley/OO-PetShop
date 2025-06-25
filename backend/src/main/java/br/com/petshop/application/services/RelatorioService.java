package br.com.petshop.application.services;

import br.com.petshop.application.dto.RelatorioDTO;
import br.com.petshop.application.dto.RelatorioConsultasDTO;
import br.com.petshop.application.dto.RelatorioFaturamentoDTO;

import java.time.LocalDate;

/**
 * Interface de serviço para operações relacionadas a Relatórios
 * 
 * @author Yan Werlley
 */
public interface RelatorioService {
    
    /**
     * Gera um relatório baseado no tipo e período
     * 
     * @param tipoRelatorio Tipo de relatório a ser gerado
     * @param dataInicio Data de início do período
     * @param dataFim Data de fim do período
     * @param filtroAdicional Filtro adicional opcional (pode ser null)
     * @return Dados do relatório
     */
    RelatorioDTO gerarRelatorio(RelatorioDTO.TipoRelatorio tipoRelatorio, 
                               LocalDate dataInicio, 
                               LocalDate dataFim,
                               String filtroAdicional);
    
    /**
     * Exporta um relatório para um formato específico
     * 
     * @param relatorioDTO Dados do relatório
     * @param formato Formato de exportação (PDF, EXCEL, CSV)
     * @return Array de bytes com o conteúdo do arquivo exportado
     */
    byte[] exportarRelatorio(RelatorioDTO relatorioDTO, String formato);
    
    /**
     * Gera relatório de consultas para um período específico
     * 
     * @param dataInicio Data de início do período
     * @param dataFim Data de fim do período
     * @return Relatório de consultas
     */
    RelatorioConsultasDTO gerarRelatorioConsultas(LocalDate dataInicio, LocalDate dataFim);
    
    /**
     * Gera relatório de consultas para um veterinário específico em um período
     * 
     * @param veterinarioId ID do veterinário
     * @param dataInicio Data de início do período
     * @param dataFim Data de fim do período
     * @return Relatório de consultas do veterinário
     */
    RelatorioConsultasDTO gerarRelatorioConsultasPorVeterinario(Long veterinarioId, LocalDate dataInicio, LocalDate dataFim);
    
    /**
     * Gera relatório de faturamento para um período específico
     * 
     * @param dataInicio Data de início do período
     * @param dataFim Data de fim do período
     * @return Relatório de faturamento
     */
    RelatorioFaturamentoDTO gerarRelatorioFaturamento(LocalDate dataInicio, LocalDate dataFim);
}
