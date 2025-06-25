package br.com.petshop.application.services;

import br.com.petshop.application.dto.AgendamentoDTO;
import br.com.petshop.domain.entities.Agendamento;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface de serviço para operações relacionadas a Agendamentos
 * 
 * @author Yan Werlley
 */
public interface AgendamentoService {
    
    /**
     * Salva um novo agendamento
     * 
     * @param agendamentoDTO Dados do agendamento
     * @return Agendamento salvo com ID gerado
     */
    AgendamentoDTO salvar(AgendamentoDTO agendamentoDTO);
    
    /**
     * Busca um agendamento pelo ID
     * 
     * @param id ID do agendamento
     * @return Agendamento encontrado ou null
     */
    AgendamentoDTO buscarPorId(Long id);
    
    /**
     * Lista todos os agendamentos
     * 
     * @return Lista de agendamentos
     */
    List<AgendamentoDTO> listarTodos();
    
    /**
     * Atualiza um agendamento existente
     * 
     * @param id ID do agendamento
     * @param agendamentoDTO Novos dados do agendamento
     * @return Agendamento atualizado
     */
    AgendamentoDTO atualizar(Long id, AgendamentoDTO agendamentoDTO);
    
    /**
     * Exclui um agendamento
     * 
     * @param id ID do agendamento
     */
    void excluir(Long id);
    
    /**
     * Busca agendamentos por animal
     * 
     * @param animalId ID do animal
     * @return Lista de agendamentos do animal
     */
    List<AgendamentoDTO> buscarPorAnimal(Long animalId);
    
    /**
     * Busca agendamentos por veterinário
     * 
     * @param veterinarioId ID do veterinário
     * @return Lista de agendamentos do veterinário
     */
    List<AgendamentoDTO> buscarPorVeterinario(Long veterinarioId);
    
    /**
     * Busca agendamentos por status
     * 
     * @param status Status do agendamento
     * @return Lista de agendamentos com o status especificado
     */
    List<AgendamentoDTO> buscarPorStatus(Agendamento.StatusAgendamento status);
    
    /**
     * Busca agendamentos em um período específico
     * 
     * @param inicio Data/hora de início do período
     * @param fim Data/hora de fim do período
     * @return Lista de agendamentos no período
     */
    List<AgendamentoDTO> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
    
    /**
     * Altera o status de um agendamento
     * 
     * @param id ID do agendamento
     * @param status Novo status
     * @return Agendamento com status atualizado
     */
    AgendamentoDTO alterarStatus(Long id, Agendamento.StatusAgendamento status);
}
