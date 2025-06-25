package br.com.petshop.domain.repositories;

import br.com.petshop.domain.entities.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para operações de persistência de Agendamentos
 * 
 * @author Yan Werlley
 */
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    
    /**
     * Busca agendamentos por ID do animal
     * 
     * @param animalId ID do animal
     * @return Lista de agendamentos do animal
     */
    List<Agendamento> findByAnimalId(Long animalId);
    
    /**
     * Busca agendamentos por ID do veterinário
     * 
     * @param veterinarioId ID do veterinário
     * @return Lista de agendamentos do veterinário
     */
    List<Agendamento> findByVeterinarioId(Long veterinarioId);
    
    /**
     * Busca agendamentos por status
     * 
     * @param status Status do agendamento
     * @return Lista de agendamentos com o status especificado
     */
    List<Agendamento> findByStatus(Agendamento.StatusAgendamento status);
    
    /**
     * Busca agendamentos em um período específico
     * 
     * @param inicio Data/hora de início do período
     * @param fim Data/hora de fim do período
     * @return Lista de agendamentos no período
     */
    List<Agendamento> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
}
