package view;

import javax.swing.*;

import CRUDS.ControleAnimal;
import modelos.Animal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**  
 * 
 * <p>A classe TelaAnimal cria uma janela que lista Animas e faz uma busca.</p>  
 * @author Yan Werlley
 * 
 */
public class TelaAnimal {
	private JFrame frame;
	private JTextField nomeAnimalsField;
	private JButton buscarButton;
	private JButton listarButton;
	private JButton editarButton;
	private JButton adicionarButton;
	private JTable resultadoTable;
	private ControleAnimal controller;

	public TelaAnimal(ControleAnimal controller) {
		this.controller = controller;
		frame = new JFrame("Busca Animal");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLayout(new FlowLayout());

		nomeAnimalsField = new JTextField(20);
		buscarButton = new JButton("Buscar");
		listarButton = new JButton("Listar");
		editarButton = new JButton("Editar");
		adicionarButton = new JButton("Adicionar Pet");

		String[] columnNames = {"Nome", "Peso", "Cor", "Possue Doença", "Tipo" };
		Object[][] data = {};
		resultadoTable = new JTable(data, columnNames);
		resultadoTable.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(resultadoTable);
		
		
		/**
		 * Adiciona ação de busca ao clique do botão "buscarButton". 
		 * Busca animal pelo nome e exibe dados na tabela "resultadoTable" 
		 * ou exibe mensagem de erro se animal não for encontrado..
		 * 
		 * 
		 */
		buscarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nome = nomeAnimalsField.getText();
				Animal animal = controller.buscarAnimal(nome);
				if (animal != null) {
					Object[][] data = { { animal.getNome(), animal.getPeso(), animal.getCor(), animal.getPossueDoenca(),
							animal.getTipoAnimals() } };
					resultadoTable = new JTable(data, columnNames);
					
					scrollPane.setViewportView(resultadoTable);
				} else {
					JOptionPane.showMessageDialog(frame, "Animal não encontrado!");
				}
			}
		});
		
		/**
		 * Adiciona ação de listagem ao clique do botão "listarButton". 
		 * Lista todos os animais e exibe dados na tabela "resultadoTable".
		 * 
		 * 
		 */
		listarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Animal> animais = controller.listaAnimais();

				Object[][] data = new Object[animais.size()][5];
				for (int i = 0; i < animais.size(); i++) {
					Animal animal = animais.get(i);
					data[i][0] = animal.getNome();
					data[i][1] = animal.getPeso();
					data[i][2] = animal.getCor();
					data[i][3] = animal.getPossueDoenca();
					data[i][4] = animal.getTipoAnimals();
				}

				resultadoTable = new JTable(data, columnNames);
	
				resultadoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane.setViewportView(resultadoTable);
			}
		});
		
		/**
		 * Adiciona ação de edição ao botão "editarButton". 
		 * Abre tela para edição do animal selecionado na tabela.
		 * 
		 * 
		 */
		editarButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = resultadoTable.getSelectedRow();
		        int selectedColumn = resultadoTable.getSelectedColumn();
		        
		        if (selectedRow != -1 && selectedColumn != -1) {
		            String nome = resultadoTable.getValueAt(selectedRow, 0).toString();
		            Animal animal = controller.buscarAnimal(nome);
		            if (animal != null) {
		                //Abrir nova tela para edição
		                TelaEditarAnimal telaEdicao = new TelaEditarAnimal(controller, animal);
		                telaEdicao.mostrar();
		            } else {
		                JOptionPane.showMessageDialog(frame, "Animal não encontrado!");
		            }
		        } else {
		            JOptionPane.showMessageDialog(frame, "Selecione um animal na tabela para editar!");
		        }
		    }
		});
		
		/**
		 * Adiciona ação de adição ao botão "adicionarButton". Abre tela para cadastro de novo animal.
		 * 
		 * 
		 */
		adicionarButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	TelaCadastroAnimal telaAnimal = new TelaCadastroAnimal(controller);
	            telaAnimal.mostrar();;
	        }
	    });
		
		/**
		 * Adiciona um listener de clique do mouse à tabela de resultados.
		 * Se o usuário clicar em uma linha da tabela, os dados daquele animal serão obtidos e 
		 * passados para uma nova tela de edição. 
		 * 
		 * 
		 * 
		 */
		resultadoTable.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {
		            int selectedRow = resultadoTable.getSelectedRow();
		            if (selectedRow >= 0) {
		            	
		                // Obtem os dados da linha selecionada
		                String nome = (String) resultadoTable.getValueAt(selectedRow, 0);
		                float peso = (float) resultadoTable.getValueAt(selectedRow, 1);
		                String cor = (String) resultadoTable.getValueAt(selectedRow, 2);
		                boolean possueDoenca = (boolean) resultadoTable.getValueAt(selectedRow, 3);
		                String tipo = (String) resultadoTable.getValueAt(selectedRow, 4);
		                Animal animal = controller.buscarAnimal(nome);

		                // Cria uma nova instância da tela de edição e passa os dados para ela
		                TelaEditarAnimal telaEditar = new TelaEditarAnimal(controller, animal);
		                telaEditar.mostrar();
		            }
		        }
		    }
		});
		
		

		frame.add(nomeAnimalsField);
		frame.add(buscarButton);
		frame.add(listarButton);
		frame.add(editarButton);
		frame.add(adicionarButton);
	
		frame.add(scrollPane);
		frame.setVisible(true);
	}
	/**
	 * Mostra a tela principal da aplicação.
	 * 
	 * 
	 * 
	 * 
	 * 
	 */	
	public void mostrarTela() {
		if (!frame.isVisible()) {
			frame.setVisible(false);
		}
	}
}