package modelos;

import java.util.ArrayList;

public class Animal {
	private int id;
	private String nome;
	private double peso;
	private String cor;
	private boolean possueDoenca;
	private String tipoAnimal;
	private ArrayList<Vacina> vacinas;

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public boolean isPossueDoenca() {
		return possueDoenca;
	}

	public void setPossueDoenca(boolean possueDoenca) {
		this.possueDoenca = possueDoenca;
	}
	

	public String getTipoAnimal() {
		return tipoAnimal;
	}

	public void setTipoAnimal(String tipoAnimal) {
		this.tipoAnimal = tipoAnimal;
	}

	public ArrayList<Vacina> getVacinas() {
		return vacinas;
	}

	public void setVacinas(ArrayList<Vacina> vacinas) {
		this.vacinas = vacinas;
	}
	
	public Animal(int id, String nome, double peso, String cor, boolean possueDoenca, String tipoAnimal, ArrayList<Vacina> vacinas) {
		this.id = id;
		this.nome = nome;
		this.peso = peso;
		this.cor = cor;
		this.possueDoenca = possueDoenca;
		this.tipoAnimal = tipoAnimal; 
		this.vacinas = vacinas;
		
	}
	public Animal(String nome) {
		
		this.nome = nome;
		
		
	}
	

	
	@Override
	public String toString() {
		
		
		return nome;
	}

	
	
	
	
	
}
