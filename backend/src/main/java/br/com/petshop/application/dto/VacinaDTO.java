package br.com.petshop.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacinaDTO {
    private Long id;
    private String nome;
    private String lote;
    private LocalDate dataAplicacao;
    private LocalDate dataValidade;
    private LocalDate dataProximaDose;
    private Long animalId;
}
