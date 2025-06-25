package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.RelatorioConsultasDTO;
import br.com.petshop.application.dto.RelatorioFaturamentoDTO;
import br.com.petshop.domain.entities.*;
import br.com.petshop.domain.repositories.ConsultaRepository;
import br.com.petshop.domain.repositories.ExameRepository;
import br.com.petshop.domain.repositories.MedicamentoRepository;
import br.com.petshop.domain.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RelatorioServiceImplTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private ExameRepository exameRepository;

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private RelatorioServiceImpl relatorioService;

    private List<Consulta> consultas;
    private Usuario veterinario;
    private Usuario cliente;
    private Animal animal;
    private Agendamento agendamento;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    @BeforeEach
    void setUp() {
        // Configurar datas
        dataInicio = LocalDate.now().minusDays(30);
        dataFim = LocalDate.now();

        // Configurar usuários
        veterinario = new Usuario();
        veterinario.setId(1L);
        veterinario.setNome("Dr. Veterinário");

        cliente = new Usuario();
        cliente.setId(2L);
        cliente.setNome("Cliente Teste");

        // Configurar animal
        animal = new Animal();
        animal.setId(1L);
        animal.setNome("Animal Teste");
        animal.setEspecie("Cachorro");
        animal.setRaca("Labrador");
        animal.setCliente(cliente);

        // Configurar agendamento
        agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setVeterinario(veterinario);
        agendamento.setAnimal(animal);
        agendamento.setDataHora(LocalDateTime.now().minusDays(15));
        agendamento.setStatus(Agendamento.StatusAgendamento.CONFIRMADO);

        // Configurar consultas
        consultas = new ArrayList<>();
        
        Consulta consulta1 = new Consulta();
        consulta1.setId(1L);
        consulta1.setAgendamento(agendamento);
        consulta1.setDataHoraInicio(LocalDateTime.now().minusDays(15));
        consulta1.setDataHoraFim(LocalDateTime.now().minusDays(15).plusHours(1));
        consulta1.setDiagnostico("Diagnóstico 1");
        consulta1.setObservacoes("Observações 1");
        consulta1.setExames(new ArrayList<>());
        consulta1.setMedicamentos(new ArrayList<>());
        
        // Adicionar exame à consulta
        Exame exame = new Exame();
        exame.setId(1L);
        exame.setNome("Exame de sangue");
        exame.setTipo("SIMPLES");
        consulta1.getExames().add(exame);
        
        // Adicionar medicamento à consulta
        Medicamento medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("Medicamento teste");
        medicamento.setDosagem("10mg");
        medicamento.setInstrucoes("Tomar 2x ao dia");
        consulta1.getMedicamentos().add(medicamento);
        
        consultas.add(consulta1);
    }

    @Test
    void gerarRelatorioConsultas_DeveGerarRelatorioComSucesso() {
        // Arrange
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(LocalTime.MAX);
        
        when(consultaRepository.findByDataHoraInicioBetween(inicio, fim)).thenReturn(consultas);

        // Act
        RelatorioConsultasDTO relatorio = relatorioService.gerarRelatorioConsultas(dataInicio, dataFim);

        // Assert
        assertNotNull(relatorio);
        assertEquals(dataInicio, relatorio.getDataInicio());
        assertEquals(dataFim, relatorio.getDataFim());
        assertEquals(1L, relatorio.getTotalConsultas());
        assertNotNull(relatorio.getConsultasPorVeterinario());
        assertNotNull(relatorio.getConsultasPorEspecie());
        assertNotNull(relatorio.getConsultasPorDia());
        verify(consultaRepository).findByDataHoraInicioBetween(inicio, fim);
    }

    @Test
    void gerarRelatorioConsultasPorVeterinario_DeveGerarRelatorioComSucesso() {
        // Arrange
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(LocalTime.MAX);
        
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(veterinario));
        when(consultaRepository.findByVeterinarioAndPeriodo(eq(veterinario), any(), any())).thenReturn(consultas);

        // Act
        RelatorioConsultasDTO relatorio = relatorioService.gerarRelatorioConsultasPorVeterinario(1L, dataInicio, dataFim);

        // Assert
        assertNotNull(relatorio);
        assertEquals(dataInicio, relatorio.getDataInicio());
        assertEquals(dataFim, relatorio.getDataFim());
        assertEquals(1L, relatorio.getTotalConsultas());
        assertNotNull(relatorio.getConsultasPorVeterinario());
        assertTrue(relatorio.getConsultasPorVeterinario().containsKey(1L));
        verify(usuarioRepository).findById(1L);
        verify(consultaRepository).findByVeterinarioAndPeriodo(eq(veterinario), any(), any());
    }

    @Test
    void gerarRelatorioFaturamento_DeveGerarRelatorioComSucesso() {
        // Arrange
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(LocalTime.MAX);
        
        when(consultaRepository.findByDataHoraInicioBetween(inicio, fim)).thenReturn(consultas);

        // Act
        RelatorioFaturamentoDTO relatorio = relatorioService.gerarRelatorioFaturamento(dataInicio, dataFim);

        // Assert
        assertNotNull(relatorio);
        assertEquals(dataInicio, relatorio.getDataInicio());
        assertEquals(dataFim, relatorio.getDataFim());
        assertNotNull(relatorio.getFaturamentoTotal());
        assertTrue(relatorio.getFaturamentoTotal().compareTo(BigDecimal.ZERO) > 0);
        assertNotNull(relatorio.getFaturamentoPorServico());
        assertNotNull(relatorio.getFaturamentoPorDia());
        assertNotNull(relatorio.getFaturamentoPorVeterinario());
        verify(consultaRepository).findByDataHoraInicioBetween(inicio, fim);
    }
}
