package modelos;

import java.util.ArrayList;
import java.util.List;

/**  
 * 
 * <p>A classe Animal é usada para modelar a entidade animal</p>  
 * @author Yan Werlley
 * 
 */

public class Animal {
    private String nome;
    private double peso;
    private String cor;
    private boolean possueDoenca;
    private String tipoAnimal;
    private List<Vacina> vacinas;

    public Animal(String nome, double peso, String cor, boolean possueDoenca, String tipoAnimal) {
        this.nome = nome;
        this.peso = peso;
        this.cor = cor;
        this.possueDoenca = possueDoenca;
        this.tipoAnimal = tipoAnimal;
        this.vacinas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public double getPeso() {
        return peso;
    }

    public String getCor() {
        return cor;
    }

    public boolean getPossueDoenca() {
        return possueDoenca;
    }

    public String getTipoAnimals() {
        return tipoAnimal;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setPossueDoenca(boolean possueDoenca) {
        this.possueDoenca = possueDoenca;
    }

    public void setTipoAnimal(String tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    
    
    public String toString() {
        return "Nome: " + nome + ", Peso: " + peso + ", Cor: " + cor + ", Possue doenca: " + possueDoenca + ", Tipo de animal: " + tipoAnimal;
    }
    
    public void addVacina(Vacina vacina) {
        vacinas.add(vacina);
    }

	public List<Vacina> getVacina() {
		return vacinas;
	}
}