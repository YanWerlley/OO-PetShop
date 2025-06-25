package br.com.petshop.application.services;

import br.com.petshop.application.dto.AnimalDTO;

import java.util.List;

public interface AnimalService {
    List<AnimalDTO> findAll();
    AnimalDTO findById(Long id);
    List<AnimalDTO> findByClienteId(Long clienteId);
    AnimalDTO save(AnimalDTO animalDTO);
    AnimalDTO update(Long id, AnimalDTO animalDTO);
    void delete(Long id);
}
