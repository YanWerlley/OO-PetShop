package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.DashboardDTO;
import br.com.petshop.domain.entities.*;
import br.com.petshop.domain.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceImplTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private ExameRepository exameRepository;

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    private List<Consulta> consultas;
    private List<Agendamento> agendamentos;
    private List<Usuario> veterinarios;
    private List<Usuario> clientes;
    private List<Animal> animais;
    private Usuario veterinario;
    private Usuario cliente;
    private Animal animal;

    @BeforeEach
    void setUp() {
        // Configurar usuários
        veterinario = new Usuario();
        veterinario.setId(1L);
        veterinario.setNome("Dr. Veterinário");
        veterinario.setEmail("vet@example.com");
        veterinario.setRole(Usuario.Role.VETERINARIO);

        cliente = new Usuario();
        cliente.setId(2L);
        cliente.setNome("Cliente Teste");
        cliente.setEmail("cliente@example.com");
        cliente.setRole(Usuario.Role.CLIENTE);

        // Configurar animal
        animal = new Animal();
        animal.setId(1L);
        animal.setNome("Animal Teste");
        animal.setEspecie("Cachorro");
        animal.setRaca("Labrador");
        animal.setCliente(cliente);

        // Configurar agendamentos
        agendamentos = new ArrayList<>();
        
        Agendamento agendamento1 = new Agendamento();
        agendamento1.setId(1L);
        agendamento1.setVeterinario(veterinario);
        agendamento1.setAnimal(animal);
        agendamento1.setDataHora(LocalDateTime.now().minusDays(15));
        agendamento1.setStatus(Agendamento.StatusAgendamento.CONFIRMADO);
        
        Agendamento agendamento2 = new Agendamento();
        agendamento2.setId(2L);
        agendamento2.setVeterinario(veterinario);
        agendamento2.setAnimal(animal);
        agendamento2.setDataHora(LocalDateTime.now().plusDays(2));
        agendamento2.setStatus(Agendamento.StatusAgendamento.PENDENTE);
        
        agendamentos.add(agendamento1);
        agendamentos.add(agendamento2);

        // Configurar consultas
        consultas = new ArrayList<>();
        
        Consulta consulta1 = new Consulta();
        consulta1.setId(1L);
        consulta1.setAgendamento(agendamento1);
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
        exame.setValor(new BigDecimal("150.00"));
        consulta1.getExames().add(exame);
        
        // Adicionar medicamento à consulta
        Medicamento medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("Medicamento teste");
        medicamento.setDosagem("10mg");
        medicamento.setInstrucoes("Tomar 2x ao dia");
        medicamento.setValor(new BigDecimal("50.00"));
        consulta1.getMedicamentos().add(medicamento);
        
        consultas.add(consulta1);
        
        // Configurar listas
        veterinarios = List.of(veterinario);
        clientes = List.of(cliente);
        animais = List.of(animal);
    }

    @Test
    void gerarDashboardGeral_DeveGerarDashboardComSucesso() {
        // Arrange
        when(usuarioRepository.countByRole(Usuario.Role.CLIENTE)).thenReturn(1L);
        when(animalRepository.count()).thenReturn(1L);
        when(consultaRepository.count()).thenReturn(1L);
        when(consultaRepository.findAll()).thenReturn(consultas);
        when(agendamentoRepository.countByStatus(Agendamento.StatusAgendamento.PENDENTE)).thenReturn(1L);
        when(agendamentoRepository.countByStatus(Agendamento.StatusAgendamento.CONFIRMADO)).thenReturn(1L);
        when(agendamentoRepository.countByStatus(Agendamento.StatusAgendamento.CANCELADO)).thenReturn(0L);
        when(usuarioRepository.findByRole(Usuario.Role.VETERINARIO)).thenReturn(veterinarios);

        // Act
        DashboardDTO dashboard = dashboardService.gerarDashboardGeral();

        // Assert
        assertNotNull(dashboard);
        assertEquals(1L, dashboard.getTotalClientes());
        assertEquals(1L, dashboard.getTotalAnimais());
        assertEquals(1L, dashboard.getTotalConsultas());
        assertNotNull(dashboard.getFaturamentoTotal());
        assertTrue(dashboard.getFaturamentoTotal().compareTo(BigDecimal.ZERO) > 0);
        assertNotNull(dashboard.getOcupacaoVeterinarios());
        assertNotNull(dashboard.getDistribuicaoPorEspecie());
        assertNotNull(dashboard.getDistribuicaoPorRaca());
        assertNotNull(dashboard.getTendenciaConsultas());
        assertNotNull(dashboard.getTendenciaFaturamento());
    }

    @Test
    void gerarDashboardPorVeterinario_DeveGerarDashboardComSucesso() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(veterinario));
        when(consultaRepository.findByVeterinarioId(1L)).thenReturn(consultas);
        when(agendamentoRepository.findByVeterinarioIdAndStatus(1L, Agendamento.StatusAgendamento.PENDENTE)).thenReturn(List.of(agendamentos.get(1)));
        when(agendamentoRepository.findByVeterinarioIdAndStatus(1L, Agendamento.StatusAgendamento.CONFIRMADO)).thenReturn(List.of(agendamentos.get(0)));

        // Act
        DashboardDTO dashboard = dashboardService.gerarDashboardPorVeterinario(1L);

        // Assert
        assertNotNull(dashboard);
        assertEquals(1L, dashboard.getTotalConsultas());
        assertNotNull(dashboard.getFaturamentoTotal());
        assertTrue(dashboard.getFaturamentoTotal().compareTo(BigDecimal.ZERO) > 0);
        assertNotNull(dashboard.getAgendamentosPendentes());
        assertEquals(1, dashboard.getAgendamentosPendentes());
        assertNotNull(dashboard.getAgendamentosConfirmados());
        assertEquals(1, dashboard.getAgendamentosConfirmados());
        assertNotNull(dashboard.getDistribuicaoPorEspecie());
        assertNotNull(dashboard.getDistribuicaoPorRaca());
        assertNotNull(dashboard.getTendenciaConsultas());
        assertNotNull(dashboard.getTendenciaFaturamento());
    }

    @Test
    void gerarDashboardPorCliente_DeveGerarDashboardComSucesso() {
        // Arrange
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(cliente));
        when(animalRepository.findByClienteId(2L)).thenReturn(animais);
        when(consultaRepository.findByClienteId(2L)).thenReturn(consultas);
        when(agendamentoRepository.findByClienteIdAndStatus(2L, Agendamento.StatusAgendamento.PENDENTE)).thenReturn(List.of(agendamentos.get(1)));
        when(agendamentoRepository.findByClienteIdAndStatus(2L, Agendamento.StatusAgendamento.CONFIRMADO)).thenReturn(List.of(agendamentos.get(0)));

        // Act
        DashboardDTO dashboard = dashboardService.gerarDashboardPorCliente(2L);

        // Assert
        assertNotNull(dashboard);
        assertEquals(1L, dashboard.getTotalAnimais());
        assertEquals(1L, dashboard.getTotalConsultas());
        assertNotNull(dashboard.getGastoTotal());
        assertTrue(dashboard.getGastoTotal().compareTo(BigDecimal.ZERO) > 0);
        assertNotNull(dashboard.getAgendamentosPendentes());
        assertEquals(1, dashboard.getAgendamentosPendentes());
        assertNotNull(dashboard.getAgendamentosConfirmados());
        assertEquals(1, dashboard.getAgendamentosConfirmados());
        assertNotNull(dashboard.getDistribuicaoPorEspecie());
        assertNotNull(dashboard.getDistribuicaoPorRaca());
        assertNotNull(dashboard.getHistoricoConsultas());
        assertNotNull(dashboard.getHistoricoGastos());
    }

    @Test
    void gerarDashboardPorData_DeveGerarDashboardComSucesso() {
        // Arrange
        LocalDate data = LocalDate.now();
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.plusDays(1).atStartOfDay().minusNanos(1);
        
        when(consultaRepository.findByDataHoraInicioBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(consultas);
        when(agendamentoRepository.findByDataHoraBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(agendamentos);

        // Act
        DashboardDTO dashboard = dashboardService.gerarDashboardPorData(data);

        // Assert
        assertNotNull(dashboard);
        assertEquals(data, dashboard.getDataReferencia());
        assertNotNull(dashboard.getTotalConsultas());
        assertNotNull(dashboard.getFaturamentoTotal());
        assertNotNull(dashboard.getAgendamentosPendentes());
        assertNotNull(dashboard.getAgendamentosConfirmados());
        assertNotNull(dashboard.getDistribuicaoPorEspecie());
        assertNotNull(dashboard.getDistribuicaoPorRaca());
        verify(consultaRepository).findByDataHoraInicioBetween(any(LocalDateTime.class), any(LocalDateTime.class));
        verify(agendamentoRepository).findByDataHoraBetween(any(LocalDateTime.class), any(LocalDateTime.class));
    }
}
