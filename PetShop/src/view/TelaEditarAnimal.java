package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import CRUDS.ControleAnimal;
import modelos.Animal;

/**  
 * 
 * <p>A classe TelaEditarAnimal cria uma janela para edição de Animal.</p>  
 * @author Yan Werlley
 * 
 */
public class TelaEditarAnimal extends JFrame {
	private JFrame frame;
	private JTextField nomeField;
	private JTextField pesoField;
	private JTextField corField;
	private JCheckBox possueDoencaBox;
	private JTextField tipoField;
	private JButton atualizarButton;
	private JButton excluirButton;
	private ControleAnimal controller;
	private Animal animal;

	public TelaEditarAnimal(ControleAnimal controller, Animal animal) {
		this.controller = controller;
		this.animal = animal;
		setTitle("Editar Animal");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 300);
		setLayout(new FlowLayout());
		setLayout(new GridLayout(7, 2));

		nomeField = new JTextField(animal.getNome(), 20);
		pesoField = new JTextField(String.valueOf(animal.getPeso()), 20);
		corField = new JTextField(animal.getCor(), 20);
		possueDoencaBox = new JCheckBox("Possue Doença", animal.getPossueDoenca());
		tipoField = new JTextField(animal.getTipoAnimals(), 20);
		atualizarButton = new JButton("Atualizar");
		excluirButton = new JButton("Excluir");
		

		/**
		 * Método para atualizar os dados de um animal já cadastrado. 
		 * Ele obtém os valores dos campos de texto da tela e atualiza os atributos do objeto animal. 
		 * Em seguida, chama o método atualizarAnimals do controller para salvar as alterações no banco de dados. 
		 * Exibe uma mensagem de sucesso e fecha a tela de edição.
		 * 
		 * 
		 * 
		 * 
		 * 
		 */	
		atualizarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nome = nomeField.getText();
				double peso = Double.parseDouble(pesoField.getText());
				String cor = corField.getText();
				boolean possueDoenca = possueDoencaBox.isSelected();
				String tipo = tipoField.getText();

				animal.setNome(nome);
				animal.setPeso(peso);
				animal.setCor(cor);
				animal.setPossueDoenca(possueDoenca);
				animal.setTipoAnimal(tipo);

				controller.atualizarAnimals(animal, nome, peso, cor, possueDoenca, tipo);
				JOptionPane.showMessageDialog(TelaEditarAnimal.this, "Animal atualizado com sucesso!");
				TelaEditarAnimal.this.dispose();
			}
		});
		
		/**
		 * Adiciona um listener ao botão excluir para remover um animal. 
		 * Quando o botão é clicado, o animal é removido através do método removerAnimals do controller. 
		 * Uma mensagem de confirmação é exibida e a tela é fechada.
		 * 
		 * 
		 * 
		 * 
		 * 
		 */	
		excluirButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.removerAnimals(animal);
				JOptionPane.showMessageDialog(TelaEditarAnimal.this, "Animal removido com sucesso!");
				TelaEditarAnimal.this.dispose();
			}
		});
		add(new JLabel("Nome: "));
		add(nomeField);
		add(new JLabel("Peso: "));
		add(pesoField);
		add(new JLabel("Cor: "));
		add(corField);
		add(new JLabel("Possue Doença: "));
		add(possueDoencaBox);
		add(new JLabel("Tipo de Animal: "));
		add(tipoField);
		add(atualizarButton);
		add(excluirButton);
		setVisible(true);
	}


	/**
	 * Mostra a tela principal da aplicação.
	 * 
	 * 
	 * 
	 * 
	 * 
	 */	
	public void mostrar() {
		if (!frame.isVisible()) {
			frame.setVisible(false);
		}

	}
}