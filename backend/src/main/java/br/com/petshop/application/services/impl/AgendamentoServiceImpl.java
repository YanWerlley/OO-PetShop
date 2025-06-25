package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.AgendamentoDTO;
import br.com.petshop.application.services.AgendamentoService;
import br.com.petshop.domain.entities.Agendamento;
import br.com.petshop.domain.entities.Agendamento.StatusAgendamento;
import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Usuario;
import br.com.petshop.domain.repositories.AgendamentoRepository;
import br.com.petshop.domain.repositories.AnimalRepository;
import br.com.petshop.domain.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação do serviço de Agendamento
 * 
 * @author Yan Werlley
 */
@Service
public class AgendamentoServiceImpl implements AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    @Transactional
    public AgendamentoDTO salvar(AgendamentoDTO agendamentoDTO) {
        Agendamento agendamento = converterParaEntidade(agendamentoDTO);
        agendamento = agendamentoRepository.save(agendamento);
        return converterParaDTO(agendamento);
    }

    @Override
    public AgendamentoDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com ID: " + id));
        return converterParaDTO(agendamento);
    }

    @Override
    public List<AgendamentoDTO> listarTodos() {
        return agendamentoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AgendamentoDTO atualizar(Long id, AgendamentoDTO agendamentoDTO) {
        if (!agendamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Agendamento não encontrado com ID: " + id);
        }
        
        agendamentoDTO.setId(id);
        Agendamento agendamento = converterParaEntidade(agendamentoDTO);
        agendamento = agendamentoRepository.save(agendamento);
        return converterParaDTO(agendamento);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Agendamento não encontrado com ID: " + id);
        }
        agendamentoRepository.deleteById(id);
    }

    @Override
    public List<AgendamentoDTO> buscarPorAnimal(Long animalId) {
        return agendamentoRepository.findByAnimalId(animalId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AgendamentoDTO> buscarPorVeterinario(Long veterinarioId) {
        return agendamentoRepository.findByVeterinarioId(veterinarioId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AgendamentoDTO> buscarPorStatus(Agendamento.StatusAgendamento status) {
        return agendamentoRepository.findByStatus(status).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AgendamentoDTO> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return agendamentoRepository.findByDataHoraBetween(inicio, fim).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AgendamentoDTO alterarStatus(Long id, Agendamento.StatusAgendamento status) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com ID: " + id));
        
        agendamento.setStatus(status);
        agendamento = agendamentoRepository.save(agendamento);
        return converterParaDTO(agendamento);
    }
    
    /**
     * Converte um DTO para entidade
     */
    private Agendamento converterParaEntidade(AgendamentoDTO dto) {
        Agendamento agendamento = new Agendamento();
        agendamento.setId(dto.getId());
        agendamento.setDataHora(dto.getDataHora());
        agendamento.setObservacoes(dto.getObservacoes());
        agendamento.setStatus(dto.getStatus());
        
        // Busca o animal pelo ID
        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + dto.getAnimalId()));
        agendamento.setAnimal(animal);
        
        // Busca o veterinário pelo ID
        Usuario veterinario = usuarioRepository.findById(dto.getVeterinarioId())
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado com ID: " + dto.getVeterinarioId()));
        agendamento.setVeterinario(veterinario);
        
        return agendamento;
    }
    
    /**
     * Converte uma entidade para DTO
     */
    private AgendamentoDTO converterParaDTO(Agendamento agendamento) {
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(agendamento.getId());
        dto.setDataHora(agendamento.getDataHora());
        dto.setObservacoes(agendamento.getObservacoes());
        dto.setStatus(agendamento.getStatus());
        
        // Dados do animal
        dto.setAnimalId(agendamento.getAnimal().getId());
        dto.setAnimalNome(agendamento.getAnimal().getNome());
        
        // Dados do cliente (dono do animal)
        if (agendamento.getAnimal().getCliente() != null) {
            dto.setClienteId(agendamento.getAnimal().getCliente().getId());
            dto.setClienteNome(agendamento.getAnimal().getCliente().getNome());
        }
        
        // Dados do veterinário
        dto.setVeterinarioId(agendamento.getVeterinario().getId());
        dto.setVeterinarioNome(agendamento.getVeterinario().getNome());
        
        return dto;
    }
}
