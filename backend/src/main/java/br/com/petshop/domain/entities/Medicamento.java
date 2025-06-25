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
public class Medicamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String dosagem;
    private String frequencia;
    private String instrucoes;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String observacoes;
    private String fabricante;
    private String tipo;
    
    @ManyToOne
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;
    
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    
    public String getFabricante() {
        return fabricante;
    }
    
    public String getTipo() {
        return tipo;
    }
}
