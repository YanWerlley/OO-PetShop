package modelos;

import java.util.ArrayList;

public class Main {
	
	static Cliente c1;
	static Vacina v1;

	public static void main(String[] args) {

		String[] nomeVacina = { "Gripe Canina", "Raiva" };
		int[] quantidadeVacinaTot = { 100, 200 };
		double[] dosagem = { 10, 6 };
		int[] quantUsada = { 5, 0};

		ArrayList<Vacina> vacinasTot = new ArrayList<Vacina>();
		ArrayList<Vacina> vacinasUsadas = new ArrayList<Vacina>();
		ArrayList<Animal> animais = new ArrayList<Animal>();

		Animal a1 = new Animal(111, "Yummi", 20, "Branca", false, "Cachorro", vacinasUsadas);
		Animal a2 = new Animal(222, "Nasus", 40, "Preto", true, "Cavalo", vacinasUsadas);
	
		animais.add(a1);
		animais.add(a2);
		
	
		
		
		//Adicionando todas as vacinas
		for (int i = 0; i < 2; i++) {
			Vacina vac = new Vacina(nomeVacina[i], quantidadeVacinaTot[i], dosagem[i], quantUsada[i]);
			vacinasTot.add(vac);

		}
		
		//Adicionando vacinas aplicadas
		for (int i = 1; i < 3; i++) {
			Vacina vac = new Vacina(nomeVacina[i-1], quantidadeVacinaTot[i-1], dosagem[i-1], quantUsada[i-1]);
		
			if (quantUsada[i-1] >0) {
				 vac.setQuantidadeVacinaTot(quantUsada[i-1]);
				 vacinasUsadas.add(vac);
			}
		}	
		
	
		//endereço do usario
		Endereco end1 = new Endereco("Rua 1", "Luziania", "Goias", 1111);
		
		//add cliente
		Cliente cliente1 = new Cliente("Ana", end1, a1.getNome(), "030666666-50", 1);
		
		//add Admin
		Admin adm = new Admin("Yan", "01001010", end1);
		

		
		
		
		System.out.println("Todas as vacinas disponiveis: \n" + vacinasTot.toString());
		System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
		System.out.println(cliente1.toString());
		//Listagem de vacinas
		System.out.println(vacinasUsadas);
		//Listagem de animais
		System.out.println("\nListas de animais: "+animais.toString());
		
		

	}

}
