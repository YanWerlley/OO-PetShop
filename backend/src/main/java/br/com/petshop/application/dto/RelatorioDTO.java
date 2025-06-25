package br.com.petshop.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * DTO para dados de relatórios
 * 
 * @author Yan Werlley
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioDTO {
    
    private String tipoRelatorio;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String filtroAdicional;
    
    // Dados gerais
    private Long totalRegistros;
    private BigDecimal valorTotal;
    
    // Dados detalhados
    private List<Map<String, Object>> dadosDetalhados;
    
    // Agrupamentos
    private Map<String, Long> contagemPorCategoria;
    private Map<String, BigDecimal> valorPorCategoria;
    
    /**
     * Enum para tipos de relatório disponíveis
     */
    public enum TipoRelatorio {
        CONSULTAS,
        MEDICAMENTOS,
        EXAMES,
        VACINAS,
        CLIENTES,
        ANIMAIS,
        FATURAMENTO
    }
}
