package view;

import javax.swing.*;
import view.TelaAnimal;

import CRUDS.ControleAnimal;
import CRUDS.ControleVacina;
import modelos.Animal;
import modelos.Vacina;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

/**  
 * 
 * <p>A classe TelaInicial cria uma janela com 2 opções para usario escolher, Ver seus pets ou Vacinas.</p>  
 * @author Yan Werlley
 * 
 */
public class TelaInicial  {
	private static JFrame frame2 = new JFrame("PetShop");
	private static JButton mostrarPets = new JButton("Seus pets");
	private static JButton vacinasPetButton = new JButton("Ver vacinas");
	private static JLabel titulo = new JLabel("PetShop Menu");
	
	
	/**
	 * Método construtor da classe TelaInicial, responsável por inicializar e configurar os elementos da tela. 
	 * Utiliza o layout null para posicionamento dos elementos manualmente. 
	 * Define as configurações básicas da tela, como tamanho, localização e opção de redimensionamento.
	 * 
	 * 
	 * 
	 * 
	 * 
	 */	
	public TelaInicial(){
	    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    titulo.setFont(new java.awt.Font("arial", Font.BOLD,24));
	    
	    
	    titulo.setBounds(165, 20, 170, 60);
	    vacinasPetButton.setBounds(310, 130, 110, 30);
	    mostrarPets.setBounds(80, 130, 110, 30);
	    
	    frame2.setLayout(null);
	    frame2.setSize(516, 300);
	    frame2.setLocationRelativeTo(null);
	    frame2.setResizable(false);
	    
	    
	}
	/**
	 * Esse é o método main da classe TelaInicial, onde é criada a tela principal da aplicação, 
	 * instanciando os controles de vacinas e animais, adicionando vacinas e animais, e aplicando vacinas em animais.
	 * 
	 * 
	 * 
	 * 
	 * 
	 */	
	public static void main(String[] args) {
	    TelaInicial inicial = new TelaInicial();
	    ControleVacina controleVacina = new ControleVacina();
		ControleAnimal controleAnimal = new ControleAnimal();

		// Adicionando vacinas
		Vacina vacina1 = new Vacina("Febre", "2022-1-1");
		Vacina vacina2 = new Vacina("Raiva", "2022-2-1");
		controleVacina.adicionarVacina(vacina1);
		controleVacina.adicionarVacina(vacina2);

		// Adicionando animais
		Animal animal1 = new Animal("Barão", 10, "Marrom", false, "Cachorro");
		Animal animal2 = new Animal("Yumi", 20, "Preto", true, "Gato");
		controleAnimal.adicionarAnimal(animal1);
		controleAnimal.adicionarAnimal(animal2);

		// Aplicando vacinas em animais
		animal1.addVacina(vacina1);
		animal1.addVacina(vacina2);
		animal2.addVacina(vacina2);
		
		
		
		frame2.add(titulo);
	    frame2.add(mostrarPets);
	    frame2.add(vacinasPetButton);
	    
	    /**
		 * Mostra a tela de animais da aplicação, com a lista de animais cadastrados no sistema. 
		 * A partir desta tela, é possível visualizar, editar e remover os animais cadastrados.
		 * 
		 * 
		 * 
		 * 
		 * 
		 */	
	    mostrarPets.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            TelaAnimal telaAnimal = new TelaAnimal(controleAnimal);
	            telaAnimal.mostrarTela();;
	        }
	    });
	    /**
		 * Este código cria um listener para o botão "mostrarPets" que ao ser clicado, 
		 * instancia uma nova tela de animais (TelaAnimals) e a exibe. 
		 * Também é criado um listener para o botão "vacinasPetButton" que ao ser clicado, 
		 * instancia uma nova tela de vacinas (TelaVacina) e a exibe.
		 * 
		 * 
		 * 
		 * 
		 * 
		 */	
	    vacinasPetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaVacina telaVacina = new TelaVacina(controleAnimal);
				telaVacina.setVisible(true);
			}
		});
	    frame2.setVisible(true);
	    
	    
	}
}
