package br.com.petshop.domain.repositories;

import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Consulta;
import br.com.petshop.domain.entities.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    List<Medicamento> findByAnimal(Animal animal);
    List<Medicamento> findByConsulta(Consulta consulta);
    List<Medicamento> findByNomeContainingIgnoreCase(String nome);
    List<Medicamento> findByDataInicioBetween(LocalDate inicio, LocalDate fim);
    List<Medicamento> findByDataFimBetween(LocalDate inicio, LocalDate fim);
}
