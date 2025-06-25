package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.VacinaDTO;
import br.com.petshop.application.services.VacinaService;
import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Vacina;
import br.com.petshop.domain.repositories.AnimalRepository;
import br.com.petshop.domain.repositories.VacinaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacinaServiceImpl implements VacinaService {

    private final VacinaRepository vacinaRepository;
    private final AnimalRepository animalRepository;

    @Autowired
    public VacinaServiceImpl(VacinaRepository vacinaRepository, AnimalRepository animalRepository) {
        this.vacinaRepository = vacinaRepository;
        this.animalRepository = animalRepository;
    }

    @Override
    public List<VacinaDTO> buscarTodas() {
        return vacinaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VacinaDTO buscarPorId(Long id) {
        return vacinaRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Vacina não encontrada com ID: " + id));
    }

    @Override
    public List<VacinaDTO> buscarPorAnimal(Long animalId) {
        return vacinaRepository.findByAnimalId(animalId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<VacinaDTO> buscarPorNome(String nome) {
        // Implementação básica - em um cenário real, adicionaríamos um método no repositório
        return vacinaRepository.findAll().stream()
                .filter(vacina -> vacina.getNome().toLowerCase().contains(nome.toLowerCase()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<VacinaDTO> buscarPorPeriodoAplicacao(LocalDate inicio, LocalDate fim) {
        // Implementação básica - em um cenário real, adicionaríamos um método no repositório
        return vacinaRepository.findAll().stream()
                .filter(vacina -> {
                    LocalDate dataAplicacao = vacina.getDataAplicacao();
                    return dataAplicacao != null && 
                           (dataAplicacao.isEqual(inicio) || dataAplicacao.isAfter(inicio)) && 
                           (dataAplicacao.isEqual(fim) || dataAplicacao.isBefore(fim));
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<VacinaDTO> buscarVencidas(LocalDate dataReferencia) {
        // Implementação básica - em um cenário real, adicionaríamos um método no repositório
        return vacinaRepository.findAll().stream()
                .filter(vacina -> {
                    LocalDate dataValidade = vacina.getDataValidade();
                    return dataValidade != null && dataValidade.isBefore(dataReferencia);
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<VacinaDTO> buscarProximasDoses(LocalDate inicio, LocalDate fim) {
        // Implementação básica - em um cenário real, adicionaríamos um método no repositório
        return vacinaRepository.findAll().stream()
                .filter(vacina -> {
                    LocalDate dataProximaDose = vacina.getDataProximaDose();
                    return dataProximaDose != null && 
                           (dataProximaDose.isEqual(inicio) || dataProximaDose.isAfter(inicio)) && 
                           (dataProximaDose.isEqual(fim) || dataProximaDose.isBefore(fim));
                })
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VacinaDTO salvar(VacinaDTO vacinaDTO) {
        Animal animal = animalRepository.findById(vacinaDTO.getAnimalId())
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + vacinaDTO.getAnimalId()));
        
        Vacina vacina = new Vacina();
        vacina.setNome(vacinaDTO.getNome());
        vacina.setLote(vacinaDTO.getLote());
        vacina.setDataAplicacao(vacinaDTO.getDataAplicacao());
        vacina.setDataValidade(vacinaDTO.getDataValidade());
        vacina.setDataProximaDose(vacinaDTO.getDataProximaDose());
        vacina.setAnimal(animal);
        
        Vacina savedVacina = vacinaRepository.save(vacina);
        return convertToDTO(savedVacina);
    }

    @Override
    @Transactional
    public VacinaDTO atualizar(Long id, VacinaDTO vacinaDTO) {
        Vacina vacina = vacinaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vacina não encontrada com ID: " + id));
        
        Animal animal = animalRepository.findById(vacinaDTO.getAnimalId())
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + vacinaDTO.getAnimalId()));
        
        vacina.setNome(vacinaDTO.getNome());
        vacina.setLote(vacinaDTO.getLote());
        vacina.setDataAplicacao(vacinaDTO.getDataAplicacao());
        vacina.setDataValidade(vacinaDTO.getDataValidade());
        vacina.setDataProximaDose(vacinaDTO.getDataProximaDose());
        vacina.setAnimal(animal);
        
        Vacina updatedVacina = vacinaRepository.save(vacina);
        return convertToDTO(updatedVacina);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        if (!vacinaRepository.existsById(id)) {
            throw new EntityNotFoundException("Vacina não encontrada com ID: " + id);
        }
        vacinaRepository.deleteById(id);
    }
    
    private VacinaDTO convertToDTO(Vacina vacina) {
        VacinaDTO dto = new VacinaDTO();
        dto.setId(vacina.getId());
        dto.setNome(vacina.getNome());
        dto.setLote(vacina.getLote());
        dto.setDataAplicacao(vacina.getDataAplicacao());
        dto.setDataValidade(vacina.getDataValidade());
        dto.setDataProximaDose(vacina.getDataProximaDose());
        
        // Se a data da próxima dose não estiver definida, mas a data de aplicação estiver,
        // podemos calcular como dataAplicacao + 6 meses como valor padrão
        if (dto.getDataProximaDose() == null && vacina.getDataAplicacao() != null) {
            dto.setDataProximaDose(vacina.getDataAplicacao().plusMonths(6));
        }
        
        dto.setAnimalId(vacina.getAnimal().getId());
        return dto;
    }
}
