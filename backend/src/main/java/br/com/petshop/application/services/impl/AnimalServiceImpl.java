package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.AnimalDTO;
import br.com.petshop.application.services.AnimalService;
import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Cachorro;
import br.com.petshop.domain.entities.Cliente;
import br.com.petshop.domain.entities.Gato;
import br.com.petshop.domain.repositories.AnimalRepository;
import br.com.petshop.domain.repositories.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository, ClienteRepository clienteRepository) {
        this.animalRepository = animalRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<AnimalDTO> findAll() {
        return animalRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AnimalDTO findById(Long id) {
        return animalRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + id));
    }

    @Override
    public List<AnimalDTO> findByClienteId(Long clienteId) {
        return animalRepository.findByClienteId(clienteId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AnimalDTO save(AnimalDTO animalDTO) {
        Cliente cliente = clienteRepository.findById(animalDTO.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + animalDTO.getClienteId()));
        
        Animal animal;
        if ("CACHORRO".equals(animalDTO.getTipoAnimal())) {
            Cachorro cachorro = new Cachorro();
            cachorro.setPorte(animalDTO.getPorte());
            cachorro.setCastrado(animalDTO.getCastrado());
            cachorro.setPeso(animalDTO.getPeso());
            animal = cachorro;
        } else if ("GATO".equals(animalDTO.getTipoAnimal())) {
            Gato gato = new Gato();
            gato.setCastrado(animalDTO.getCastrado());
            gato.setPeso(animalDTO.getPeso());
            gato.setPelagem(animalDTO.getPelagem());
            animal = gato;
        } else {
            throw new IllegalArgumentException("Tipo de animal inválido: " + animalDTO.getTipoAnimal());
        }
        
        animal.setNome(animalDTO.getNome());
        animal.setDataNascimento(animalDTO.getDataNascimento());
        animal.setRaca(animalDTO.getRaca());
        animal.setCor(animalDTO.getCor());
        animal.setCliente(cliente);
        
        Animal savedAnimal = animalRepository.save(animal);
        return convertToDTO(savedAnimal);
    }

    @Override
    @Transactional
    public AnimalDTO update(Long id, AnimalDTO animalDTO) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + id));
        
        Cliente cliente = clienteRepository.findById(animalDTO.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + animalDTO.getClienteId()));
        
        animal.setNome(animalDTO.getNome());
        animal.setDataNascimento(animalDTO.getDataNascimento());
        animal.setRaca(animalDTO.getRaca());
        animal.setCor(animalDTO.getCor());
        animal.setCliente(cliente);
        
        if (animal instanceof Cachorro && "CACHORRO".equals(animalDTO.getTipoAnimal())) {
            Cachorro cachorro = (Cachorro) animal;
            cachorro.setPorte(animalDTO.getPorte());
            cachorro.setCastrado(animalDTO.getCastrado());
            cachorro.setPeso(animalDTO.getPeso());
        } else if (animal instanceof Gato && "GATO".equals(animalDTO.getTipoAnimal())) {
            Gato gato = (Gato) animal;
            gato.setCastrado(animalDTO.getCastrado());
            gato.setPeso(animalDTO.getPeso());
            gato.setPelagem(animalDTO.getPelagem());
        }
        
        Animal updatedAnimal = animalRepository.save(animal);
        return convertToDTO(updatedAnimal);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new EntityNotFoundException("Animal não encontrado com ID: " + id);
        }
        animalRepository.deleteById(id);
    }
    
    private AnimalDTO convertToDTO(Animal animal) {
        AnimalDTO dto = new AnimalDTO();
        dto.setId(animal.getId());
        dto.setNome(animal.getNome());
        dto.setDataNascimento(animal.getDataNascimento());
        dto.setRaca(animal.getRaca());
        dto.setCor(animal.getCor());
        dto.setClienteId(animal.getCliente().getId());
        
        if (animal instanceof Cachorro) {
            Cachorro cachorro = (Cachorro) animal;
            dto.setTipoAnimal("CACHORRO");
            dto.setPorte(cachorro.getPorte());
            dto.setCastrado(cachorro.getCastrado());
            dto.setPeso(cachorro.getPeso());
        } else if (animal instanceof Gato) {
            Gato gato = (Gato) animal;
            dto.setTipoAnimal("GATO");
            dto.setCastrado(gato.getCastrado());
            dto.setPeso(gato.getPeso());
            dto.setPelagem(gato.getPelagem());
        }
        
        return dto;
    }
}
