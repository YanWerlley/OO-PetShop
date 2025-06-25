package br.com.petshop.application.dto;

import br.com.petshop.domain.entities.Agendamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO para transferência de dados de Agendamento
 * 
 * @author Yan Werlley
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoDTO {
    
    private Long id;
    
    @NotNull(message = "A data e hora do agendamento são obrigatórias")
    @Future(message = "A data e hora do agendamento deve ser no futuro")
    private LocalDateTime dataHora;
    
    private String observacoes;
    
    @NotNull(message = "O ID do animal é obrigatório")
    private Long animalId;
    
    private String animalNome;
    
    private Long clienteId;
    
    private String clienteNome;
    
    @NotNull(message = "O ID do veterinário é obrigatório")
    private Long veterinarioId;
    
    private String veterinarioNome;
    
    private Agendamento.StatusAgendamento status = Agendamento.StatusAgendamento.AGENDADO;
}
