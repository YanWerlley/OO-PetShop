package br.com.petshop.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoDTO {
    private Long id;
    private String nome;
    private String dosagem;
    private String frequencia;
    private String instrucoes;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String observacoes;
    private Long consultaId;
    private Long animalId;
    private String animalNome;
}
