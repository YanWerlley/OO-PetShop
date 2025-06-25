package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.ClienteDTO;
import br.com.petshop.application.dto.EnderecoDTO;
import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Cachorro;
import br.com.petshop.domain.entities.Cliente;
import br.com.petshop.domain.entities.Endereco;
import br.com.petshop.domain.repositories.AnimalRepository;
import br.com.petshop.domain.repositories.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para ClienteServiceImpl
 * 
 * @author Yan Werlley
 */
@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;
    
    @Mock
    private AnimalRepository animalRepository;
    
    @InjectMocks
    private ClienteServiceImpl clienteService;
    
    private Cliente cliente;
    private Endereco endereco;
    private Animal animal;
    private ClienteDTO clienteDTO;
    private EnderecoDTO enderecoDTO;
    
    @BeforeEach
    void setUp() {
        // Configuração do endereço
        endereco = Endereco.builder()
                .id(1L)
                .rua("Rua das Flores")
                .numero("123")
                .complemento("Apto 101")
                .bairro("Centro")
                .cidade("São Paulo")
                .estado("SP")
                .cep("01234-567")
                .build();
        
        // Configuração do cliente
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-01");
        cliente.setTelefone("(11) 98765-4321");
        cliente.setDataNascimento(LocalDate.of(1980, 5, 15));
        cliente.setEmail("joao.silva@email.com");
        cliente.setEndereco(endereco);
        cliente.setAnimais(new ArrayList<>());
        
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
        
        // Adiciona o animal ao cliente
        cliente.getAnimais().add(animal);
        
        // Configuração do DTO de endereço
        enderecoDTO = EnderecoDTO.builder()
                .id(1L)
                .rua("Rua das Flores")
                .numero("123")
                .complemento("Apto 101")
                .bairro("Centro")
                .cidade("São Paulo")
                .estado("SP")
                .cep("01234-567")
                .build();
        
        // Configuração do DTO de cliente
        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("João Silva");
        clienteDTO.setCpf("123.456.789-01");
        clienteDTO.setTelefone("(11) 98765-4321");
        clienteDTO.setDataNascimento(LocalDate.of(1980, 5, 15));
        clienteDTO.setEmail("joao.silva@email.com");
        clienteDTO.setEndereco(enderecoDTO);
        clienteDTO.setAnimaisIds(List.of(1L));
    }
    
    @Test
    @DisplayName("Deve buscar todos os clientes com sucesso")
    void buscarTodos() {
        // Arrange
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        
        // Act
        List<ClienteDTO> resultado = clienteService.buscarTodos();
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
        verify(clienteRepository, times(1)).findAll();
    }
    
    @Test
    @DisplayName("Deve buscar um cliente por ID com sucesso")
    void buscarPorId() {
        // Arrange
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        
        // Act
        ClienteDTO resultado = clienteService.buscarPorId(1L);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("123.456.789-01", resultado.getCpf());
        assertEquals("joao.silva@email.com", resultado.getEmail());
        assertNotNull(resultado.getEndereco());
        assertEquals("Rua das Flores", resultado.getEndereco().getRua());
        assertEquals(1, resultado.getAnimaisIds().size());
        verify(clienteRepository, times(1)).findById(1L);
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao buscar cliente com ID inexistente")
    void buscarPorIdInexistente() {
        // Arrange
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            clienteService.buscarPorId(99L);
        });
        verify(clienteRepository, times(1)).findById(99L);
    }
    
    @Test
    @DisplayName("Deve buscar clientes por nome com sucesso")
    void buscarPorNome() {
        // Arrange
        when(clienteRepository.findByNomeContainingIgnoreCase("João")).thenReturn(List.of(cliente));
        
        // Act
        List<ClienteDTO> resultado = clienteService.buscarPorNome("João");
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
        verify(clienteRepository, times(1)).findByNomeContainingIgnoreCase("João");
    }
    
    @Test
    @DisplayName("Deve buscar cliente por CPF com sucesso")
    void buscarPorCpf() {
        // Arrange
        when(clienteRepository.findByCpf("123.456.789-01")).thenReturn(Optional.of(cliente));
        
        // Act
        ClienteDTO resultado = clienteService.buscarPorCpf("123.456.789-01");
        
        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("123.456.789-01", resultado.getCpf());
        verify(clienteRepository, times(1)).findByCpf("123.456.789-01");
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao buscar cliente com CPF inexistente")
    void buscarPorCpfInexistente() {
        // Arrange
        when(clienteRepository.findByCpf("999.999.999-99")).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            clienteService.buscarPorCpf("999.999.999-99");
        });
        verify(clienteRepository, times(1)).findByCpf("999.999.999-99");
    }
    
    @Test
    @DisplayName("Deve buscar cliente por email com sucesso")
    void buscarPorEmail() {
        // Arrange
        when(clienteRepository.findByEmail("joao.silva@email.com")).thenReturn(Optional.of(cliente));
        
        // Act
        ClienteDTO resultado = clienteService.buscarPorEmail("joao.silva@email.com");
        
        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao.silva@email.com", resultado.getEmail());
        verify(clienteRepository, times(1)).findByEmail("joao.silva@email.com");
    }
    
    @Test
    @DisplayName("Deve buscar cliente por telefone com sucesso")
    void buscarPorTelefone() {
        // Arrange
        when(clienteRepository.findByTelefone("(11) 98765-4321")).thenReturn(Optional.of(cliente));
        
        // Act
        ClienteDTO resultado = clienteService.buscarPorTelefone("(11) 98765-4321");
        
        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("(11) 98765-4321", resultado.getTelefone());
        verify(clienteRepository, times(1)).findByTelefone("(11) 98765-4321");
    }
    
    @Test
    @DisplayName("Deve salvar um cliente com sucesso")
    void salvar() {
        // Arrange
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });
        
        ClienteDTO novoCliente = new ClienteDTO();
        novoCliente.setNome("Maria Oliveira");
        novoCliente.setCpf("987.654.321-09");
        novoCliente.setTelefone("(21) 91234-5678");
        novoCliente.setDataNascimento(LocalDate.of(1992, 10, 20));
        novoCliente.setEmail("maria.oliveira@email.com");
        novoCliente.setEndereco(enderecoDTO);
        
        // Act
        ClienteDTO resultado = clienteService.salvar(novoCliente);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Maria Oliveira", resultado.getNome());
        assertEquals("987.654.321-09", resultado.getCpf());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }
    
    @Test
    @DisplayName("Deve atualizar um cliente com sucesso")
    void atualizar() {
        // Arrange
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        
        clienteDTO.setNome("João Silva Atualizado");
        clienteDTO.setTelefone("(11) 99999-8888");
        clienteDTO.getEndereco().setCidade("Campinas");
        
        // Act
        ClienteDTO resultado = clienteService.atualizar(1L, clienteDTO);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva Atualizado", resultado.getNome());
        assertEquals("(11) 99999-8888", resultado.getTelefone());
        assertEquals("Campinas", resultado.getEndereco().getCidade());
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao atualizar cliente inexistente")
    void atualizarClienteInexistente() {
        // Arrange
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            clienteService.atualizar(99L, clienteDTO);
        });
        verify(clienteRepository, times(1)).findById(99L);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }
    
    @Test
    @DisplayName("Deve remover um cliente com sucesso")
    void remover() {
        // Arrange
        when(clienteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(1L);
        
        // Act
        clienteService.remover(1L);
        
        // Assert
        verify(clienteRepository, times(1)).existsById(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao remover cliente inexistente")
    void removerClienteInexistente() {
        // Arrange
        when(clienteRepository.existsById(99L)).thenReturn(false);
        
        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            clienteService.remover(99L);
        });
        verify(clienteRepository, times(1)).existsById(99L);
        verify(clienteRepository, never()).deleteById(anyLong());
    }
    
    @Test
    @DisplayName("Deve adicionar um animal ao cliente com sucesso")
    void adicionarAnimal() {
        // Arrange
        Animal novoAnimal = new Cachorro();
        novoAnimal.setId(2L);
        novoAnimal.setNome("Thor");
        
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(animalRepository.findById(2L)).thenReturn(Optional.of(novoAnimal));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        
        // Act
        ClienteDTO resultado = clienteService.adicionarAnimal(1L, 2L);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getAnimaisIds().size());
        assertTrue(resultado.getAnimaisIds().contains(2L));
        verify(clienteRepository, times(1)).findById(1L);
        verify(animalRepository, times(1)).findById(2L);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }
    
    @Test
    @DisplayName("Deve remover um animal do cliente com sucesso")
    void removerAnimal() {
        // Arrange
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente c = invocation.getArgument(0);
            c.getAnimais().remove(animal);
            return c;
        });
        
        // Act
        ClienteDTO resultado = clienteService.removerAnimal(1L, 1L);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.getAnimaisIds().size());
        verify(clienteRepository, times(1)).findById(1L);
        verify(animalRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }
}
