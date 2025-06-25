package br.com.petshop.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * DTO para relatório de faturamento
 */
public class RelatorioFaturamentoDTO {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal faturamentoTotal;
    private BigDecimal faturamentoConsultas;
    private BigDecimal faturamentoExames;
    private BigDecimal faturamentoMedicamentos;
    private BigDecimal faturamentoVacinas;
    private Map<String, BigDecimal> faturamentoPorMes;
    private Map<String, BigDecimal> faturamentoPorServico;
    private Map<String, BigDecimal> faturamentoPorVeterinario;

    public RelatorioFaturamentoDTO() {
        this.faturamentoTotal = BigDecimal.ZERO;
        this.faturamentoConsultas = BigDecimal.ZERO;
        this.faturamentoExames = BigDecimal.ZERO;
        this.faturamentoMedicamentos = BigDecimal.ZERO;
        this.faturamentoVacinas = BigDecimal.ZERO;
        this.faturamentoPorMes = new HashMap<>();
        this.faturamentoPorServico = new HashMap<>();
        this.faturamentoPorVeterinario = new HashMap<>();
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public BigDecimal getFaturamentoTotal() {
        return faturamentoTotal;
    }

    public void setFaturamentoTotal(BigDecimal faturamentoTotal) {
        this.faturamentoTotal = faturamentoTotal;
    }

    public BigDecimal getFaturamentoConsultas() {
        return faturamentoConsultas;
    }

    public void setFaturamentoConsultas(BigDecimal faturamentoConsultas) {
        this.faturamentoConsultas = faturamentoConsultas;
    }

    public BigDecimal getFaturamentoExames() {
        return faturamentoExames;
    }

    public void setFaturamentoExames(BigDecimal faturamentoExames) {
        this.faturamentoExames = faturamentoExames;
    }

    public BigDecimal getFaturamentoMedicamentos() {
        return faturamentoMedicamentos;
    }

    public void setFaturamentoMedicamentos(BigDecimal faturamentoMedicamentos) {
        this.faturamentoMedicamentos = faturamentoMedicamentos;
    }

    public BigDecimal getFaturamentoVacinas() {
        return faturamentoVacinas;
    }

    public void setFaturamentoVacinas(BigDecimal faturamentoVacinas) {
        this.faturamentoVacinas = faturamentoVacinas;
    }

    public Map<String, BigDecimal> getFaturamentoPorMes() {
        return faturamentoPorMes;
    }

    public void setFaturamentoPorMes(Map<String, BigDecimal> faturamentoPorMes) {
        this.faturamentoPorMes = faturamentoPorMes;
    }

    public Map<String, BigDecimal> getFaturamentoPorServico() {
        return faturamentoPorServico;
    }

    public void setFaturamentoPorServico(Map<String, BigDecimal> faturamentoPorServico) {
        this.faturamentoPorServico = faturamentoPorServico;
    }

    public Map<String, BigDecimal> getFaturamentoPorVeterinario() {
        return faturamentoPorVeterinario;
    }

    public void setFaturamentoPorVeterinario(Map<String, BigDecimal> faturamentoPorVeterinario) {
        this.faturamentoPorVeterinario = faturamentoPorVeterinario;
    }
}
