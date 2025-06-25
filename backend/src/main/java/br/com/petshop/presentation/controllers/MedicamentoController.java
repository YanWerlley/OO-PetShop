package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.MedicamentoDTO;
import br.com.petshop.application.services.MedicamentoService;
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
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIO')")
    public ResponseEntity<List<MedicamentoDTO>> buscarTodos() {
        return ResponseEntity.ok(medicamentoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicamentoService.buscarPorId(id));
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<MedicamentoDTO>> buscarPorAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(medicamentoService.buscarPorAnimal(animalId));
    }

    @GetMapping("/consulta/{consultaId}")
    public ResponseEntity<List<MedicamentoDTO>> buscarPorConsulta(@PathVariable Long consultaId) {
        return ResponseEntity.ok(medicamentoService.buscarPorConsulta(consultaId));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<MedicamentoDTO>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(medicamentoService.buscarPorNome(nome));
    }

    @GetMapping("/periodo-inicio")
    public ResponseEntity<List<MedicamentoDTO>> buscarPorPeriodoInicio(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(medicamentoService.buscarPorPeriodoInicio(inicio, fim));
    }

    @GetMapping("/periodo-fim")
    public ResponseEntity<List<MedicamentoDTO>> buscarPorPeriodoFim(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(medicamentoService.buscarPorPeriodoFim(inicio, fim));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIO')")
    public ResponseEntity<MedicamentoDTO> salvar(@Valid @RequestBody MedicamentoDTO medicamentoDTO) {
        MedicamentoDTO novoMedicamento = medicamentoService.salvar(medicamentoDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoMedicamento.getId())
                .toUri();
        return ResponseEntity.created(location).body(novoMedicamento);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('VETERINARIO')")
    public ResponseEntity<MedicamentoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody MedicamentoDTO medicamentoDTO) {
        return ResponseEntity.ok(medicamentoService.atualizar(id, medicamentoDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        medicamentoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
