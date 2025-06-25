package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.ExameDTO;
import br.com.petshop.application.services.ExameService;
import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Consulta;
import br.com.petshop.domain.entities.Exame;
import br.com.petshop.domain.repositories.AnimalRepository;
import br.com.petshop.domain.repositories.ConsultaRepository;
import br.com.petshop.domain.repositories.ExameRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExameServiceImpl implements ExameService {

    @Autowired
    private ExameRepository exameRepository;
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public List<ExameDTO> buscarTodos() {
        return exameRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExameDTO buscarPorId(Long id) {
        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exame não encontrado com ID: " + id));
        return convertToDTO(exame);
    }

    @Override
    public List<ExameDTO> buscarPorAnimal(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + animalId));
        return exameRepository.findByAnimal(animal).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExameDTO> buscarPorConsulta(Long consultaId) {
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com ID: " + consultaId));
        return exameRepository.findByConsulta(consulta).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExameDTO> buscarPorTipo(String tipo) {
        return exameRepository.findByTipo(tipo).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExameDTO> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return exameRepository.findByDataRealizacaoBetween(inicio, fim).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExameDTO> buscarPorNome(String nome) {
        return exameRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ExameDTO salvar(ExameDTO exameDTO) {
        Exame exame = convertToEntity(exameDTO);
        Exame savedExame = exameRepository.save(exame);
        return convertToDTO(savedExame);
    }

    @Override
    @Transactional
    public ExameDTO atualizar(Long id, ExameDTO exameDTO) {
        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exame não encontrado com ID: " + id));
        
        Animal animal = animalRepository.findById(exameDTO.getAnimalId())
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + exameDTO.getAnimalId()));
        
        Consulta consulta = null;
        if (exameDTO.getConsultaId() != null) {
            consulta = consultaRepository.findById(exameDTO.getConsultaId())
                    .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com ID: " + exameDTO.getConsultaId()));
        }
        
        exame.setNome(exameDTO.getNome());
        exame.setTipo(exameDTO.getTipo());
        exame.setResultado(exameDTO.getResultado());
        exame.setDataRealizacao(exameDTO.getDataRealizacao());
        exame.setDataResultado(exameDTO.getDataResultado());
        exame.setObservacoes(exameDTO.getObservacoes());
        exame.setAnimal(animal);
        exame.setConsulta(consulta);
        
        Exame updatedExame = exameRepository.save(exame);
        return convertToDTO(updatedExame);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        if (!exameRepository.existsById(id)) {
            throw new EntityNotFoundException("Exame não encontrado com ID: " + id);
        }
        exameRepository.deleteById(id);
    }
    
    private ExameDTO convertToDTO(Exame exame) {
        ExameDTO dto = new ExameDTO();
        dto.setId(exame.getId());
        dto.setNome(exame.getNome());
        dto.setTipo(exame.getTipo());
        dto.setResultado(exame.getResultado());
        dto.setDataRealizacao(exame.getDataRealizacao());
        dto.setDataResultado(exame.getDataResultado());
        dto.setObservacoes(exame.getObservacoes());
        
        if (exame.getConsulta() != null) {
            dto.setConsultaId(exame.getConsulta().getId());
        }
        
        if (exame.getAnimal() != null) {
            dto.setAnimalId(exame.getAnimal().getId());
            dto.setAnimalNome(exame.getAnimal().getNome());
        }
        
        return dto;
    }
    
    private Exame convertToEntity(ExameDTO dto) {
        Exame exame = new Exame();
        exame.setId(dto.getId());
        exame.setNome(dto.getNome());
        exame.setTipo(dto.getTipo());
        exame.setResultado(dto.getResultado());
        exame.setDataRealizacao(dto.getDataRealizacao());
        exame.setDataResultado(dto.getDataResultado());
        exame.setObservacoes(dto.getObservacoes());
        
        if (dto.getAnimalId() != null) {
            Animal animal = animalRepository.findById(dto.getAnimalId())
                    .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + dto.getAnimalId()));
            exame.setAnimal(animal);
        }
        
        if (dto.getConsultaId() != null) {
            Consulta consulta = consultaRepository.findById(dto.getConsultaId())
                    .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com ID: " + dto.getConsultaId()));
            exame.setConsulta(consulta);
        }
        
        return exame;
    }
}
