package br.com.petshop.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacina {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String lote;
    private String fabricante;
    private LocalDate dataAplicacao;
    private LocalDate dataValidade;
    private LocalDate dataProximaDose;
    
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    
    public String getFabricante() {
        return fabricante;
    }
}
