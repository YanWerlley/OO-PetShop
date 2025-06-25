package br.com.petshop.application.services;

import br.com.petshop.application.dto.VacinaDTO;

import java.time.LocalDate;
import java.util.List;

public interface VacinaService {
    List<VacinaDTO> buscarTodas();
    VacinaDTO buscarPorId(Long id);
    List<VacinaDTO> buscarPorAnimal(Long animalId);
    List<VacinaDTO> buscarPorNome(String nome);
    List<VacinaDTO> buscarPorPeriodoAplicacao(LocalDate inicio, LocalDate fim);
    List<VacinaDTO> buscarVencidas(LocalDate dataReferencia);
    List<VacinaDTO> buscarProximasDoses(LocalDate inicio, LocalDate fim);
    VacinaDTO salvar(VacinaDTO vacinaDTO);
    VacinaDTO atualizar(Long id, VacinaDTO vacinaDTO);
    void remover(Long id);
}
