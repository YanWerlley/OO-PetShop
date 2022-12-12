package modelos;

import java.util.ArrayList;

public class Admin extends Pessoa {

	private String nomeBuscar;
	private ArrayList<Animal> animais;

	public Admin(String nome, String cpf, Endereco end) {
		super(nome, cpf, end);
	}



}
