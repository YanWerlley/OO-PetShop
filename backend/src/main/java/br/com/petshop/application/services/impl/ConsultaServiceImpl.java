package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.ConsultaDTO;
import br.com.petshop.application.dto.ExameDTO;
import br.com.petshop.application.dto.MedicamentoDTO;
import br.com.petshop.application.services.ConsultaService;
import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Consulta;
import br.com.petshop.domain.entities.Usuario;
import br.com.petshop.domain.repositories.AnimalRepository;
import br.com.petshop.domain.repositories.ConsultaRepository;
import br.com.petshop.domain.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<ConsultaDTO> buscarTodas() {
        return consultaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ConsultaDTO buscarPorId(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com ID: " + id));
        return convertToDTO(consulta);
    }

    @Override
    public List<ConsultaDTO> buscarPorAnimal(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + animalId));
        return consultaRepository.findByAnimal(animal).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaDTO> buscarPorVeterinario(Long veterinarioId) {
        Usuario veterinario = usuarioRepository.findById(veterinarioId)
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado com ID: " + veterinarioId));
        return consultaRepository.findByVeterinario(veterinario).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaDTO> buscarPorStatus(String status) {
        Consulta.StatusConsulta statusEnum = Consulta.StatusConsulta.valueOf(status);
        return consultaRepository.findByStatus(statusEnum).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConsultaDTO> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return consultaRepository.findByDataHoraBetween(inicio, fim).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ConsultaDTO agendar(ConsultaDTO consultaDTO) {
        Consulta consulta = convertToEntity(consultaDTO);
        consulta.setStatus(Consulta.StatusConsulta.AGENDADA);
        Consulta savedConsulta = consultaRepository.save(consulta);
        return convertToDTO(savedConsulta);
    }

    @Override
    @Transactional
    public ConsultaDTO atualizar(Long id, ConsultaDTO consultaDTO) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com ID: " + id));
        
        Animal animal = animalRepository.findById(consultaDTO.getAnimalId())
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + consultaDTO.getAnimalId()));
        
        Usuario veterinario = usuarioRepository.findById(consultaDTO.getVeterinarioId())
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado com ID: " + consultaDTO.getVeterinarioId()));
        
        consulta.setDataHora(consultaDTO.getDataHora());
        consulta.setMotivo(consultaDTO.getMotivo());
        consulta.setDiagnostico(consultaDTO.getDiagnostico());
        consulta.setTratamento(consultaDTO.getTratamento());
        consulta.setObservacoes(consultaDTO.getObservacoes());
        if (consultaDTO.getStatus() != null && !consultaDTO.getStatus().isEmpty()) {
            try {
                consulta.setStatus(Consulta.StatusConsulta.valueOf(consultaDTO.getStatus()));
            } catch (IllegalArgumentException e) {
                // Se o status não for válido, mantém o status atual
                // ou define como AGENDADA se for uma nova consulta
                if (consulta.getStatus() == null) {
                    consulta.setStatus(Consulta.StatusConsulta.AGENDADA);
                }
            }
        }
        consulta.setAnimal(animal);
        consulta.setVeterinario(veterinario);
        
        Consulta updatedConsulta = consultaRepository.save(consulta);
        return convertToDTO(updatedConsulta);
    }

    @Override
    @Transactional
    public void cancelar(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com ID: " + id));
        consulta.setStatus(Consulta.StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        if (!consultaRepository.existsById(id)) {
            throw new EntityNotFoundException("Consulta não encontrada com ID: " + id);
        }
        consultaRepository.deleteById(id);
    }
    
    @Override
    @Transactional
    public ConsultaDTO iniciarConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com ID: " + id));
        
        // Verifica se a consulta está agendada antes de iniciar
        if (consulta.getStatus() != Consulta.StatusConsulta.AGENDADA) {
            throw new IllegalStateException("Apenas consultas com status AGENDADA podem ser iniciadas");
        }
        
        // Atualiza o status para REALIZADA (em andamento)
        consulta.setStatus(Consulta.StatusConsulta.REALIZADA);
        
        // Salva a consulta atualizada
        Consulta updatedConsulta = consultaRepository.save(consulta);
        return convertToDTO(updatedConsulta);
    }
    
    @Override
    @Transactional
    public ConsultaDTO finalizarConsulta(Long id, ConsultaDTO consultaDTO) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com ID: " + id));
        
        // Atualiza os dados da consulta com os valores do DTO
        consulta.setDiagnostico(consultaDTO.getDiagnostico());
        consulta.setTratamento(consultaDTO.getTratamento());
        consulta.setObservacoes(consultaDTO.getObservacoes());
        consulta.setValor(consultaDTO.getValor());
        
        // Atualiza o status para REALIZADA (finalizada)
        consulta.setStatus(Consulta.StatusConsulta.REALIZADA);
        
        // Salva a consulta atualizada
        Consulta updatedConsulta = consultaRepository.save(consulta);
        return convertToDTO(updatedConsulta);
    }
    
    @Override
    public ConsultaDTO buscarConsultaPorId(Long id) {
        // Este método é essencialmente o mesmo que buscarPorId, mas foi adicionado
        // para manter compatibilidade com os testes existentes
        return buscarPorId(id);
    }
    
    private ConsultaDTO convertToDTO(Consulta consulta) {
        ConsultaDTO dto = new ConsultaDTO();
        dto.setId(consulta.getId());
        dto.setDataHora(consulta.getDataHora());
        dto.setMotivo(consulta.getMotivo());
        dto.setDiagnostico(consulta.getDiagnostico());
        dto.setTratamento(consulta.getTratamento());
        dto.setObservacoes(consulta.getObservacoes());
        dto.setValor(consulta.getValor());
        
        if (consulta.getStatus() != null) {
            dto.setStatus(consulta.getStatus().name());
        }
        
        if (consulta.getAnimal() != null) {
            dto.setAnimalId(consulta.getAnimal().getId());
            dto.setAnimalNome(consulta.getAnimal().getNome());
        }
        
        if (consulta.getVeterinario() != null) {
            dto.setVeterinarioId(consulta.getVeterinario().getId());
            dto.setVeterinarioNome(consulta.getVeterinario().getNome());
        }
        
        // Exames e medicamentos seriam convertidos aqui em um cenário completo
        
        return dto;
    }
    
    private Consulta convertToEntity(ConsultaDTO dto) {
        Consulta consulta = new Consulta();
        consulta.setId(dto.getId());
        consulta.setDataHora(dto.getDataHora());
        consulta.setMotivo(dto.getMotivo());
        consulta.setDiagnostico(dto.getDiagnostico());
        consulta.setTratamento(dto.getTratamento());
        consulta.setObservacoes(dto.getObservacoes());
        consulta.setValor(dto.getValor());
        
        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            try {
                consulta.setStatus(Consulta.StatusConsulta.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException e) {
                // Se o status não for válido, define como AGENDADA por padrão
                consulta.setStatus(Consulta.StatusConsulta.AGENDADA);
            }
        }
        
        if (dto.getAnimalId() != null) {
            Animal animal = animalRepository.findById(dto.getAnimalId())
                    .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + dto.getAnimalId()));
            consulta.setAnimal(animal);
        }
        
        if (dto.getVeterinarioId() != null) {
            Usuario veterinario = usuarioRepository.findById(dto.getVeterinarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado com ID: " + dto.getVeterinarioId()));
            consulta.setVeterinario(veterinario);
        }
        
        return consulta;
    }
}
