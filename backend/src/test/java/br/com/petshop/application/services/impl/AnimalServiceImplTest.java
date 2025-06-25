package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.AnimalDTO;
import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Cachorro;
import br.com.petshop.domain.entities.Cliente;
import br.com.petshop.domain.entities.Gato;
import br.com.petshop.domain.repositories.AnimalRepository;
import br.com.petshop.domain.repositories.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
 * Testes unitários para AnimalServiceImpl
 * 
 * @author Yan Werlley
 */
@ExtendWith(MockitoExtension.class)
class AnimalServiceImplTest {

    @Mock
    private AnimalRepository animalRepository;
    
    @Mock
    private ClienteRepository clienteRepository;
    
    @InjectMocks
    private AnimalServiceImpl animalService;
    
    private Cliente cliente;
    private Cachorro cachorro;
    private Gato gato;
    private AnimalDTO cachorroDTO;
    private AnimalDTO gatoDTO;
    
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
        
        // Configuração do cachorro
        cachorro = new Cachorro();
        cachorro.setId(1L);
        cachorro.setNome("Rex");
        cachorro.setPeso(15.5);
        cachorro.setCor("Marrom");
        cachorro.setPossueDoenca(false);
        cachorro.setProprietario(cliente);
        cachorro.setRaca("Labrador");
        cachorro.setAdestrado(true);
        
        // Configuração do gato
        gato = new Gato();
        gato.setId(2L);
        gato.setNome("Luna");
        gato.setPeso(4.3);
        gato.setCor("Branco");
        gato.setPossueDoenca(false);
        gato.setProprietario(cliente);
        gato.setRaca("Siamês");
        gato.setCastrado(true);
        
        // Configuração do DTO de cachorro
        cachorroDTO = new AnimalDTO();
        cachorroDTO.setId(1L);
        cachorroDTO.setNome("Rex");
        cachorroDTO.setPeso(15.5);
        cachorroDTO.setCor("Marrom");
        cachorroDTO.setPossueDoenca(false);
        cachorroDTO.setTipo("CACHORRO");
        cachorroDTO.setProprietarioId(1L);
        cachorroDTO.setRaca("Labrador");
        cachorroDTO.setAdestrado(true);
        
        // Configuração do DTO de gato
        gatoDTO = new AnimalDTO();
        gatoDTO.setId(2L);
        gatoDTO.setNome("Luna");
        gatoDTO.setPeso(4.3);
        gatoDTO.setCor("Branco");
        gatoDTO.setPossueDoenca(false);
        gatoDTO.setTipo("GATO");
        gatoDTO.setProprietarioId(1L);
        gatoDTO.setRaca("Siamês");
        gatoDTO.setCastrado(true);
    }
    
    @Test
    @DisplayName("Deve buscar todos os animais com sucesso")
    void buscarTodos() {
        // Arrange
        when(animalRepository.findAll()).thenReturn(Arrays.asList(cachorro, gato));
        
        // Act
        List<AnimalDTO> resultado = animalService.buscarTodos();
        
        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Rex", resultado.get(0).getNome());
        assertEquals("Luna", resultado.get(1).getNome());
        verify(animalRepository, times(1)).findAll();
    }
    
    @Test
    @DisplayName("Deve buscar um animal por ID com sucesso")
    void buscarPorId() {
        // Arrange
        when(animalRepository.findById(1L)).thenReturn(Optional.of(cachorro));
        
        // Act
        AnimalDTO resultado = animalService.buscarPorId(1L);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("Rex", resultado.getNome());
        assertEquals("CACHORRO", resultado.getTipo());
        assertTrue(resultado.getAdestrado());
        verify(animalRepository, times(1)).findById(1L);
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao buscar animal com ID inexistente")
    void buscarPorIdInexistente() {
        // Arrange
        when(animalRepository.findById(99L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            animalService.buscarPorId(99L);
        });
        verify(animalRepository, times(1)).findById(99L);
    }
    
    @Test
    @DisplayName("Deve buscar animais por nome com sucesso")
    void buscarPorNome() {
        // Arrange
        when(animalRepository.findByNomeContainingIgnoreCase("Rex")).thenReturn(List.of(cachorro));
        
        // Act
        List<AnimalDTO> resultado = animalService.buscarPorNome("Rex");
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Rex", resultado.get(0).getNome());
        verify(animalRepository, times(1)).findByNomeContainingIgnoreCase("Rex");
    }
    
    @Test
    @DisplayName("Deve buscar animais com doença com sucesso")
    void buscarComDoenca() {
        // Arrange
        cachorro.setPossueDoenca(true);
        when(animalRepository.findByPossueDoencaTrue()).thenReturn(List.of(cachorro));
        
        // Act
        List<AnimalDTO> resultado = animalService.buscarComDoenca();
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).isPossueDoenca());
        verify(animalRepository, times(1)).findByPossueDoencaTrue();
    }
    
    @Test
    @DisplayName("Deve buscar animais por proprietário com sucesso")
    void buscarPorProprietario() {
        // Arrange
        when(animalRepository.findByProprietarioId(1L)).thenReturn(Arrays.asList(cachorro, gato));
        
        // Act
        List<AnimalDTO> resultado = animalService.buscarPorProprietario(1L);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getProprietarioId());
        assertEquals(1L, resultado.get(1).getProprietarioId());
        verify(animalRepository, times(1)).findByProprietarioId(1L);
    }
    
    @Test
    @DisplayName("Deve salvar um cachorro com sucesso")
    void salvarCachorro() {
        // Arrange
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(animalRepository.save(any(Cachorro.class))).thenAnswer(invocation -> {
            Cachorro c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });
        
        // Act
        AnimalDTO resultado = animalService.salvar(cachorroDTO);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Rex", resultado.getNome());
        assertEquals("CACHORRO", resultado.getTipo());
        assertTrue(resultado.getAdestrado());
        verify(clienteRepository, times(1)).findById(1L);
        verify(animalRepository, times(1)).save(any(Cachorro.class));
    }
    
    @Test
    @DisplayName("Deve salvar um gato com sucesso")
    void salvarGato() {
        // Arrange
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(animalRepository.save(any(Gato.class))).thenAnswer(invocation -> {
            Gato g = invocation.getArgument(0);
            g.setId(2L);
            return g;
        });
        
        // Act
        AnimalDTO resultado = animalService.salvar(gatoDTO);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        assertEquals("Luna", resultado.getNome());
        assertEquals("GATO", resultado.getTipo());
        assertTrue(resultado.getCastrado());
        verify(clienteRepository, times(1)).findById(1L);
        verify(animalRepository, times(1)).save(any(Gato.class));
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao salvar animal com proprietário inexistente")
    void salvarAnimalProprietarioInexistente() {
        // Arrange
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
        cachorroDTO.setProprietarioId(99L);
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            animalService.salvar(cachorroDTO);
        });
        verify(clienteRepository, times(1)).findById(99L);
        verify(animalRepository, never()).save(any(Animal.class));
    }
    
    @Test
    @DisplayName("Deve atualizar um cachorro com sucesso")
    void atualizarCachorro() {
        // Arrange
        when(animalRepository.findById(1L)).thenReturn(Optional.of(cachorro));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(animalRepository.save(any(Cachorro.class))).thenReturn(cachorro);
        
        cachorroDTO.setNome("Rex Atualizado");
        cachorroDTO.setCor("Preto");
        
        // Act
        AnimalDTO resultado = animalService.atualizar(1L, cachorroDTO);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("Rex Atualizado", resultado.getNome());
        assertEquals("Preto", resultado.getCor());
        verify(animalRepository, times(1)).findById(1L);
        verify(animalRepository, times(1)).save(any(Cachorro.class));
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao atualizar animal inexistente")
    void atualizarAnimalInexistente() {
        // Arrange
        when(animalRepository.findById(99L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            animalService.atualizar(99L, cachorroDTO);
        });
        verify(animalRepository, times(1)).findById(99L);
        verify(animalRepository, never()).save(any(Animal.class));
    }
    
    @Test
    @DisplayName("Deve remover um animal com sucesso")
    void remover() {
        // Arrange
        when(animalRepository.existsById(1L)).thenReturn(true);
        doNothing().when(animalRepository).deleteById(1L);
        
        // Act
        animalService.remover(1L);
        
        // Assert
        verify(animalRepository, times(1)).existsById(1L);
        verify(animalRepository, times(1)).deleteById(1L);
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao remover animal inexistente")
    void removerAnimalInexistente() {
        // Arrange
        when(animalRepository.existsById(99L)).thenReturn(false);
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            animalService.remover(99L);
        });
        verify(animalRepository, times(1)).existsById(99L);
        verify(animalRepository, never()).deleteById(anyLong());
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"CACHORRO", "GATO"})
    @DisplayName("Deve criar o tipo correto de animal baseado no tipo informado")
    void criarAnimalPeloTipo(String tipo) {
        // Arrange
        AnimalDTO dto = new AnimalDTO();
        dto.setTipo(tipo);
        dto.setNome("Animal Teste");
        dto.setPeso(10.0);
        dto.setCor("Marrom");
        dto.setProprietarioId(1L);
        
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        
        if ("CACHORRO".equals(tipo)) {
            dto.setRaca("Labrador");
            dto.setAdestrado(true);
            when(animalRepository.save(any(Cachorro.class))).thenAnswer(invocation -> {
                Cachorro c = invocation.getArgument(0);
                c.setId(10L);
                return c;
            });
        } else {
            dto.setRaca("Siamês");
            dto.setCastrado(true);
            when(animalRepository.save(any(Gato.class))).thenAnswer(invocation -> {
                Gato g = invocation.getArgument(0);
                g.setId(10L);
                return g;
            });
        }
        
        // Act
        AnimalDTO resultado = animalService.salvar(dto);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(tipo, resultado.getTipo());
        assertEquals("Animal Teste", resultado.getNome());
        
        if ("CACHORRO".equals(tipo)) {
            assertTrue(resultado.getAdestrado());
        } else {
            assertTrue(resultado.getCastrado());
        }
        
        verify(clienteRepository, times(1)).findById(1L);
        verify(animalRepository, times(1)).save(any(Animal.class));
    }
}
