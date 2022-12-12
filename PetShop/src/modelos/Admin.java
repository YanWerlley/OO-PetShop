package modelos;

import java.util.ArrayList;

public class Admin extends Pessoa {

	private String nomeBuscar;
	private ArrayList<Animal> animais;

	public Admin(String nome, String cpf, Endereco end) {
		super(nome, cpf, end);
	}

//	public ArrayList<Animal> BuscarAnimal(String nomeBuscar) {
//		
//		ArrayList<Animal> buscador = new ArrayList<Animal>();
//        animais.forEach((animal) -> {
//            if(animal.getNome().equals(nomeBuscar)) {
//            	System.out.println("Sim");
//            }else {
//            	System.out.println("Não");
//            }
//               
//            	
//        });
//        return buscador;
//		
//		
//		
//		
//	}

}
