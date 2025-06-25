package br.com.petshop.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {
    
    public enum StatusConsulta {
        AGENDADA, REALIZADA, CANCELADA, CONCLUIDA
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime dataHora;
    
    // Data derivada de dataHora para facilitar consultas por data
    @Column(name = "data")
    private LocalDate data;
    
    private String motivo;
    private String diagnostico;
    private String tratamento;
    private String observacoes;
    private BigDecimal valor;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusConsulta status;
    
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    
    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    private Usuario veterinario;
    
    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exame> exames = new ArrayList<>();
    
    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medicamento> medicamentos = new ArrayList<>();
    
    // Método para definir dataHora e atualizar data automaticamente
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
        if (dataHora != null) {
            this.data = dataHora.toLocalDate();
        }
    }
    
    // Método para obter a data da consulta
    public LocalDate getData() {
        return this.data;
    }
}
