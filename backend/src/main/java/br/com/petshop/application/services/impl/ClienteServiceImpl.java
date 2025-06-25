package br.com.petshop.application.services.impl;

import br.com.petshop.application.dto.AnimalDTO;
import br.com.petshop.application.dto.ClienteDTO;
import br.com.petshop.application.dto.EnderecoDTO;
import br.com.petshop.application.services.ClienteService;
import br.com.petshop.domain.entities.Cliente;
import br.com.petshop.domain.entities.Endereco;
import br.com.petshop.domain.repositories.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO findById(Long id) {
        return clienteRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + id));
    }

    @Override
    public ClienteDTO findByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com CPF: " + cpf));
    }

    @Override
    public ClienteDTO findByEmail(String email) {
        return clienteRepository.findByEmail(email)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com Email: " + email));
    }

    @Override
    @Transactional
    public ClienteDTO save(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        
        if (clienteDTO.getEndereco() != null) {
            Endereco endereco = new Endereco();
            endereco.setLogradouro(clienteDTO.getEndereco().getLogradouro());
            endereco.setNumero(clienteDTO.getEndereco().getNumero());
            endereco.setComplemento(clienteDTO.getEndereco().getComplemento());
            endereco.setBairro(clienteDTO.getEndereco().getBairro());
            endereco.setCidade(clienteDTO.getEndereco().getCidade());
            endereco.setEstado(clienteDTO.getEndereco().getEstado());
            endereco.setCep(clienteDTO.getEndereco().getCep());
            cliente.setEndereco(endereco);
        }
        
        Cliente savedCliente = clienteRepository.save(cliente);
        return convertToDTO(savedCliente);
    }

    @Override
    @Transactional
    public ClienteDTO update(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + id));
        
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefone(clienteDTO.getTelefone());
        
        if (clienteDTO.getEndereco() != null) {
            Endereco endereco = cliente.getEndereco();
            if (endereco == null) {
                endereco = new Endereco();
                cliente.setEndereco(endereco);
            }
            endereco.setLogradouro(clienteDTO.getEndereco().getLogradouro());
            endereco.setNumero(clienteDTO.getEndereco().getNumero());
            endereco.setComplemento(clienteDTO.getEndereco().getComplemento());
            endereco.setBairro(clienteDTO.getEndereco().getBairro());
            endereco.setCidade(clienteDTO.getEndereco().getCidade());
            endereco.setEstado(clienteDTO.getEndereco().getEstado());
            endereco.setCep(clienteDTO.getEndereco().getCep());
        }
        
        Cliente updatedCliente = clienteRepository.save(cliente);
        return convertToDTO(updatedCliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
    
    private ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        
        if (cliente.getEndereco() != null) {
            EnderecoDTO enderecoDTO = new EnderecoDTO();
            enderecoDTO.setId(cliente.getEndereco().getId());
            enderecoDTO.setLogradouro(cliente.getEndereco().getLogradouro());
            enderecoDTO.setNumero(cliente.getEndereco().getNumero());
            enderecoDTO.setComplemento(cliente.getEndereco().getComplemento());
            enderecoDTO.setBairro(cliente.getEndereco().getBairro());
            enderecoDTO.setCidade(cliente.getEndereco().getCidade());
            enderecoDTO.setEstado(cliente.getEndereco().getEstado());
            enderecoDTO.setCep(cliente.getEndereco().getCep());
            dto.setEndereco(enderecoDTO);
        }
        
        return dto;
    }
}
