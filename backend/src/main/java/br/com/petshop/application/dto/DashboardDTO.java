package br.com.petshop.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DTO para dados do dashboard
 * 
 * @author Yan Werlley
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    
    // Estatísticas gerais
    private Long totalConsultas;
    private Long totalAnimais;
    private Long totalClientes;
    private Long totalVeterinarios;
    private BigDecimal faturamentoTotal;
    private BigDecimal gastoTotal;
    
    // Estatísticas por período
    private Map<String, Long> consultasPorMes;
    private Map<String, BigDecimal> faturamentoPorMes;
    
    // Estatísticas por tipo
    private Map<String, Long> consultasPorEspecie;
    private Map<String, Long> consultasPorStatus;
    private Map<String, Long> distribuicaoPorEspecie;
    private Map<String, Long> distribuicaoPorRaca;
    
    // Agendamentos
    private Long agendamentosHoje;
    private Long agendamentosProximos;
    private Long agendamentosPendentes;
    private Long agendamentosConfirmados;
    private List<AgendamentoResumoDTO> proximosAgendamentos;
    
    // Tendências e históricos
    private Map<String, Long> tendenciaConsultas;
    private Map<String, BigDecimal> tendenciaFaturamento;
    private Map<String, Long> historicoConsultas;
    private Map<String, BigDecimal> historicoGastos;
    private Map<String, Long> ocupacaoVeterinarios;
    
    // Referência de data para relatórios por período
    private LocalDate dataReferencia;
    
    /**
     * DTO para resumo de agendamentos no dashboard
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AgendamentoResumoDTO {
        private Long id;
        private String dataHora;
        private String animalNome;
        private String clienteNome;
        private String veterinarioNome;
        private String status;
    }
}
