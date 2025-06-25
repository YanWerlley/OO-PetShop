package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.ConsultaDTO;
import br.com.petshop.domain.entities.Agendamento;
import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Consulta;
import br.com.petshop.domain.entities.Exame;
import br.com.petshop.domain.entities.Medicamento;
import br.com.petshop.domain.entities.Usuario;
import br.com.petshop.domain.repositories.AgendamentoRepository;
import br.com.petshop.domain.repositories.ConsultaRepository;
import br.com.petshop.domain.repositories.ExameRepository;
import br.com.petshop.domain.repositories.MedicamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultaServiceImplTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private ExameRepository exameRepository;

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @InjectMocks
    private ConsultaServiceImpl consultaService;

    private Consulta consulta;
    private Agendamento agendamento;
    private Exame exame;
    private Medicamento medicamento;
    private Animal animal;
    private Usuario veterinario;

    @BeforeEach
    void setUp() {
        // Configurar animal
        animal = new Animal();
        animal.setId(1L);
        animal.setNome("Rex");
        animal.setEspecie("Cachorro");
        animal.setRaca("Labrador");
        
        // Configurar veterinário
        veterinario = new Usuario();
        veterinario.setId(1L);
        veterinario.setNome("Dr. João Silva");
        veterinario.setEmail("joao.silva@petshop.com");
        
        // Configurar agendamento
        agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setStatus(Agendamento.StatusAgendamento.CONFIRMADO);
        agendamento.setDataHora(LocalDateTime.now().plusHours(1));
        agendamento.setAnimal(animal);

        // Configurar consulta
        consulta = new Consulta();
        consulta.setId(1L);
        consulta.setAgendamento(agendamento);
        consulta.setDataHoraInicio(LocalDateTime.now());
        consulta.setDiagnostico("Diagnóstico de teste");
        consulta.setObservacoes("Observações de teste");
        consulta.setExames(new ArrayList<>());
        consulta.setMedicamentos(new ArrayList<>());
        consulta.setVeterinario(veterinario);
        consulta.setStatus(Consulta.StatusConsulta.EM_ANDAMENTO);

        // Configurar exame
        exame = new Exame();
        exame.setId(1L);
        exame.setNome("Exame de sangue");
        exame.setTipo("SIMPLES");
        exame.setAnimal(animal);
        exame.setConsulta(consulta);

        // Configurar medicamento
        medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("Medicamento teste");
        medicamento.setDosagem("10mg");
        medicamento.setInstrucoes("Tomar 2x ao dia");
        medicamento.setAnimal(animal);
        medicamento.setConsulta(consulta);
    }

    @Test
    void iniciarConsulta_DeveIniciarConsultaComSucesso() {
        // Arrange
        when(agendamentoRepository.findById(1L)).thenReturn(Optional.of(agendamento));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        // Act
        ConsultaDTO resultado = consultaService.iniciarConsulta(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(agendamentoRepository).findById(1L);
        verify(consultaRepository).save(any(Consulta.class));
    }

    @Test
    void finalizarConsulta_DeveFinalizarConsultaComSucesso() {
        // Arrange
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setId(1L);
        consultaDTO.setDiagnostico("Diagnóstico atualizado");
        consultaDTO.setObservacoes("Observações atualizadas");

        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        // Act
        ConsultaDTO resultado = consultaService.finalizarConsulta(1L, consultaDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Diagnóstico atualizado", resultado.getDiagnostico());
        assertNotNull(resultado.getDataHoraFim());
        verify(consultaRepository).findById(1L);
        verify(consultaRepository).save(any(Consulta.class));
    }

    @Test
    void buscarConsultaPorId_DeveRetornarConsulta() {
        // Arrange
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));

        // Act
        ConsultaDTO resultado = consultaService.buscarConsultaPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(consultaRepository).findById(1L);
    }

    @Test
    void listarConsultas_DeveRetornarPaginaDeConsultas() {
        // Arrange
        List<Consulta> consultas = List.of(consulta);
        Page<Consulta> consultaPage = new PageImpl<>(consultas);
        Pageable pageable = PageRequest.of(0, 10);

        when(consultaRepository.findAll(pageable)).thenReturn(consultaPage);

        // Act
        Page<ConsultaDTO> resultado = consultaService.listarConsultas(pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        verify(consultaRepository).findAll(pageable);
    }

    @Test
    void adicionarExame_DeveAdicionarExameAConsulta() {
        // Arrange
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(exameRepository.findById(1L)).thenReturn(Optional.of(exame));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        // Act
        ConsultaDTO resultado = consultaService.adicionarExame(1L, 1L);

        // Assert
        assertNotNull(resultado);
        verify(consultaRepository).findById(1L);
        verify(exameRepository).findById(1L);
        verify(consultaRepository).save(any(Consulta.class));
    }

    @Test
    @DisplayName("Deve adicionar medicamento à consulta com sucesso")
    void adicionarMedicamento_DeveAdicionarMedicamentoAConsulta() {
        // Arrange
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(medicamento));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        // Act
        ConsultaDTO resultado = consultaService.adicionarMedicamento(1L, 1L);

        // Assert
        assertNotNull(resultado);
        verify(consultaRepository).findById(1L);
        verify(medicamentoRepository).findById(1L);
        verify(consultaRepository).save(any(Consulta.class));
    }
    
    @Test
    @DisplayName("Deve remover exame da consulta com sucesso")
    void removerExame_DeveRemoverExameDaConsulta() {
        // Arrange
        consulta.getExames().add(exame);
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(exameRepository.findById(1L)).thenReturn(Optional.of(exame));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        // Act
        ConsultaDTO resultado = consultaService.removerExame(1L, 1L);

        // Assert
        assertNotNull(resultado);
        verify(consultaRepository).findById(1L);
        verify(exameRepository).findById(1L);
        verify(consultaRepository).save(any(Consulta.class));
    }
    
    @Test
    @DisplayName("Deve remover medicamento da consulta com sucesso")
    void removerMedicamento_DeveRemoverMedicamentoDaConsulta() {
        // Arrange
        consulta.getMedicamentos().add(medicamento);
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(medicamento));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        // Act
        ConsultaDTO resultado = consultaService.removerMedicamento(1L, 1L);

        // Assert
        assertNotNull(resultado);
        verify(consultaRepository).findById(1L);
        verify(medicamentoRepository).findById(1L);
        verify(consultaRepository).save(any(Consulta.class));
    }
    
    @Test
    @DisplayName("Deve buscar consultas por animal com sucesso")
    void buscarPorAnimal_DeveRetornarConsultasDoAnimal() {
        // Arrange
        List<Consulta> consultas = List.of(consulta);
        when(consultaRepository.findByAgendamentoAnimalId(1L)).thenReturn(consultas);

        // Act
        List<ConsultaDTO> resultado = consultaService.buscarPorAnimal(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        verify(consultaRepository).findByAgendamentoAnimalId(1L);
    }
    
    @Test
    @DisplayName("Deve buscar consultas por veterinário com sucesso")
    void buscarPorVeterinario_DeveRetornarConsultasDoVeterinario() {
        // Arrange
        List<Consulta> consultas = List.of(consulta);
        when(consultaRepository.findByVeterinarioId(1L)).thenReturn(consultas);

        // Act
        List<ConsultaDTO> resultado = consultaService.buscarPorVeterinario(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        verify(consultaRepository).findByVeterinarioId(1L);
    }
    
    @Test
    @DisplayName("Deve buscar consultas por status com sucesso")
    void buscarPorStatus_DeveRetornarConsultasComStatus() {
        // Arrange
        List<Consulta> consultas = List.of(consulta);
        when(consultaRepository.findByStatus(Consulta.StatusConsulta.EM_ANDAMENTO)).thenReturn(consultas);

        // Act
        List<ConsultaDTO> resultado = consultaService.buscarPorStatus(Consulta.StatusConsulta.EM_ANDAMENTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        verify(consultaRepository).findByStatus(Consulta.StatusConsulta.EM_ANDAMENTO);
    }
    
    @Test
    @DisplayName("Deve buscar consultas por período com sucesso")
    void buscarPorPeriodo_DeveRetornarConsultasNoPeriodo() {
        // Arrange
        List<Consulta> consultas = List.of(consulta);
        LocalDateTime inicio = LocalDateTime.now().minusDays(1);
        LocalDateTime fim = LocalDateTime.now().plusDays(1);
        when(consultaRepository.findByDataHoraInicioBetween(inicio, fim)).thenReturn(consultas);

        // Act
        List<ConsultaDTO> resultado = consultaService.buscarPorPeriodo(inicio, fim);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        verify(consultaRepository).findByDataHoraInicioBetween(inicio, fim);
    }
}
