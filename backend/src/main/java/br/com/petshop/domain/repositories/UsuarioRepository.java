package br.com.petshop.domain.repositories;

import br.com.petshop.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações de persistência de Usuários
 * 
 * @author Yan Werlley
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    /**
     * Busca usuários por perfil
     */
    @Query("SELECT u FROM Usuario u JOIN u.perfil p WHERE p.nome = :nomePerfil")
    List<Usuario> findByPerfilNome(@Param("nomePerfil") String nomePerfil);
    
    /**
     * Conta usuários por perfil
     */
    @Query("SELECT COUNT(u) FROM Usuario u JOIN u.perfil p WHERE p.nome = :nomePerfil")
    Long countByPerfilNome(@Param("nomePerfil") String nomePerfil);
}
