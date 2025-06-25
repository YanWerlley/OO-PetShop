package br.com.petshop.domain.repositories;

import br.com.petshop.domain.entities.Animal;
import br.com.petshop.domain.entities.Consulta;
import br.com.petshop.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para operações de persistência de Consultas
 * 
 * @author Yan Werlley
 */
@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByAnimal(Animal animal);
    List<Consulta> findByVeterinario(Usuario veterinario);
    List<Consulta> findByStatus(Consulta.StatusConsulta status);
    List<Consulta> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Consulta> findByAnimalAndStatus(Animal animal, Consulta.StatusConsulta status);
    List<Consulta> findByVeterinarioAndStatus(Usuario veterinario, Consulta.StatusConsulta status);
    
    /**
     * Busca consultas por período de data
     */
    @Query("SELECT c FROM Consulta c WHERE DATE(c.dataHora) BETWEEN :dataInicio AND :dataFim")
    List<Consulta> findByDataBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Conta consultas por status
     */
    Long countByStatus(Consulta.StatusConsulta status);
    
    /**
     * Conta consultas por tipo de animal
     */
    @Query("SELECT COUNT(c) FROM Consulta c WHERE c.animal.discriminator = :tipo")
    Long countByAnimalTipo(@Param("tipo") String tipo);
    
    /**
     * Conta consultas por tipos de animal diferentes dos especificados
     */
    @Query("SELECT COUNT(c) FROM Consulta c WHERE c.animal.discriminator NOT IN (:tipo1, :tipo2)")
    Long countByAnimalTipoNot(@Param("tipo1") String tipo1, @Param("tipo2") String tipo2);
}
