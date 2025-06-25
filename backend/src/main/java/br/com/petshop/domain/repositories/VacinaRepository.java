package br.com.petshop.domain.repositories;

import br.com.petshop.domain.entities.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositório para operações de persistência de Vacinas
 * 
 * @author Yan Werlley
 */
@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long> {
    /**
     * Busca vacinas por ID do animal
     */
    List<Vacina> findByAnimalId(Long animalId);
    
    /**
     * Busca vacinas por período de aplicação
     */
    List<Vacina> findByDataAplicacaoBetween(LocalDate inicio, LocalDate fim);
    
    /**
     * Busca vacinas por nome
     */
    List<Vacina> findByNomeContainingIgnoreCase(String nome);
    
    /**
     * Busca vacinas por fabricante
     */
    List<Vacina> findByFabricanteContainingIgnoreCase(String fabricante);
    
    /**
     * Conta vacinas por fabricante
     */
    Long countByFabricante(String fabricante);
}
