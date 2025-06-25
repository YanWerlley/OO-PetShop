package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.VacinaDTO;
import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Cachorro;
import br.com.petshop.domain.entities.Cliente;
import br.com.petshop.domain.entities.Vacina;
import br.com.petshop.domain.repositories.AnimalRepository;
import br.com.petshop.domain.repositories.VacinaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para VacinaServiceImpl
 * 
 * @author Yan Werlley
 */
@ExtendWith(MockitoExtension.class)
class VacinaServiceImplTest {

    @Mock
    private VacinaRepository vacinaRepository;
    
    @Mock
    private AnimalRepository animalRepository;
    
    @InjectMocks
    private VacinaServiceImpl vacinaService;
    
    private Cliente cliente;
    private Animal animal;
    private Vacina vacina1;
    private Vacina vacina2;
    private VacinaDTO vacinaDTO;
    
    @BeforeEach
    void setUp() {
        // Configuração do cliente
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-01");
        cliente.setTelefone("(11) 98765-4321");
        cliente.setDataNascimento(LocalDate.of(1980, 5, 15));
        cliente.setEmail("joao.silva@email.com");
        
        // Configuração do animal
        animal = new Cachorro();
        animal.setId(1L);
        animal.setNome("Rex");
        animal.setPeso(15.5);
        animal.setCor("Marrom");
        animal.setPossueDoenca(false);
        animal.setProprietario(cliente);
        ((Cachorro) animal).setRaca("Labrador");
        ((Cachorro) animal).setAdestrado(true);
        
        // Configuração da vacina 1
        vacina1 = new Vacina();
        vacina1.setId(1L);
        vacina1.setNome("V8");
        vacina1.setDataAplicacao(LocalDate.of(2025, 1, 15));
        vacina1.setLote("L123456");
        vacina1.setDataValidade(LocalDate.of(2026, 1, 15));
        vacina1.setDataProximaDose(LocalDate.of(2025, 7, 15));
        vacina1.setAnimal(animal);
        
        // Configuração da vacina 2
        vacina2 = new Vacina();
        vacina2.setId(2L);
        vacina2.setNome("Antirrábica");
        vacina2.setDataAplicacao(LocalDate.of(2025, 2, 10));
        vacina2.setLote("L789012");
        vacina2.setDataValidade(LocalDate.of(2026, 2, 10));
        vacina2.setAnimal(animal);
        
        // Configuração do DTO de vacina
        vacinaDTO = new VacinaDTO();
        vacinaDTO.setId(1L);
        vacinaDTO.setNome("V8");
        vacinaDTO.setDataAplicacao(LocalDate.of(2025, 1, 15));
        vacinaDTO.setLote("L123456");
        vacinaDTO.setDataValidade(LocalDate.of(2026, 1, 15));
        vacinaDTO.setDataProximaDose(LocalDate.of(2025, 7, 15));
        vacinaDTO.setAnimalId(1L);
    }
    
    @Test
    @DisplayName("Deve buscar todas as vacinas com sucesso")
    void buscarTodas() {
        // Arrange
        when(vacinaRepository.findAll()).thenReturn(Arrays.asList(vacina1, vacina2));
        
        // Act
        List<VacinaDTO> resultado = vacinaService.buscarTodas();
        
        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("V8", resultado.get(0).getNome());
        assertEquals("Antirrábica", resultado.get(1).getNome());
        verify(vacinaRepository, times(1)).findAll();
    }
    
    @Test
    @DisplayName("Deve buscar uma vacina por ID com sucesso")
    void buscarPorId() {
        // Arrange
        when(vacinaRepository.findById(1L)).thenReturn(Optional.of(vacina1));
        
        // Act
        VacinaDTO resultado = vacinaService.buscarPorId(1L);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("V8", resultado.getNome());
        assertEquals("L123456", resultado.getLote());
        assertEquals(LocalDate.of(2025, 7, 15), resultado.getDataProximaDose());
        verify(vacinaRepository, times(1)).findById(1L);
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao buscar vacina com ID inexistente")
    void buscarPorIdInexistente() {
        // Arrange
        when(vacinaRepository.findById(99L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            vacinaService.buscarPorId(99L);
        });
        verify(vacinaRepository, times(1)).findById(99L);
    }
    
    @Test
    @DisplayName("Deve buscar vacinas por animal com sucesso")
    void buscarPorAnimal() {
        // Arrange
        when(vacinaRepository.findByAnimalId(1L)).thenReturn(Arrays.asList(vacina1, vacina2));
        
        // Act
        List<VacinaDTO> resultado = vacinaService.buscarPorAnimal(1L);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getAnimalId());
        assertEquals(1L, resultado.get(1).getAnimalId());
        verify(vacinaRepository, times(1)).findByAnimalId(1L);
    }
    
    @Test
    @DisplayName("Deve buscar vacinas por nome com sucesso")
    void buscarPorNome() {
        // Arrange
        when(vacinaRepository.findByNomeContainingIgnoreCase("V8")).thenReturn(List.of(vacina1));
        
        // Act
        List<VacinaDTO> resultado = vacinaService.buscarPorNome("V8");
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("V8", resultado.get(0).getNome());
        verify(vacinaRepository, times(1)).findByNomeContainingIgnoreCase("V8");
    }
    
    @Test
    @DisplayName("Deve buscar vacinas por período de aplicação com sucesso")
    void buscarPorPeriodoAplicacao() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 31);
        when(vacinaRepository.findByDataAplicacaoBetween(inicio, fim)).thenReturn(List.of(vacina1));
        
        // Act
        List<VacinaDTO> resultado = vacinaService.buscarPorPeriodoAplicacao(inicio, fim);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("V8", resultado.get(0).getNome());
        assertEquals(LocalDate.of(2025, 1, 15), resultado.get(0).getDataAplicacao());
        verify(vacinaRepository, times(1)).findByDataAplicacaoBetween(inicio, fim);
    }
    
    @Test
    @DisplayName("Deve buscar vacinas vencidas com sucesso")
    void buscarVencidas() {
        // Arrange
        LocalDate dataReferencia = LocalDate.of(2026, 2, 15);
        when(vacinaRepository.findByDataValidadeBefore(dataReferencia)).thenReturn(Arrays.asList(vacina1, vacina2));
        
        // Act
        List<VacinaDTO> resultado = vacinaService.buscarVencidas(dataReferencia);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(vacinaRepository, times(1)).findByDataValidadeBefore(dataReferencia);
    }
    
    @Test
    @DisplayName("Deve buscar vacinas com próximas doses em um período com sucesso")
    void buscarProximasDoses() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 7, 1);
        LocalDate fim = LocalDate.of(2025, 7, 31);
        when(vacinaRepository.findByDataProximaDoseBetween(inicio, fim)).thenReturn(List.of(vacina1));
        
        // Act
        List<VacinaDTO> resultado = vacinaService.buscarProximasDoses(inicio, fim);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("V8", resultado.get(0).getNome());
        assertEquals(LocalDate.of(2025, 7, 15), resultado.get(0).getDataProximaDose());
        verify(vacinaRepository, times(1)).findByDataProximaDoseBetween(inicio, fim);
    }
    
    @Test
    @DisplayName("Deve salvar uma vacina com sucesso")
    void salvar() {
        // Arrange
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(vacinaRepository.save(any(Vacina.class))).thenAnswer(invocation -> {
            Vacina v = invocation.getArgument(0);
            v.setId(1L);
            return v;
        });
        
        VacinaDTO novaVacina = new VacinaDTO();
        novaVacina.setNome("V10");
        novaVacina.setDataAplicacao(LocalDate.now());
        novaVacina.setLote("L999999");
        novaVacina.setDataValidade(LocalDate.now().plusYears(1));
        novaVacina.setAnimalId(1L);
        
        // Act
        VacinaDTO resultado = vacinaService.salvar(novaVacina);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("V10", resultado.getNome());
        assertEquals("L999999", resultado.getLote());
        verify(animalRepository, times(1)).findById(1L);
        verify(vacinaRepository, times(1)).save(any(Vacina.class));
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao salvar vacina com animal inexistente")
    void salvarVacinaAnimalInexistente() {
        // Arrange
        when(animalRepository.findById(99L)).thenReturn(Optional.empty());
        vacinaDTO.setAnimalId(99L);
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            vacinaService.salvar(vacinaDTO);
        });
        verify(animalRepository, times(1)).findById(99L);
        verify(vacinaRepository, never()).save(any(Vacina.class));
    }
    
    @Test
    @DisplayName("Deve atualizar uma vacina com sucesso")
    void atualizar() {
        // Arrange
        when(vacinaRepository.findById(1L)).thenReturn(Optional.of(vacina1));
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(vacinaRepository.save(any(Vacina.class))).thenReturn(vacina1);
        
        vacinaDTO.setNome("V8 Atualizada");
        vacinaDTO.setLote("L123456-A");
        
        // Act
        VacinaDTO resultado = vacinaService.atualizar(1L, vacinaDTO);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("V8 Atualizada", resultado.getNome());
        assertEquals("L123456-A", resultado.getLote());
        verify(vacinaRepository, times(1)).findById(1L);
        verify(vacinaRepository, times(1)).save(any(Vacina.class));
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao atualizar vacina inexistente")
    void atualizarVacinaInexistente() {
        // Arrange
        when(vacinaRepository.findById(99L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            vacinaService.atualizar(99L, vacinaDTO);
        });
        verify(vacinaRepository, times(1)).findById(99L);
        verify(vacinaRepository, never()).save(any(Vacina.class));
    }
    
    @Test
    @DisplayName("Deve remover uma vacina com sucesso")
    void remover() {
        // Arrange
        when(vacinaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(vacinaRepository).deleteById(1L);
        
        // Act
        vacinaService.remover(1L);
        
        // Assert
        verify(vacinaRepository, times(1)).existsById(1L);
        verify(vacinaRepository, times(1)).deleteById(1L);
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao remover vacina inexistente")
    void removerVacinaInexistente() {
        // Arrange
        when(vacinaRepository.existsById(99L)).thenReturn(false);
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            vacinaService.remover(99L);
        });
        verify(vacinaRepository, times(1)).existsById(99L);
        verify(vacinaRepository, never()).deleteById(anyLong());
    }
    
    @ParameterizedTest
    @CsvSource({
        "2025-01-01, 2025-01-31, 1",
        "2025-02-01, 2025-02-28, 1",
        "2025-03-01, 2025-03-31, 0"
    })
    @DisplayName("Deve buscar vacinas aplicadas em diferentes períodos")
    void buscarPorPeriodoAplicacaoParametrizado(String inicioStr, String fimStr, int quantidadeEsperada) {
        // Arrange
        LocalDate inicio = LocalDate.parse(inicioStr);
        LocalDate fim = LocalDate.parse(fimStr);
        
        List<Vacina> vacinasEncontradas = List.of();
        if (quantidadeEsperada == 1) {
            if (inicio.getMonthValue() == 1) {
                vacinasEncontradas = List.of(vacina1);
            } else if (inicio.getMonthValue() == 2) {
                vacinasEncontradas = List.of(vacina2);
            }
        }
        
        when(vacinaRepository.findByDataAplicacaoBetween(inicio, fim)).thenReturn(vacinasEncontradas);
        
        // Act
        List<VacinaDTO> resultado = vacinaService.buscarPorPeriodoAplicacao(inicio, fim);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(quantidadeEsperada, resultado.size());
        verify(vacinaRepository, times(1)).findByDataAplicacaoBetween(inicio, fim);
    }
}
