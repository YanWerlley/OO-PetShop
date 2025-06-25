package br.com.petshop.application.services;

import br.com.petshop.application.dto.RelatorioConsultasDTO;
import br.com.petshop.application.dto.RelatorioFaturamentoDTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Serviço para exportação de relatórios em diferentes formatos
 */
public interface ExportacaoService {
    
    /**
     * Exporta relatório de consultas para PDF
     * @param relatorio dados do relatório
     * @return array de bytes do PDF
     * @throws IOException em caso de erro na geração do PDF
     */
    ByteArrayOutputStream exportarRelatorioPDF(RelatorioConsultasDTO relatorio) throws IOException;
    
    /**
     * Exporta relatório de faturamento para PDF
     * @param relatorio dados do relatório
     * @return array de bytes do PDF
     * @throws IOException em caso de erro na geração do PDF
     */
    ByteArrayOutputStream exportarRelatorioPDF(RelatorioFaturamentoDTO relatorio) throws IOException;
    
    /**
     * Exporta relatório de consultas para Excel
     * @param relatorio dados do relatório
     * @return array de bytes do Excel
     * @throws IOException em caso de erro na geração do Excel
     */
    ByteArrayOutputStream exportarRelatorioExcel(RelatorioConsultasDTO relatorio) throws IOException;
    
    /**
     * Exporta relatório de faturamento para Excel
     * @param relatorio dados do relatório
     * @return array de bytes do Excel
     * @throws IOException em caso de erro na geração do Excel
     */
    ByteArrayOutputStream exportarRelatorioExcel(RelatorioFaturamentoDTO relatorio) throws IOException;
}
