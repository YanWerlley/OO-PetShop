package br.com.petshop.application.services;

import br.com.petshop.application.dto.ExameDTO;

import java.time.LocalDate;
import java.util.List;

public interface ExameService {
    List<ExameDTO> buscarTodos();
    ExameDTO buscarPorId(Long id);
    List<ExameDTO> buscarPorAnimal(Long animalId);
    List<ExameDTO> buscarPorConsulta(Long consultaId);
    List<ExameDTO> buscarPorTipo(String tipo);
    List<ExameDTO> buscarPorPeriodo(LocalDate inicio, LocalDate fim);
    List<ExameDTO> buscarPorNome(String nome);
    ExameDTO salvar(ExameDTO exameDTO);
    ExameDTO atualizar(Long id, ExameDTO exameDTO);
    void remover(Long id);
}
