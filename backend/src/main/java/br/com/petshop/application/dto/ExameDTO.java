package br.com.petshop.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExameDTO {
    private Long id;
    private String nome;
    private String tipo;
    private String resultado;
    private LocalDate dataRealizacao;
    private LocalDate dataResultado;
    private String observacoes;
    private Long consultaId;
    private Long animalId;
    private String animalNome;
}
