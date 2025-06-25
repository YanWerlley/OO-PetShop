package br.com.petshop.application.services;

import br.com.petshop.application.dto.ClienteDTO;

import java.util.List;

public interface ClienteService {
    List<ClienteDTO> findAll();
    ClienteDTO findById(Long id);
    ClienteDTO findByCpf(String cpf);
    ClienteDTO findByEmail(String email);
    ClienteDTO save(ClienteDTO clienteDTO);
    ClienteDTO update(Long id, ClienteDTO clienteDTO);
    void delete(Long id);
}
