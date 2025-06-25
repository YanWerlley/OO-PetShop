package br.com.petshop.application.services;

import br.com.petshop.application.dto.MedicamentoDTO;

import java.time.LocalDate;
import java.util.List;

public interface MedicamentoService {
    List<MedicamentoDTO> buscarTodos();
    MedicamentoDTO buscarPorId(Long id);
    List<MedicamentoDTO> buscarPorAnimal(Long animalId);
    List<MedicamentoDTO> buscarPorConsulta(Long consultaId);
    List<MedicamentoDTO> buscarPorNome(String nome);
    List<MedicamentoDTO> buscarPorPeriodoInicio(LocalDate inicio, LocalDate fim);
    List<MedicamentoDTO> buscarPorPeriodoFim(LocalDate inicio, LocalDate fim);
    MedicamentoDTO salvar(MedicamentoDTO medicamentoDTO);
    MedicamentoDTO atualizar(Long id, MedicamentoDTO medicamentoDTO);
    void remover(Long id);
}
