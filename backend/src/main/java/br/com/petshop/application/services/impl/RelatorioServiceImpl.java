package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.RelatorioDTO;
import br.com.petshop.application.dto.RelatorioConsultasDTO;
import br.com.petshop.application.dto.RelatorioFaturamentoDTO;
import br.com.petshop.application.services.RelatorioService;
import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Cliente;
import br.com.petshop.domain.entities.Consulta;
import br.com.petshop.domain.entities.Exame;
import br.com.petshop.domain.entities.Medicamento;
import br.com.petshop.domain.entities.Vacina;
import br.com.petshop.domain.repositories.AnimalRepository;
import br.com.petshop.domain.repositories.ClienteRepository;
import br.com.petshop.domain.repositories.ConsultaRepository;
import br.com.petshop.domain.repositories.ExameRepository;
import br.com.petshop.domain.repositories.MedicamentoRepository;
import br.com.petshop.domain.repositories.VacinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementação do serviço de Relatórios
 * 
 * @author Yan Werlley
 */
@Service
public class RelatorioServiceImpl implements RelatorioService {

    @Autowired
    private ConsultaRepository consultaRepository;
    
    @Autowired
    private MedicamentoRepository medicamentoRepository;
    
    @Autowired
    private ExameRepository exameRepository;
    
    @Autowired
    private VacinaRepository vacinaRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public RelatorioDTO gerarRelatorio(RelatorioDTO.TipoRelatorio tipoRelatorio, 
                                      LocalDate dataInicio, 
                                      LocalDate dataFim, 
                                      String filtroAdicional) {
        RelatorioDTO relatorio = new RelatorioDTO();
        relatorio.setTipoRelatorio(tipoRelatorio.name());
        relatorio.setDataInicio(dataInicio);
        relatorio.setDataFim(dataFim);
        relatorio.setFiltroAdicional(filtroAdicional);
        
        switch (tipoRelatorio) {
            case CONSULTAS:
                gerarRelatorioConsultas(relatorio, dataInicio, dataFim, filtroAdicional);
                break;
            case MEDICAMENTOS:
                gerarRelatorioMedicamentos(relatorio, dataInicio, dataFim, filtroAdicional);
                break;
            case EXAMES:
                gerarRelatorioExames(relatorio, dataInicio, dataFim, filtroAdicional);
                break;
            case VACINAS:
                gerarRelatorioVacinas(relatorio, dataInicio, dataFim, filtroAdicional);
                break;
            case CLIENTES:
                gerarRelatorioClientes(relatorio, dataInicio, dataFim, filtroAdicional);
                break;
            case ANIMAIS:
                gerarRelatorioAnimais(relatorio, dataInicio, dataFim, filtroAdicional);
                break;
            case FATURAMENTO:
                gerarRelatorioFaturamento(relatorio, dataInicio, dataFim, filtroAdicional);
                break;
        }
        
        return relatorio;
    }

    @Override
    public byte[] exportarRelatorio(RelatorioDTO relatorioDTO, String formato) {
        // Implementação básica para exportação de relatórios
        // Em uma implementação real, usaríamos bibliotecas como JasperReports, Apache POI, etc.
        
        // Por enquanto, retornamos uma representação simples em bytes
        String conteudo = "Relatório: " + relatorioDTO.getTipoRelatorio() + "\n" +
                          "Período: " + relatorioDTO.getDataInicio() + " a " + relatorioDTO.getDataFim() + "\n" +
                          "Total de registros: " + relatorioDTO.getTotalRegistros() + "\n" +
                          "Valor total: " + relatorioDTO.getValorTotal();
        
        return conteudo.getBytes();
    }
    
    @Override
    public RelatorioConsultasDTO gerarRelatorioConsultas(LocalDate dataInicio, LocalDate dataFim) {
        RelatorioConsultasDTO relatorio = new RelatorioConsultasDTO();
        
        // Busca consultas no período
        List<Consulta> consultas = consultaRepository.findByDataBetween(dataInicio, dataFim);
        
        // Configura dados básicos do relatório
        relatorio.setDataInicio(dataInicio);
        relatorio.setDataFim(dataFim);
        relatorio.setTotalConsultas((long) consultas.size());
        
        // Calcula totais e mapas
        BigDecimal valorTotal = BigDecimal.ZERO;
        Map<String, Long> consultasPorStatus = new HashMap<>();
        Map<String, Long> consultasPorVeterinario = new HashMap<>();
        Map<String, Long> consultasPorEspecie = new HashMap<>();
        
        for (Consulta consulta : consultas) {
            // Soma valor total
            if (consulta.getValor() != null) {
                valorTotal = valorTotal.add(consulta.getValor());
            }
            
            // Contagem por status
            String status = consulta.getStatus() != null ? consulta.getStatus().name() : "DESCONHECIDO";
            consultasPorStatus.put(status, consultasPorStatus.getOrDefault(status, 0L) + 1);
            
            // Contagem por veterinário
            if (consulta.getVeterinario() != null) {
                String nomeVet = consulta.getVeterinario().getNome();
                consultasPorVeterinario.put(nomeVet, consultasPorVeterinario.getOrDefault(nomeVet, 0L) + 1);
            }
            
            // Contagem por espécie
            if (consulta.getAnimal() != null) {
                String especie = consulta.getAnimal().getEspecie() != null ? 
                        consulta.getAnimal().getEspecie() : "DESCONHECIDA";
                consultasPorEspecie.put(especie, consultasPorEspecie.getOrDefault(especie, 0L) + 1);
            }
        }
        
        relatorio.setValorTotal(valorTotal);
        relatorio.setConsultasPorStatus(consultasPorStatus);
        relatorio.setConsultasPorVeterinario(consultasPorVeterinario);
        relatorio.setConsultasPorEspecie(consultasPorEspecie);
        
        return relatorio;
    }
    
    @Override
    public RelatorioConsultasDTO gerarRelatorioConsultasPorVeterinario(Long veterinarioId, LocalDate dataInicio, LocalDate dataFim) {
        RelatorioConsultasDTO relatorio = new RelatorioConsultasDTO();
        
        // Busca consultas no período para o veterinário específico
        List<Consulta> consultas = consultaRepository.findByDataBetween(dataInicio, dataFim).stream()
                .filter(c -> c.getVeterinario() != null && c.getVeterinario().getId().equals(veterinarioId))
                .collect(Collectors.toList());
        
        // Configura dados básicos do relatório
        relatorio.setDataInicio(dataInicio);
        relatorio.setDataFim(dataFim);
        relatorio.setTotalConsultas((long) consultas.size());
        
        // Calcula totais e mapas
        BigDecimal valorTotal = BigDecimal.ZERO;
        Map<String, Long> consultasPorStatus = new HashMap<>();
        Map<String, Long> consultasPorEspecie = new HashMap<>();
        
        for (Consulta consulta : consultas) {
            // Soma valor total
            if (consulta.getValor() != null) {
                valorTotal = valorTotal.add(consulta.getValor());
            }
            
            // Contagem por status
            String status = consulta.getStatus() != null ? consulta.getStatus().name() : "DESCONHECIDO";
            consultasPorStatus.put(status, consultasPorStatus.getOrDefault(status, 0L) + 1);
            
            // Contagem por espécie
            if (consulta.getAnimal() != null) {
                String especie = consulta.getAnimal().getEspecie() != null ? 
                        consulta.getAnimal().getEspecie() : "DESCONHECIDA";
                consultasPorEspecie.put(especie, consultasPorEspecie.getOrDefault(especie, 0L) + 1);
            }
        }
        
        relatorio.setValorTotal(valorTotal);
        relatorio.setConsultasPorStatus(consultasPorStatus);
        relatorio.setConsultasPorEspecie(consultasPorEspecie);
        
        return relatorio;
    }
    
    @Override
    public RelatorioFaturamentoDTO gerarRelatorioFaturamento(LocalDate dataInicio, LocalDate dataFim) {
        RelatorioFaturamentoDTO relatorio = new RelatorioFaturamentoDTO();
        
        // Busca consultas no período
        List<Consulta> consultas = consultaRepository.findByDataBetween(dataInicio, dataFim);
        
        // Configura dados básicos do relatório
        relatorio.setDataInicio(dataInicio);
        relatorio.setDataFim(dataFim);
        relatorio.setTotalConsultas((long) consultas.size());
        
        // Calcula faturamento total
        BigDecimal faturamentoTotal = BigDecimal.ZERO;
        Map<String, BigDecimal> faturamentoPorMes = new HashMap<>();
        Map<String, BigDecimal> faturamentoPorServico = new HashMap<>();
        Map<String, BigDecimal> faturamentoPorVeterinario = new HashMap<>();
        
        for (Consulta consulta : consultas) {
            // Adiciona ao faturamento total
            BigDecimal valor = consulta.getValor() != null ? consulta.getValor() : BigDecimal.ZERO;
            faturamentoTotal = faturamentoTotal.add(valor);
            
            // Faturamento por mês
            LocalDate data = consulta.getData();
            if (data == null && consulta.getDataHora() != null) {
                data = consulta.getDataHora().toLocalDate();
            }
            
            if (data != null) {
                String mesAno = data.getMonth().toString() + "/" + data.getYear();
                BigDecimal valorMes = faturamentoPorMes.getOrDefault(mesAno, BigDecimal.ZERO);
                faturamentoPorMes.put(mesAno, valorMes.add(valor));
            }
            
            // Faturamento por serviço (usando o tipo de consulta como serviço)
            String tipoServico = consulta.getTipo() != null ? consulta.getTipo() : "CONSULTA_PADRAO";
            BigDecimal valorServico = faturamentoPorServico.getOrDefault(tipoServico, BigDecimal.ZERO);
            faturamentoPorServico.put(tipoServico, valorServico.add(valor));
            
            // Faturamento por veterinário
            if (consulta.getVeterinario() != null) {
                String nomeVet = consulta.getVeterinario().getNome();
                BigDecimal valorVet = faturamentoPorVeterinario.getOrDefault(nomeVet, BigDecimal.ZERO);
                faturamentoPorVeterinario.put(nomeVet, valorVet.add(valor));
            }
        }
        
        relatorio.setFaturamentoTotal(faturamentoTotal);
        relatorio.setFaturamentoPorMes(faturamentoPorMes);
        relatorio.setFaturamentoPorServico(faturamentoPorServico);
        relatorio.setFaturamentoPorVeterinario(faturamentoPorVeterinario);
        
        return relatorio;
    }
    
    /**
     * Gera relatório de consultas
     */
    private void gerarRelatorioConsultas(RelatorioDTO relatorio, LocalDate dataInicio, LocalDate dataFim, String filtroAdicional) {
        List<Consulta> consultas = consultaRepository.findByDataBetween(dataInicio, dataFim);
        
        // Aplica filtro adicional se existir
        if (filtroAdicional != null && !filtroAdicional.isEmpty()) {
            consultas = consultas.stream()
                    .filter(c -> c.getVeterinario().getNome().contains(filtroAdicional) || 
                                c.getAnimal().getNome().contains(filtroAdicional) ||
                                c.getStatus().name().contains(filtroAdicional))
                    .collect(Collectors.toList());
        }
        
        // Estatísticas gerais
        relatorio.setTotalRegistros((long) consultas.size());
        
        BigDecimal valorTotal = consultas.stream()
                .map(Consulta::getValor)
                .filter(valor -> valor != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        relatorio.setValorTotal(valorTotal);
        
        // Dados detalhados
        List<Map<String, Object>> dadosDetalhados = new ArrayList<>();
        for (Consulta consulta : consultas) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", consulta.getId());
            item.put("data", consulta.getData());
            item.put("animal", consulta.getAnimal().getNome());
            item.put("cliente", consulta.getAnimal().getCliente() != null ? 
                    consulta.getAnimal().getCliente().getNome() : "Cliente não informado");
            item.put("veterinario", consulta.getVeterinario().getNome());
            item.put("status", consulta.getStatus().name());
            item.put("valor", consulta.getValor());
            dadosDetalhados.add(item);
        }
        relatorio.setDadosDetalhados(dadosDetalhados);
        
        // Agrupamentos
        Map<String, Long> contagemPorStatus = new HashMap<>();
        Map<String, BigDecimal> valorPorStatus = new HashMap<>();
        
        for (Consulta.StatusConsulta status : Consulta.StatusConsulta.values()) {
            String statusName = status.name();
            contagemPorStatus.put(statusName, 0L);
            valorPorStatus.put(statusName, BigDecimal.ZERO);
        }
        
        for (Consulta consulta : consultas) {
            String status = consulta.getStatus().name();
            
            // Incrementa contagem
            contagemPorStatus.put(status, contagemPorStatus.getOrDefault(status, 0L) + 1);
            
            // Soma valor
            if (consulta.getValor() != null) {
                BigDecimal valorAtual = valorPorStatus.getOrDefault(status, BigDecimal.ZERO);
                valorPorStatus.put(status, valorAtual.add(consulta.getValor()));
            }
        }
        
        relatorio.setContagemPorCategoria(contagemPorStatus);
        relatorio.setValorPorCategoria(valorPorStatus);
    }
    
    /**
     * Gera relatório de medicamentos
     */
    private void gerarRelatorioMedicamentos(RelatorioDTO relatorio, LocalDate dataInicio, LocalDate dataFim, String filtroAdicional) {
        List<Medicamento> medicamentos = medicamentoRepository.findAll();
        
        // Aplica filtro adicional se existir
        if (filtroAdicional != null && !filtroAdicional.isEmpty()) {
            medicamentos = medicamentos.stream()
                    .filter(m -> m.getNome().contains(filtroAdicional) || 
                                m.getFabricante().contains(filtroAdicional))
                    .collect(Collectors.toList());
        }
        
        // Estatísticas gerais
        relatorio.setTotalRegistros((long) medicamentos.size());
        
        // Dados detalhados
        List<Map<String, Object>> dadosDetalhados = new ArrayList<>();
        for (Medicamento medicamento : medicamentos) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", medicamento.getId());
            item.put("nome", medicamento.getNome());
            item.put("fabricante", medicamento.getFabricante() != null ? medicamento.getFabricante() : "Não informado");
            item.put("dosagem", medicamento.getDosagem());
            item.put("tipo", medicamento.getTipo() != null ? medicamento.getTipo() : "Não informado");
            dadosDetalhados.add(item);
        }
        relatorio.setDadosDetalhados(dadosDetalhados);
        
        // Agrupamentos por tipo
        Map<String, Long> contagemPorTipo = medicamentos.stream()
                .filter(m -> m.getTipo() != null)
                .collect(Collectors.groupingBy(m -> m.getTipo(), Collectors.counting()));
        
        relatorio.setContagemPorCategoria(contagemPorTipo);
    }
    
    /**
     * Gera relatório de exames
     */
    private void gerarRelatorioExames(RelatorioDTO relatorio, LocalDate dataInicio, LocalDate dataFim, String filtroAdicional) {
        List<Exame> exames = exameRepository.findByDataRealizacaoBetween(dataInicio, dataFim);
        
        // Aplica filtro adicional se existir
        if (filtroAdicional != null && !filtroAdicional.isEmpty()) {
            exames = exames.stream()
                    .filter(e -> e.getNome().contains(filtroAdicional) || 
                                e.getTipo().contains(filtroAdicional))
                    .collect(Collectors.toList());
        }
        
        // Estatísticas gerais
        relatorio.setTotalRegistros((long) exames.size());
        
        // Dados detalhados
        List<Map<String, Object>> dadosDetalhados = new ArrayList<>();
        for (Exame exame : exames) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", exame.getId());
            item.put("nome", exame.getNome());
            item.put("tipo", exame.getTipo());
            item.put("dataRealizacao", exame.getDataRealizacao());
            item.put("animal", exame.getAnimal().getNome());
            dadosDetalhados.add(item);
        }
        relatorio.setDadosDetalhados(dadosDetalhados);
        
        // Agrupamentos por tipo
        Map<String, Long> contagemPorTipo = exames.stream()
                .collect(Collectors.groupingBy(
                        Exame::getTipo,
                        Collectors.counting()
                ));
        
        relatorio.setContagemPorCategoria(contagemPorTipo);
    }
    
    /**
     * Gera relatório de vacinas
     */
    private void gerarRelatorioVacinas(RelatorioDTO relatorio, LocalDate dataInicio, LocalDate dataFim, String filtroAdicional) {
        List<Vacina> vacinas = vacinaRepository.findByDataAplicacaoBetween(dataInicio, dataFim);
        
        // Aplica filtro adicional se existir
        if (filtroAdicional != null && !filtroAdicional.isEmpty()) {
            vacinas = vacinas.stream()
                    .filter(v -> v.getNome().contains(filtroAdicional) || 
                                v.getFabricante().contains(filtroAdicional))
                    .collect(Collectors.toList());
        }
        
        // Estatísticas gerais
        relatorio.setTotalRegistros((long) vacinas.size());
        
        // Dados detalhados
        List<Map<String, Object>> dadosDetalhados = new ArrayList<>();
        for (Vacina vacina : vacinas) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", vacina.getId());
            item.put("nome", vacina.getNome());
            item.put("fabricante", vacina.getFabricante() != null ? vacina.getFabricante() : "Não informado");
            item.put("dataAplicacao", vacina.getDataAplicacao());
            item.put("animal", vacina.getAnimal().getNome());
            dadosDetalhados.add(item);
        }
        relatorio.setDadosDetalhados(dadosDetalhados);
        
        // Agrupamentos por fabricante
        Map<String, Long> contagemPorFabricante = vacinas.stream()
                .filter(m -> m.getFabricante() != null)
                .collect(Collectors.groupingBy(m -> m.getFabricante(), Collectors.counting()));
        
        relatorio.setContagemPorCategoria(contagemPorFabricante);
    }
    
    /**
     * Gera relatório de clientes
     */
    private void gerarRelatorioClientes(RelatorioDTO relatorio, LocalDate dataInicio, LocalDate dataFim, String filtroAdicional) {
        List<Cliente> clientes = clienteRepository.findAll();
        
        // Aplica filtro adicional se existir
        if (filtroAdicional != null && !filtroAdicional.isEmpty()) {
            clientes = clientes.stream()
                    .filter(c -> c.getNome().contains(filtroAdicional) || 
                                c.getEmail().contains(filtroAdicional) ||
                                c.getTelefone().contains(filtroAdicional))
                    .collect(Collectors.toList());
        }
        
        // Estatísticas gerais
        relatorio.setTotalRegistros((long) clientes.size());
        
        // Dados detalhados
        List<Map<String, Object>> dadosDetalhados = new ArrayList<>();
        for (Cliente cliente : clientes) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", cliente.getId());
            item.put("nome", cliente.getNome());
            item.put("email", cliente.getEmail());
            item.put("telefone", cliente.getTelefone());
            item.put("qtdAnimais", cliente.getAnimais() != null ? cliente.getAnimais().size() : 0);
            dadosDetalhados.add(item);
        }
        relatorio.setDadosDetalhados(dadosDetalhados);
    }
    
    /**
     * Gera relatório de animais
     */
    private void gerarRelatorioAnimais(RelatorioDTO relatorio, LocalDate dataInicio, LocalDate dataFim, String filtroAdicional) {
        List<Animal> animais = animalRepository.findAll();
        
        // Aplica filtro adicional se existir
        if (filtroAdicional != null && !filtroAdicional.isEmpty()) {
            animais = animais.stream()
                    .filter(a -> a.getNome().contains(filtroAdicional) || 
                                a.getRaca().contains(filtroAdicional) ||
                                (a.getCliente() != null && a.getCliente().getNome().contains(filtroAdicional)))
                    .collect(Collectors.toList());
        }
        
        // Estatísticas gerais
        relatorio.setTotalRegistros((long) animais.size());
        
        // Dados detalhados
        List<Map<String, Object>> dadosDetalhados = new ArrayList<>();
        for (Animal animal : animais) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", animal.getId());
            item.put("nome", animal.getNome());
            item.put("tipo", animal instanceof br.com.petshop.domain.entities.Cachorro ? "Cachorro" : 
                           animal instanceof br.com.petshop.domain.entities.Gato ? "Gato" : "Outro");
            item.put("raca", animal.getRaca());
            item.put("idade", animal.getIdade());
            item.put("cliente", animal.getCliente() != null ? animal.getCliente().getNome() : "Cliente não informado");
            dadosDetalhados.add(item);
        }
        relatorio.setDadosDetalhados(dadosDetalhados);
        
        // Agrupamentos por tipo
        Map<String, Long> contagemPorTipo = new HashMap<>();
        contagemPorTipo.put("Cachorro", animais.stream()
                .filter(a -> a instanceof br.com.petshop.domain.entities.Cachorro)
                .count());
        contagemPorTipo.put("Gato", animais.stream()
                .filter(a -> a instanceof br.com.petshop.domain.entities.Gato)
                .count());
        
        relatorio.setContagemPorCategoria(contagemPorTipo);
    }
    
    /**
     * Gera relatório de faturamento
     */
    private void gerarRelatorioFaturamento(RelatorioDTO relatorio, LocalDate dataInicio, LocalDate dataFim, String filtroAdicional) {
        List<Consulta> consultas = consultaRepository.findByDataBetween(dataInicio, dataFim);
        
        // Estatísticas gerais
        relatorio.setTotalRegistros((long) consultas.size());
        
        BigDecimal valorTotal = consultas.stream()
                .map(Consulta::getValor)
                .filter(valor -> valor != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        relatorio.setValorTotal(valorTotal);
        
        // Dados detalhados por mês
        Map<String, BigDecimal> faturamentoPorMes = new HashMap<>();
        Map<String, Long> consultasPorMes = new HashMap<>();
        
        for (Consulta consulta : consultas) {
            LocalDate data = consulta.getData();
            if (data == null && consulta.getDataHora() != null) {
                data = consulta.getDataHora().toLocalDate();
            }
            
            if (data != null) {
                String mesAno = data.getMonth().toString() + "/" + data.getYear();
                
                // Incrementa contagem
                consultasPorMes.put(mesAno, consultasPorMes.getOrDefault(mesAno, 0L) + 1);
                
                // Soma valor
                if (consulta.getValor() != null) {
                    BigDecimal valorAtual = faturamentoPorMes.getOrDefault(mesAno, BigDecimal.ZERO);
                    faturamentoPorMes.put(mesAno, valorAtual.add(consulta.getValor()));
                }
            }
        }
        
        relatorio.setContagemPorCategoria(consultasPorMes);
        relatorio.setValorPorCategoria(faturamentoPorMes);
        
        // Dados detalhados
        List<Map<String, Object>> dadosDetalhados = new ArrayList<>();
        for (Consulta consulta : consultas) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", consulta.getId());
            item.put("data", consulta.getData());
            item.put("animal", consulta.getAnimal().getNome());
            item.put("cliente", consulta.getAnimal().getCliente() != null ? 
                    consulta.getAnimal().getCliente().getNome() : "Cliente não informado");
            item.put("veterinario", consulta.getVeterinario().getNome());
            item.put("valor", consulta.getValor());
            dadosDetalhados.add(item);
        }
        relatorio.setDadosDetalhados(dadosDetalhados);
    }
}
