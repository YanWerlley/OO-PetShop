package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.AnimalDTO;
import br.com.petshop.application.services.AnimalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animais")
public class AnimalController {

    private final AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public ResponseEntity<List<AnimalDTO>> findAll() {
        return ResponseEntity.ok(animalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.findById(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AnimalDTO>> findByClienteId(@PathVariable Long clienteId) {
        return ResponseEntity.ok(animalService.findByClienteId(clienteId));
    }

    @PostMapping
    public ResponseEntity<AnimalDTO> save(@Valid @RequestBody AnimalDTO animalDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalService.save(animalDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalDTO> update(@PathVariable Long id, @Valid @RequestBody AnimalDTO animalDTO) {
        return ResponseEntity.ok(animalService.update(id, animalDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
