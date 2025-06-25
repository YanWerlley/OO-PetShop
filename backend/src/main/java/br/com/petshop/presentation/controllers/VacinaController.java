package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.VacinaDTO;
import br.com.petshop.application.services.VacinaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vacinas")
public class VacinaController {

    private final VacinaService vacinaService;

    @Autowired
    public VacinaController(VacinaService vacinaService) {
        this.vacinaService = vacinaService;
    }

    @GetMapping
    public ResponseEntity<List<VacinaDTO>> buscarTodas() {
        return ResponseEntity.ok(vacinaService.buscarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacinaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vacinaService.buscarPorId(id));
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<VacinaDTO>> buscarPorAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(vacinaService.buscarPorAnimal(animalId));
    }
    
    @GetMapping("/busca/nome/{nome}")
    public ResponseEntity<List<VacinaDTO>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(vacinaService.buscarPorNome(nome));
    }
    
    @GetMapping("/busca/periodo-aplicacao")
    public ResponseEntity<List<VacinaDTO>> buscarPorPeriodoAplicacao(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(vacinaService.buscarPorPeriodoAplicacao(inicio, fim));
    }
    
    @GetMapping("/busca/vencidas")
    public ResponseEntity<List<VacinaDTO>> buscarVencidas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataReferencia) {
        if (dataReferencia == null) {
            dataReferencia = LocalDate.now();
        }
        return ResponseEntity.ok(vacinaService.buscarVencidas(dataReferencia));
    }
    
    @GetMapping("/busca/proximas-doses")
    public ResponseEntity<List<VacinaDTO>> buscarProximasDoses(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(vacinaService.buscarProximasDoses(inicio, fim));
    }

    @PostMapping
    public ResponseEntity<VacinaDTO> criar(@Valid @RequestBody VacinaDTO vacinaDTO) {
        VacinaDTO novaVacina = vacinaService.salvar(vacinaDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaVacina.getId())
                .toUri();
        return ResponseEntity.created(location).body(novaVacina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VacinaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody VacinaDTO vacinaDTO) {
        return ResponseEntity.ok(vacinaService.atualizar(id, vacinaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        vacinaService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
