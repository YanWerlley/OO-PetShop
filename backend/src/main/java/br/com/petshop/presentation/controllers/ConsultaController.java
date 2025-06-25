package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.ConsultaDTO;
import br.com.petshop.application.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIO')")
    public ResponseEntity<List<ConsultaDTO>> buscarTodas() {
        return ResponseEntity.ok(consultaService.buscarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<ConsultaDTO>> buscarPorAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(consultaService.buscarPorAnimal(animalId));
    }

    @GetMapping("/veterinario/{veterinarioId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIO')")
    public ResponseEntity<List<ConsultaDTO>> buscarPorVeterinario(@PathVariable Long veterinarioId) {
        return ResponseEntity.ok(consultaService.buscarPorVeterinario(veterinarioId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ConsultaDTO>> buscarPorStatus(@PathVariable String status) {
        return ResponseEntity.ok(consultaService.buscarPorStatus(status));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<ConsultaDTO>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(consultaService.buscarPorPeriodo(inicio, fim));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIO')")
    public ResponseEntity<ConsultaDTO> agendar(@Valid @RequestBody ConsultaDTO consultaDTO) {
        ConsultaDTO novaConsulta = consultaService.agendar(consultaDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaConsulta.getId())
                .toUri();
        return ResponseEntity.created(location).body(novaConsulta);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIO')")
    public ResponseEntity<ConsultaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ConsultaDTO consultaDTO) {
        return ResponseEntity.ok(consultaService.atualizar(id, consultaDTO));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        consultaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        consultaService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
