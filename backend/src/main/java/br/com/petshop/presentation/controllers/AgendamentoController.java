package br.com.petshop.presentation.controllers;

import br.com.petshop.application.dto.AgendamentoDTO;
import br.com.petshop.application.services.AgendamentoService;
import br.com.petshop.domain.entities.Agendamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller para operações relacionadas a Agendamentos
 * 
 * @author Yan Werlley
 */
@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {
    
    @Autowired
    private AgendamentoService agendamentoService;
    
    /**
     * Cria um novo agendamento
     */
    @PostMapping
    public ResponseEntity<AgendamentoDTO> criar(@Valid @RequestBody AgendamentoDTO agendamentoDTO) {
        AgendamentoDTO novoAgendamento = agendamentoService.salvar(agendamentoDTO);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoAgendamento.getId())
                .toUri();
        
        return ResponseEntity.created(location).body(novoAgendamento);
    }
    
    /**
     * Busca um agendamento pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }
    
    /**
     * Lista todos os agendamentos
     */
    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> listarTodos() {
        return ResponseEntity.ok(agendamentoService.listarTodos());
    }
    
    /**
     * Atualiza um agendamento existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> atualizar(@PathVariable Long id, 
                                                  @Valid @RequestBody AgendamentoDTO agendamentoDTO) {
        return ResponseEntity.ok(agendamentoService.atualizar(id, agendamentoDTO));
    }
    
    /**
     * Exclui um agendamento
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        agendamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Busca agendamentos por animal
     */
    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<AgendamentoDTO>> buscarPorAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(agendamentoService.buscarPorAnimal(animalId));
    }
    
    /**
     * Busca agendamentos por veterinário
     */
    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<List<AgendamentoDTO>> buscarPorVeterinario(@PathVariable Long veterinarioId) {
        return ResponseEntity.ok(agendamentoService.buscarPorVeterinario(veterinarioId));
    }
    
    /**
     * Busca agendamentos por status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AgendamentoDTO>> buscarPorStatus(@PathVariable Agendamento.StatusAgendamento status) {
        return ResponseEntity.ok(agendamentoService.buscarPorStatus(status));
    }
    
    /**
     * Busca agendamentos por período
     */
    @GetMapping("/periodo")
    public ResponseEntity<List<AgendamentoDTO>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(agendamentoService.buscarPorPeriodo(inicio, fim));
    }
    
    /**
     * Altera o status de um agendamento
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<AgendamentoDTO> alterarStatus(@PathVariable Long id, 
                                                      @RequestParam Agendamento.StatusAgendamento status) {
        return ResponseEntity.ok(agendamentoService.alterarStatus(id, status));
    }
}
