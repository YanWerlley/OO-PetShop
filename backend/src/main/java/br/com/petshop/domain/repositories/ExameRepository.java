package br.com.petshop.domain.repositories;

import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Consulta;
import br.com.petshop.domain.entities.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositório para operações de persistência de Exames
 * 
 * @author Yan Werlley
 */
@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {
    List<Exame> findByAnimal(Animal animal);
    List<Exame> findByConsulta(Consulta consulta);
    List<Exame> findByTipo(String tipo);
    List<Exame> findByDataRealizacaoBetween(LocalDate inicio, LocalDate fim);
    List<Exame> findByNomeContainingIgnoreCase(String nome);
    
    /**
     * Conta exames por tipo
     */
    Long countByTipo(String tipo);
    
    /**
     * Busca exames por ID do animal
     */
    List<Exame> findByAnimalId(Long animalId);
    
    /**
     * Busca exames por período e tipo
     */
    List<Exame> findByDataRealizacaoBetweenAndTipo(LocalDate inicio, LocalDate fim, String tipo);
}
