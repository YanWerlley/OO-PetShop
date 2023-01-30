package view;

import javax.swing.*;
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import CRUDS.ControleAnimal;
import modelos.Animal;

/**  
 * 
 * <p>A classe TelaCadastroAnimal cria uma janela para cadastro de Animal.</p>  
 * @author Yan Werlley
 * 
 */
public class TelaCadastroAnimal extends JFrame {
	private JFrame frame;
	private JTextField nomeField;
	private JTextField pesoField;
	private JTextField corField;
	private JCheckBox possueDoencaBox;
	private JTextField tipoField;
	private JButton cadastrarButton;
	private ControleAnimal controller;

	public TelaCadastroAnimal(ControleAnimal controller) {
		this.controller = controller;
		setTitle("Cadastrar Animal");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 300);
		setLayout(new FlowLayout());
		setLayout(new GridLayout(7, 2));

		nomeField = new JTextField("", 20);
		pesoField = new JTextField("", 20);
		corField = new JTextField("", 20);
		possueDoencaBox = new JCheckBox("Possue Doença", false);
		tipoField = new JTextField("", 20);
		cadastrarButton = new JButton("Cadastrar");
		
		/**
		 * ActionListener para o botão "cadastrar".
		 * Quando acionado, pega os valores dos campos de nome, peso, cor, possue doença e tipo,cria um objeto Animal 
		 * com esses valores e adiciona ao controller.
		 * Exibe uma mensagem de sucesso e fecha a tela de cadastro.
		 *  
		 * 
		 * 
		 * 
		 */
		cadastrarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nome = nomeField.getText();
				double peso = Double.parseDouble(pesoField.getText());
				String cor = corField.getText();
				boolean possueDoenca = possueDoencaBox.isSelected();
				String tipo = tipoField.getText();
				
				Animal animal = new Animal(nome, peso, cor, possueDoenca, tipo);

				controller.adicionarAnimal(animal);
				JOptionPane.showMessageDialog(TelaCadastroAnimal.this, "Animal cadastrado com sucesso!");
				TelaCadastroAnimal.this.dispose();
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
		add(cadastrarButton);
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