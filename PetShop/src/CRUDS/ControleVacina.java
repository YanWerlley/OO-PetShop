package CRUDS;

import java.util.ArrayList;
import java.util.List;

import modelos.Vacina;

/**  
 * 
 * <p>A classe ControleVacina serve para adicionar, atualizar e deletar Vacinas.</p>  
 * @author Yan Werlley
 * 
 */
public class ControleVacina {
    private List<Vacina> vacinas;
    
    /**
	 * Construtor inicializa uma lista vazia chamada vacinas".
	 * 
	 * 
	 */
    public ControleVacina() {
        this.vacinas = new ArrayList<>();
    }
    
    /**
	 * Método que cadastra um objeto Vacina".
	 * @param Vacina que será cadastrado
	 * 
	 */
    public void adicionarVacina(Vacina vacina) {
        this.vacinas.add(vacina);
    }
    
    /**
	 * Método que deleta um objeto Vacina.
	 * @param Vacina que será deletado
	 */
    public void removerVacina(Vacina vacina) {
        this.vacinas.remove(vacina);
    }
    
    /**
	 * Método que faz uma busca por Vacina.
	 * @param Vacina que será buscado
	 */
    public Vacina buscarVacina(String nome) {
        for (Vacina vacina : this.vacinas) {
            if (vacina.getNome().equals(nome)) {
                return vacina;
            }
        }
        return null;
    }

    public List<Vacina> listarVacinas() {
        return this.vacinas;
    }
}