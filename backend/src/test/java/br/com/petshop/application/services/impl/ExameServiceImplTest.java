package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.ExameDTO;
import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Consulta;
import br.com.petshop.domain.entities.Exame;
import br.com.petshop.domain.repositories.AnimalRepository;
import br.com.petshop.domain.repositories.ConsultaRepository;
import br.com.petshop.domain.repositories.ExameRepository;
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
class ExameServiceImplTest {

    @Mock
    private ExameRepository exameRepository;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private ConsultaRepository consultaRepository;

    @InjectMocks
    private ExameServiceImpl exameService;

    private Animal animal;
    private Consulta consulta;
    private Exame exame;
    private ExameDTO exameDTO;

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

        // Configurar exame
        exame = new Exame();
        exame.setId(1L);
        exame.setNome("Hemograma Completo");
        exame.setTipo("Sangue");
        exame.setResultado("Normal");
        exame.setDataRealizacao(LocalDate.of(2025, 6, 15));
        exame.setDataResultado(LocalDate.of(2025, 6, 16));
        exame.setObservacoes("Todos os parâmetros dentro da normalidade");
        exame.setAnimal(animal);
        exame.setConsulta(consulta);

        // Configurar DTO
        exameDTO = new ExameDTO();
        exameDTO.setId(1L);
        exameDTO.setNome("Hemograma Completo");
        exameDTO.setTipo("Sangue");
        exameDTO.setResultado("Normal");
        exameDTO.setDataRealizacao(LocalDate.of(2025, 6, 15));
        exameDTO.setDataResultado(LocalDate.of(2025, 6, 16));
        exameDTO.setObservacoes("Todos os parâmetros dentro da normalidade");
        exameDTO.setAnimalId(1L);
        exameDTO.setAnimalNome("Rex");
        exameDTO.setConsultaId(1L);
    }

    @Test
    @DisplayName("Deve buscar todos os exames com sucesso")
    void buscarTodos() {
        // Arrange
        when(exameRepository.findAll()).thenReturn(Arrays.asList(exame));

        // Act
        List<ExameDTO> resultado = exameService.buscarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Hemograma Completo", resultado.get(0).getNome());
        verify(exameRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar exame por ID com sucesso")
    void buscarPorId() {
        // Arrange
        when(exameRepository.findById(1L)).thenReturn(Optional.of(exame));

        // Act
        ExameDTO resultado = exameService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Hemograma Completo", resultado.getNome());
        verify(exameRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar exame inexistente")
    void buscarPorId_ExameInexistente() {
        // Arrange
        when(exameRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> exameService.buscarPorId(99L));
        verify(exameRepository).findById(99L);
    }

    @Test
    @DisplayName("Deve buscar exames por animal com sucesso")
    void buscarPorAnimal() {
        // Arrange
        when(exameRepository.findByAnimalId(1L)).thenReturn(Arrays.asList(exame));

        // Act
        List<ExameDTO> resultado = exameService.buscarPorAnimal(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Hemograma Completo", resultado.get(0).getNome());
        assertEquals(1L, resultado.get(0).getAnimalId());
        verify(exameRepository).findByAnimalId(1L);
    }

    @Test
    @DisplayName("Deve buscar exames por consulta com sucesso")
    void buscarPorConsulta() {
        // Arrange
        when(exameRepository.findByConsultaId(1L)).thenReturn(Arrays.asList(exame));

        // Act
        List<ExameDTO> resultado = exameService.buscarPorConsulta(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Hemograma Completo", resultado.get(0).getNome());
        assertEquals(1L, resultado.get(0).getConsultaId());
        verify(exameRepository).findByConsultaId(1L);
    }

    @Test
    @DisplayName("Deve buscar exames por tipo com sucesso")
    void buscarPorTipo() {
        // Arrange
        when(exameRepository.findByTipo("Sangue")).thenReturn(Arrays.asList(exame));

        // Act
        List<ExameDTO> resultado = exameService.buscarPorTipo("Sangue");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Hemograma Completo", resultado.get(0).getNome());
        assertEquals("Sangue", resultado.get(0).getTipo());
        verify(exameRepository).findByTipo("Sangue");
    }

    @Test
    @DisplayName("Deve buscar exames por nome com sucesso")
    void buscarPorNome() {
        // Arrange
        when(exameRepository.findByNomeContainingIgnoreCase("Hemograma")).thenReturn(Arrays.asList(exame));

        // Act
        List<ExameDTO> resultado = exameService.buscarPorNome("Hemograma");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Hemograma Completo", resultado.get(0).getNome());
        verify(exameRepository).findByNomeContainingIgnoreCase("Hemograma");
    }

    @Test
    @DisplayName("Deve buscar exames por período com sucesso")
    void buscarPorPeriodo() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 6, 1);
        LocalDate fim = LocalDate.of(2025, 6, 30);
        when(exameRepository.findByDataRealizacaoBetween(inicio, fim)).thenReturn(Arrays.asList(exame));

        // Act
        List<ExameDTO> resultado = exameService.buscarPorPeriodo(inicio, fim);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Hemograma Completo", resultado.get(0).getNome());
        assertEquals(LocalDate.of(2025, 6, 15), resultado.get(0).getDataRealizacao());
        verify(exameRepository).findByDataRealizacaoBetween(inicio, fim);
    }

    @Test
    @DisplayName("Deve salvar exame com sucesso")
    void salvar() {
        // Arrange
        ExameDTO novoExameDTO = new ExameDTO();
        novoExameDTO.setNome("Ultrassom Abdominal");
        novoExameDTO.setTipo("Imagem");
        novoExameDTO.setDataRealizacao(LocalDate.of(2025, 6, 18));
        novoExameDTO.setAnimalId(1L);
        novoExameDTO.setConsultaId(1L);

        Exame novoExame = new Exame();
        novoExame.setId(2L);
        novoExame.setNome("Ultrassom Abdominal");
        novoExame.setTipo("Imagem");
        novoExame.setDataRealizacao(LocalDate.of(2025, 6, 18));
        novoExame.setAnimal(animal);
        novoExame.setConsulta(consulta);

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(exameRepository.save(any(Exame.class))).thenReturn(novoExame);

        // Act
        ExameDTO resultado = exameService.salvar(novoExameDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals("Ultrassom Abdominal", resultado.getNome());
        assertEquals("Imagem", resultado.getTipo());
        verify(animalRepository).findById(1L);
        verify(consultaRepository).findById(1L);
        verify(exameRepository).save(any(Exame.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar exame com animal inexistente")
    void salvar_AnimalInexistente() {
        // Arrange
        ExameDTO novoExameDTO = new ExameDTO();
        novoExameDTO.setNome("Ultrassom Abdominal");
        novoExameDTO.setAnimalId(99L);

        when(animalRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> exameService.salvar(novoExameDTO));
        verify(animalRepository).findById(99L);
        verify(exameRepository, never()).save(any(Exame.class));
    }

    @Test
    @DisplayName("Deve atualizar exame com sucesso")
    void atualizar() {
        // Arrange
        ExameDTO atualizacaoDTO = new ExameDTO();
        atualizacaoDTO.setNome("Hemograma Completo Atualizado");
        atualizacaoDTO.setResultado("Alterado");
        atualizacaoDTO.setObservacoes("Alterações nos níveis de glicose");
        atualizacaoDTO.setAnimalId(1L);
        atualizacaoDTO.setConsultaId(1L);

        Exame exameAtualizado = new Exame();
        exameAtualizado.setId(1L);
        exameAtualizado.setNome("Hemograma Completo Atualizado");
        exameAtualizado.setTipo("Sangue");
        exameAtualizado.setResultado("Alterado");
        exameAtualizado.setDataRealizacao(LocalDate.of(2025, 6, 15));
        exameAtualizado.setDataResultado(LocalDate.of(2025, 6, 16));
        exameAtualizado.setObservacoes("Alterações nos níveis de glicose");
        exameAtualizado.setAnimal(animal);
        exameAtualizado.setConsulta(consulta);

        when(exameRepository.findById(1L)).thenReturn(Optional.of(exame));
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(exameRepository.save(any(Exame.class))).thenReturn(exameAtualizado);

        // Act
        ExameDTO resultado = exameService.atualizar(1L, atualizacaoDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Hemograma Completo Atualizado", resultado.getNome());
        assertEquals("Alterado", resultado.getResultado());
        assertEquals("Alterações nos níveis de glicose", resultado.getObservacoes());
        verify(exameRepository).findById(1L);
        verify(animalRepository).findById(1L);
        verify(consultaRepository).findById(1L);
        verify(exameRepository).save(any(Exame.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar exame inexistente")
    void atualizar_ExameInexistente() {
        // Arrange
        ExameDTO atualizacaoDTO = new ExameDTO();
        atualizacaoDTO.setNome("Hemograma Completo Atualizado");

        when(exameRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> exameService.atualizar(99L, atualizacaoDTO));
        verify(exameRepository).findById(99L);
        verify(exameRepository, never()).save(any(Exame.class));
    }

    @Test
    @DisplayName("Deve remover exame com sucesso")
    void remover() {
        // Arrange
        when(exameRepository.findById(1L)).thenReturn(Optional.of(exame));
        doNothing().when(exameRepository).delete(exame);

        // Act
        exameService.remover(1L);

        // Assert
        verify(exameRepository).findById(1L);
        verify(exameRepository).delete(exame);
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover exame inexistente")
    void remover_ExameInexistente() {
        // Arrange
        when(exameRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> exameService.remover(99L));
        verify(exameRepository).findById(99L);
        verify(exameRepository, never()).delete(any(Exame.class));
    }
}
