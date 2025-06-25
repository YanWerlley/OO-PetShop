package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.MedicamentoDTO;
import br.com.petshop.application.services.MedicamentoService;
import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Consulta;
import br.com.petshop.domain.entities.Medicamento;
import br.com.petshop.domain.repositories.AnimalRepository;
import br.com.petshop.domain.repositories.ConsultaRepository;
import br.com.petshop.domain.repositories.MedicamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public List<MedicamentoDTO> buscarTodos() {
        return medicamentoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MedicamentoDTO buscarPorId(Long id) {
        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento não encontrado com ID: " + id));
        return convertToDTO(medicamento);
    }

    @Override
    public List<MedicamentoDTO> buscarPorAnimal(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + animalId));
        return medicamentoRepository.findByAnimal(animal).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicamentoDTO> buscarPorConsulta(Long consultaId) {
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com ID: " + consultaId));
        return medicamentoRepository.findByConsulta(consulta).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicamentoDTO> buscarPorNome(String nome) {
        return medicamentoRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicamentoDTO> buscarPorPeriodoInicio(LocalDate inicio, LocalDate fim) {
        return medicamentoRepository.findByDataInicioBetween(inicio, fim).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicamentoDTO> buscarPorPeriodoFim(LocalDate inicio, LocalDate fim) {
        return medicamentoRepository.findByDataFimBetween(inicio, fim).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MedicamentoDTO salvar(MedicamentoDTO medicamentoDTO) {
        Medicamento medicamento = convertToEntity(medicamentoDTO);
        Medicamento savedMedicamento = medicamentoRepository.save(medicamento);
        return convertToDTO(savedMedicamento);
    }

    @Override
    @Transactional
    public MedicamentoDTO atualizar(Long id, MedicamentoDTO medicamentoDTO) {
        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento não encontrado com ID: " + id));
        
        Animal animal = animalRepository.findById(medicamentoDTO.getAnimalId())
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + medicamentoDTO.getAnimalId()));
        
        Consulta consulta = null;
        if (medicamentoDTO.getConsultaId() != null) {
            consulta = consultaRepository.findById(medicamentoDTO.getConsultaId())
                    .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com ID: " + medicamentoDTO.getConsultaId()));
        }
        
        medicamento.setNome(medicamentoDTO.getNome());
        medicamento.setDosagem(medicamentoDTO.getDosagem());
        medicamento.setFrequencia(medicamentoDTO.getFrequencia());
        medicamento.setInstrucoes(medicamentoDTO.getInstrucoes());
        medicamento.setDataInicio(medicamentoDTO.getDataInicio());
        medicamento.setDataFim(medicamentoDTO.getDataFim());
        medicamento.setObservacoes(medicamentoDTO.getObservacoes());
        medicamento.setAnimal(animal);
        medicamento.setConsulta(consulta);
        
        Medicamento updatedMedicamento = medicamentoRepository.save(medicamento);
        return convertToDTO(updatedMedicamento);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        if (!medicamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Medicamento não encontrado com ID: " + id);
        }
        medicamentoRepository.deleteById(id);
    }
    
    private MedicamentoDTO convertToDTO(Medicamento medicamento) {
        MedicamentoDTO dto = new MedicamentoDTO();
        dto.setId(medicamento.getId());
        dto.setNome(medicamento.getNome());
        dto.setDosagem(medicamento.getDosagem());
        dto.setFrequencia(medicamento.getFrequencia());
        dto.setInstrucoes(medicamento.getInstrucoes());
        dto.setDataInicio(medicamento.getDataInicio());
        dto.setDataFim(medicamento.getDataFim());
        dto.setObservacoes(medicamento.getObservacoes());
        
        if (medicamento.getConsulta() != null) {
            dto.setConsultaId(medicamento.getConsulta().getId());
        }
        
        if (medicamento.getAnimal() != null) {
            dto.setAnimalId(medicamento.getAnimal().getId());
            dto.setAnimalNome(medicamento.getAnimal().getNome());
        }
        
        return dto;
    }
    
    private Medicamento convertToEntity(MedicamentoDTO dto) {
        Medicamento medicamento = new Medicamento();
        medicamento.setId(dto.getId());
        medicamento.setNome(dto.getNome());
        medicamento.setDosagem(dto.getDosagem());
        medicamento.setFrequencia(dto.getFrequencia());
        medicamento.setInstrucoes(dto.getInstrucoes());
        medicamento.setDataInicio(dto.getDataInicio());
        medicamento.setDataFim(dto.getDataFim());
        medicamento.setObservacoes(dto.getObservacoes());
        
        if (dto.getAnimalId() != null) {
            Animal animal = animalRepository.findById(dto.getAnimalId())
                    .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + dto.getAnimalId()));
            medicamento.setAnimal(animal);
        }
        
        if (dto.getConsultaId() != null) {
            Consulta consulta = consultaRepository.findById(dto.getConsultaId())
                    .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com ID: " + dto.getConsultaId()));
            medicamento.setConsulta(consulta);
        }
        
        return medicamento;
    }
}
