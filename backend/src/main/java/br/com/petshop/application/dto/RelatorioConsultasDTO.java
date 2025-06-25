package br.com.petshop.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DTO para relatório de consultas
 */
public class RelatorioConsultasDTO {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Long totalConsultas;
    private BigDecimal valorTotal;
    private Map<String, Long> consultasPorStatus;
    private Map<String, Long> consultasPorVeterinario;
    private Map<String, Long> consultasPorEspecie;
    private List<ConsultaDTO> consultas;

    public RelatorioConsultasDTO() {
        this.consultasPorStatus = new HashMap<>();
        this.consultasPorVeterinario = new HashMap<>();
        this.consultasPorEspecie = new HashMap<>();
        this.consultas = new ArrayList<>();
        this.valorTotal = BigDecimal.ZERO;
        this.totalConsultas = 0L;
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

    public Long getTotalConsultas() {
        return totalConsultas;
    }

    public void setTotalConsultas(Long totalConsultas) {
        this.totalConsultas = totalConsultas;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Map<String, Long> getConsultasPorStatus() {
        return consultasPorStatus;
    }

    public void setConsultasPorStatus(Map<String, Long> consultasPorStatus) {
        this.consultasPorStatus = consultasPorStatus;
    }

    public Map<String, Long> getConsultasPorVeterinario() {
        return consultasPorVeterinario;
    }

    public void setConsultasPorVeterinario(Map<String, Long> consultasPorVeterinario) {
        this.consultasPorVeterinario = consultasPorVeterinario;
    }

    public Map<String, Long> getConsultasPorEspecie() {
        return consultasPorEspecie;
    }

    public void setConsultasPorEspecie(Map<String, Long> consultasPorEspecie) {
        this.consultasPorEspecie = consultasPorEspecie;
    }

    public List<ConsultaDTO> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<ConsultaDTO> consultas) {
        this.consultas = consultas;
    }
}
