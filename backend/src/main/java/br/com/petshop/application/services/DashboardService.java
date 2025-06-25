package br.com.petshop.application.services;

import br.com.petshop.application.dto.DashboardDTO;

import java.time.LocalDate;

/**
 * Interface de serviço para operações relacionadas ao Dashboard
 * 
 * @author Yan Werlley
 */
public interface DashboardService {
    
    /**
     * Obtém os dados do dashboard
     * 
     * @return Dados do dashboard
     */
    DashboardDTO obterDadosDashboard();
    
    /**
     * Obtém os dados do dashboard para um período específico
     * 
     * @param dataInicio Data de início do período
     * @param dataFim Data de fim do período
     * @return Dados do dashboard para o período
     */
    DashboardDTO obterDadosDashboardPorPeriodo(LocalDate dataInicio, LocalDate dataFim);
    
    /**
     * Gera dashboard com dados gerais do sistema
     * 
     * @return Dados do dashboard geral
     */
    DashboardDTO gerarDashboardGeral();
    
    /**
     * Gera dashboard com dados específicos para um veterinário
     * 
     * @param veterinarioId ID do veterinário
     * @return Dados do dashboard do veterinário
     */
    DashboardDTO gerarDashboardPorVeterinario(Long veterinarioId);
    
    /**
     * Gera dashboard com dados específicos para um cliente
     * 
     * @param clienteId ID do cliente
     * @return Dados do dashboard do cliente
     */
    DashboardDTO gerarDashboardPorCliente(Long clienteId);
    
    /**
     * Gera dashboard com dados específicos para uma data
     * 
     * @param data Data de referência
     * @return Dados do dashboard para a data especificada
     */
    DashboardDTO gerarDashboardPorData(LocalDate data);
}
