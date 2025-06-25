package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.ExameDTO;
import br.com.petshop.application.services.ExameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/exames")
public class ExameController {

    @Autowired
    private ExameService exameService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIO')")
    public ResponseEntity<List<ExameDTO>> buscarTodos() {
        return ResponseEntity.ok(exameService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExameDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(exameService.buscarPorId(id));
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<ExameDTO>> buscarPorAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(exameService.buscarPorAnimal(animalId));
    }

    @GetMapping("/consulta/{consultaId}")
    public ResponseEntity<List<ExameDTO>> buscarPorConsulta(@PathVariable Long consultaId) {
        return ResponseEntity.ok(exameService.buscarPorConsulta(consultaId));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ExameDTO>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(exameService.buscarPorTipo(tipo));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<ExameDTO>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(exameService.buscarPorPeriodo(inicio, fim));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ExameDTO>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(exameService.buscarPorNome(nome));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIO')")
    public ResponseEntity<ExameDTO> salvar(@Valid @RequestBody ExameDTO exameDTO) {
        ExameDTO novoExame = exameService.salvar(exameDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoExame.getId())
                .toUri();
        return ResponseEntity.created(location).body(novoExame);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIO')")
    public ResponseEntity<ExameDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ExameDTO exameDTO) {
        return ResponseEntity.ok(exameService.atualizar(id, exameDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        exameService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
