package br.com.petshop.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO {
    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String raca;
    private String cor;
    private Long clienteId;
    private String tipoAnimal;
    
    // Campos específicos para Cachorro
    private String porte;
    private Boolean castrado;
    private Double peso;
    
    // Campos específicos para Gato
    private String pelagem;
}
