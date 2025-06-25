package br.com.petshop.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaDTO {
    private Long id;
    private LocalDateTime dataHora;
    private LocalDateTime dataHoraFim;
    private String motivo;
    private String diagnostico;
    private String tratamento;
    private String observacoes;
    private String status;
    private Long animalId;
    private String animalNome;
    private Long veterinarioId;
    private String veterinarioNome;
    private BigDecimal valor;
    private List<ExameDTO> exames = new ArrayList<>();
    private List<MedicamentoDTO> medicamentos = new ArrayList<>();
}
