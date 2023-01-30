package CRUDS;

import java.time.LocalDate;


import java.util.ArrayList;
import java.util.List;

import modelos.Animal;
import modelos.Vacina;

/**  
 * 
 * <p>A classe ControleAnimal serve para adicionar, atualizar e deletar Animais.</p>  
 * @author Yan Werlley
 * 
 */
public class ControleAnimal {
	private List<Animal> animais;

	public ControleAnimal() {
		this.animais = new ArrayList<>();
	}
	
	/**
	 * Método que cadastra um objeto Animal".
	 * @param Animal que será cadastrado
	 * 
	 */
	public void adicionarAnimal(Animal animal) {
		this.animais.add(animal);
	}
	
	/**
	 * Método que deleta um objeto Animal.
	 * @param Animal que será deletado
	 */
	public void removerAnimals(Animal animal) {
		if (!this.animais.contains(animal)) {
			throw new IllegalArgumentException("O animal não existe!");
		}
		this.animais.remove(animal);
	}
	
	/**
	 * Método que faz uma busca por Animal pelo seu nome.
	 * @param Animal que será buscado
	 */
	public Animal buscarAnimal(String nome) {
		for (Animal animal : this.animais) {
			if (animal.getNome().equals(nome)) {
				return animal;
			}
		}
		return null;
	}
	
	/**
	 * Método que atualiza os dados do Animal.
	 * @param Animal que será atualizado
	 */
	public void atualizarAnimals(Animal animal, String nome, double peso, String cor, boolean possueDoenca,
			String tipoAnimal) {
		animal.setNome(nome);
		animal.setPeso(peso);
		animal.setCor(cor);
		animal.setPossueDoenca(possueDoenca);
		animal.setTipoAnimal(tipoAnimal);
	}
	
	/**
	 * Método que lista todas as vacinas recebidas.
	 * @param Vacina que será listado
	 */
	public List<Vacina> listarVacinasRecebidas(String nomeAnimals) {
		Animal animal = buscarAnimal(nomeAnimals);
		if (animal != null) {
			return animal.getVacina();
		}
		return null;
	}

	public List<Animal> getListaAnimais() {
		return animais;
	}
	
	
	/**
	 * Método que remove uma vacina de um Animal.
	 * @param Vacina que será removida
	 */
	public void removerVacina(String nomeAnimal, String nomeVacina) {
		Animal animal = buscarAnimal(nomeAnimal);
		if (animal == null) {
			throw new IllegalArgumentException("O animal não existe!");
		}
		List<Vacina> vacinas = animal.getVacina();
		for (Vacina vacina : vacinas) {
			if (vacina.getNome().equals(nomeVacina)) {
				vacinas.remove(vacina);
				return;
			}
		}
		throw new IllegalArgumentException("A vacina não foi encontrada para este animal!");
	}
	
	
	/**
	 * Método que mostrar todos os Animais.
	 * @param Animal que será mostrado
	 */
	public String listarAnimais() {
		String lista = "";
		for (Animal animal : animais) {
			lista += "Nome: " + animal.getNome() + "\nPeso: " + animal.getPeso() + "\nCor: " + animal.getCor()
					+ "\nPossue Doença: " + animal.getPossueDoenca() + "\nTipo: " + animal.getTipoAnimals() + "\n\n";
		}
		return lista;
	}

	public List<Animal> listaAnimais() {
		return animais;
	}

}