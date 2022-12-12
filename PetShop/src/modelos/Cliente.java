package modelos;

import java.util.ArrayList;

public class Cliente extends Pessoa {
	
	private int idCliente;
	private String nomepet;
	ArrayList<Animal> animais;
	
	public String getNomepet() {
		return nomepet;
	}
	public ArrayList<Animal> getAnimais() {
		return animais;
	}
	public void setAnimais(ArrayList<Animal> animais) {
		this.animais = animais;
	}
	public void setNomepet(String nomepet) {
		this.nomepet = nomepet;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	
	
	
	
	public Cliente(String nome, Endereco end, String nomepet, String cpf, int idCliente) {
		super(nome, cpf, end);
		this.idCliente = idCliente;
		this.nomepet = nomepet;
	}
	public void addAnimal(Animal animal) {
		animais.add(animal);

	}
	
	
	
//	public void listarAnimais() {
//		 getAnimais();
//	}
	
	@Override
	public String toString() {
		return "O animal " + nomepet + " tomou as seguintes vacinas:\n";
	}

}
