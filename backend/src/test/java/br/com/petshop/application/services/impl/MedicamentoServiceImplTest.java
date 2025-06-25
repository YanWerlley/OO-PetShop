package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.MedicamentoDTO;
import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Consulta;
import br.com.petshop.domain.entities.Medicamento;
import br.com.petshop.domain.repositories.AnimalRepository;
import br.com.petshop.domain.repositories.ConsultaRepository;
import br.com.petshop.domain.repositories.MedicamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicamentoServiceImplTest {

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private ConsultaRepository consultaRepository;

    @InjectMocks
    private MedicamentoServiceImpl medicamentoService;

    private Animal animal;
    private Consulta consulta;
    private Medicamento medicamento;
    private MedicamentoDTO medicamentoDTO;

    @BeforeEach
    void setUp() {
        // Configurar animal
        animal = new Animal();
        animal.setId(1L);
        animal.setNome("Rex");
        animal.setEspecie("Cachorro");
        animal.setRaca("Labrador");

        // Configurar consulta
        consulta = new Consulta();
        consulta.setId(1L);
        consulta.setAnimal(animal);

        // Configurar medicamento
        medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("Amoxicilina");
        medicamento.setDosagem("50mg");
        medicamento.setFrequencia("12/12h");
        medicamento.setInstrucoes("Administrar com alimento");
        medicamento.setDataInicio(LocalDate.of(2025, 6, 15));
        medicamento.setDataFim(LocalDate.of(2025, 6, 25));
        medicamento.setObservacoes("Completar o tratamento mesmo com melhora dos sintomas");
        medicamento.setAnimal(animal);
        medicamento.setConsulta(consulta);

        // Configurar DTO
        medicamentoDTO = new MedicamentoDTO();
        medicamentoDTO.setId(1L);
        medicamentoDTO.setNome("Amoxicilina");
        medicamentoDTO.setDosagem("50mg");
        medicamentoDTO.setFrequencia("12/12h");
        medicamentoDTO.setInstrucoes("Administrar com alimento");
        medicamentoDTO.setDataInicio(LocalDate.of(2025, 6, 15));
        medicamentoDTO.setDataFim(LocalDate.of(2025, 6, 25));
        medicamentoDTO.setObservacoes("Completar o tratamento mesmo com melhora dos sintomas");
        medicamentoDTO.setAnimalId(1L);
        medicamentoDTO.setAnimalNome("Rex");
        medicamentoDTO.setConsultaId(1L);
    }

    @Test
    @DisplayName("Deve buscar todos os medicamentos com sucesso")
    void buscarTodos() {
        // Arrange
        when(medicamentoRepository.findAll()).thenReturn(Arrays.asList(medicamento));

        // Act
        List<MedicamentoDTO> resultado = medicamentoService.buscarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Amoxicilina", resultado.get(0).getNome());
        verify(medicamentoRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar medicamento por ID com sucesso")
    void buscarPorId() {
        // Arrange
        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(medicamento));

        // Act
        MedicamentoDTO resultado = medicamentoService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Amoxicilina", resultado.getNome());
        verify(medicamentoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar medicamento inexistente")
    void buscarPorId_MedicamentoInexistente() {
        // Arrange
        when(medicamentoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> medicamentoService.buscarPorId(99L));
        verify(medicamentoRepository).findById(99L);
    }

    @Test
    @DisplayName("Deve buscar medicamentos por animal com sucesso")
    void buscarPorAnimal() {
        // Arrange
        when(medicamentoRepository.findByAnimalId(1L)).thenReturn(Arrays.asList(medicamento));

        // Act
        List<MedicamentoDTO> resultado = medicamentoService.buscarPorAnimal(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Amoxicilina", resultado.get(0).getNome());
        assertEquals(1L, resultado.get(0).getAnimalId());
        verify(medicamentoRepository).findByAnimalId(1L);
    }

    @Test
    @DisplayName("Deve buscar medicamentos por consulta com sucesso")
    void buscarPorConsulta() {
        // Arrange
        when(medicamentoRepository.findByConsultaId(1L)).thenReturn(Arrays.asList(medicamento));

        // Act
        List<MedicamentoDTO> resultado = medicamentoService.buscarPorConsulta(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Amoxicilina", resultado.get(0).getNome());
        assertEquals(1L, resultado.get(0).getConsultaId());
        verify(medicamentoRepository).findByConsultaId(1L);
    }

    @Test
    @DisplayName("Deve buscar medicamentos por nome com sucesso")
    void buscarPorNome() {
        // Arrange
        when(medicamentoRepository.findByNomeContainingIgnoreCase("Amox")).thenReturn(Arrays.asList(medicamento));

        // Act
        List<MedicamentoDTO> resultado = medicamentoService.buscarPorNome("Amox");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Amoxicilina", resultado.get(0).getNome());
        verify(medicamentoRepository).findByNomeContainingIgnoreCase("Amox");
    }

    @Test
    @DisplayName("Deve buscar medicamentos por período de início com sucesso")
    void buscarPorPeriodoInicio() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 6, 1);
        LocalDate fim = LocalDate.of(2025, 6, 30);
        when(medicamentoRepository.findByDataInicioBetween(inicio, fim)).thenReturn(Arrays.asList(medicamento));

        // Act
        List<MedicamentoDTO> resultado = medicamentoService.buscarPorPeriodoInicio(inicio, fim);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Amoxicilina", resultado.get(0).getNome());
        assertEquals(LocalDate.of(2025, 6, 15), resultado.get(0).getDataInicio());
        verify(medicamentoRepository).findByDataInicioBetween(inicio, fim);
    }

    @Test
    @DisplayName("Deve buscar medicamentos por período de fim com sucesso")
    void buscarPorPeriodoFim() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 6, 20);
        LocalDate fim = LocalDate.of(2025, 6, 30);
        when(medicamentoRepository.findByDataFimBetween(inicio, fim)).thenReturn(Arrays.asList(medicamento));

        // Act
        List<MedicamentoDTO> resultado = medicamentoService.buscarPorPeriodoFim(inicio, fim);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Amoxicilina", resultado.get(0).getNome());
        assertEquals(LocalDate.of(2025, 6, 25), resultado.get(0).getDataFim());
        verify(medicamentoRepository).findByDataFimBetween(inicio, fim);
    }

    @Test
    @DisplayName("Deve salvar medicamento com sucesso")
    void salvar() {
        // Arrange
        MedicamentoDTO novoMedicamentoDTO = new MedicamentoDTO();
        novoMedicamentoDTO.setNome("Prednisolona");
        novoMedicamentoDTO.setDosagem("10mg");
        novoMedicamentoDTO.setFrequencia("24h");
        novoMedicamentoDTO.setInstrucoes("Administrar pela manhã");
        novoMedicamentoDTO.setDataInicio(LocalDate.of(2025, 6, 16));
        novoMedicamentoDTO.setDataFim(LocalDate.of(2025, 6, 23));
        novoMedicamentoDTO.setAnimalId(1L);
        novoMedicamentoDTO.setConsultaId(1L);

        Medicamento novoMedicamento = new Medicamento();
        novoMedicamento.setId(2L);
        novoMedicamento.setNome("Prednisolona");
        novoMedicamento.setDosagem("10mg");
        novoMedicamento.setFrequencia("24h");
        novoMedicamento.setInstrucoes("Administrar pela manhã");
        novoMedicamento.setDataInicio(LocalDate.of(2025, 6, 16));
        novoMedicamento.setDataFim(LocalDate.of(2025, 6, 23));
        novoMedicamento.setAnimal(animal);
        novoMedicamento.setConsulta(consulta);

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(medicamentoRepository.save(any(Medicamento.class))).thenReturn(novoMedicamento);

        // Act
        MedicamentoDTO resultado = medicamentoService.salvar(novoMedicamentoDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals("Prednisolona", resultado.getNome());
        assertEquals("10mg", resultado.getDosagem());
        verify(animalRepository).findById(1L);
        verify(consultaRepository).findById(1L);
        verify(medicamentoRepository).save(any(Medicamento.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar medicamento com animal inexistente")
    void salvar_AnimalInexistente() {
        // Arrange
        MedicamentoDTO novoMedicamentoDTO = new MedicamentoDTO();
        novoMedicamentoDTO.setNome("Prednisolona");
        novoMedicamentoDTO.setAnimalId(99L);

        when(animalRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> medicamentoService.salvar(novoMedicamentoDTO));
        verify(animalRepository).findById(99L);
        verify(medicamentoRepository, never()).save(any(Medicamento.class));
    }

    @Test
    @DisplayName("Deve atualizar medicamento com sucesso")
    void atualizar() {
        // Arrange
        MedicamentoDTO atualizacaoDTO = new MedicamentoDTO();
        atualizacaoDTO.setDosagem("75mg");
        atualizacaoDTO.setObservacoes("Aumentar dose conforme orientação");
        atualizacaoDTO.setAnimalId(1L);
        atualizacaoDTO.setConsultaId(1L);

        Medicamento medicamentoAtualizado = new Medicamento();
        medicamentoAtualizado.setId(1L);
        medicamentoAtualizado.setNome("Amoxicilina");
        medicamentoAtualizado.setDosagem("75mg");
        medicamentoAtualizado.setFrequencia("12/12h");
        medicamentoAtualizado.setInstrucoes("Administrar com alimento");
        medicamentoAtualizado.setDataInicio(LocalDate.of(2025, 6, 15));
        medicamentoAtualizado.setDataFim(LocalDate.of(2025, 6, 25));
        medicamentoAtualizado.setObservacoes("Aumentar dose conforme orientação");
        medicamentoAtualizado.setAnimal(animal);
        medicamentoAtualizado.setConsulta(consulta);

        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(medicamento));
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(medicamentoRepository.save(any(Medicamento.class))).thenReturn(medicamentoAtualizado);

        // Act
        MedicamentoDTO resultado = medicamentoService.atualizar(1L, atualizacaoDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Amoxicilina", resultado.getNome());
        assertEquals("75mg", resultado.getDosagem());
        assertEquals("Aumentar dose conforme orientação", resultado.getObservacoes());
        verify(medicamentoRepository).findById(1L);
        verify(animalRepository).findById(1L);
        verify(consultaRepository).findById(1L);
        verify(medicamentoRepository).save(any(Medicamento.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar medicamento inexistente")
    void atualizar_MedicamentoInexistente() {
        // Arrange
        MedicamentoDTO atualizacaoDTO = new MedicamentoDTO();
        atualizacaoDTO.setDosagem("75mg");

        when(medicamentoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> medicamentoService.atualizar(99L, atualizacaoDTO));
        verify(medicamentoRepository).findById(99L);
        verify(medicamentoRepository, never()).save(any(Medicamento.class));
    }

    @Test
    @DisplayName("Deve remover medicamento com sucesso")
    void remover() {
        // Arrange
        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(medicamento));
        doNothing().when(medicamentoRepository).delete(medicamento);

        // Act
        medicamentoService.remover(1L);

        // Assert
        verify(medicamentoRepository).findById(1L);
        verify(medicamentoRepository).delete(medicamento);
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover medicamento inexistente")
    void remover_MedicamentoInexistente() {
        // Arrange
        when(medicamentoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> medicamentoService.remover(99L));
        verify(medicamentoRepository).findById(99L);
        verify(medicamentoRepository, never()).delete(any(Medicamento.class));
    }
}
