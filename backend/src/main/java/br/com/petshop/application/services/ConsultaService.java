package br.com.petshop.application.services;

import br.com.petshop.application.dto.ConsultaDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaService {
    List<ConsultaDTO> buscarTodas();
    ConsultaDTO buscarPorId(Long id);
    List<ConsultaDTO> buscarPorAnimal(Long animalId);
    List<ConsultaDTO> buscarPorVeterinario(Long veterinarioId);
    List<ConsultaDTO> buscarPorStatus(String status);
    List<ConsultaDTO> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
    ConsultaDTO agendar(ConsultaDTO consultaDTO);
    ConsultaDTO atualizar(Long id, ConsultaDTO consultaDTO);
    void cancelar(Long id);
    void remover(Long id);
    
    // Métodos adicionais para gerenciamento do fluxo de consultas
    ConsultaDTO iniciarConsulta(Long id);
    ConsultaDTO finalizarConsulta(Long id, ConsultaDTO consultaDTO);
    ConsultaDTO buscarConsultaPorId(Long id);
}
