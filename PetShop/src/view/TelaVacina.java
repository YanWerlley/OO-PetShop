package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;

import CRUDS.ControleAnimal;
import CRUDS.ControleVacina;
import modelos.Animal;
import modelos.Vacina;

/**  
 * 
 * <p>A classe TelaVacina cria uma janela com as vacinas aplicadas em cada Animal</p>  
 * @author Yan Werlley
 * 
 */
public class TelaVacina extends JFrame {
	private JPanel contentPane;
	private JTable table;
	private JComboBox<String> cbAnimais;
	private List<Animal> animais;
	private JTextField nomeAnimalField;
	private ControleAnimal controller;
	private ControleVacina controller2;
	private JButton addVacinaBotao;
	private JButton excluirVacina;

	public TelaVacina(ControleAnimal controller) {
		this.controller = controller;
		setTitle("Vacinas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setLayout(new FlowLayout());

		JPanel panel = new JPanel();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblSelecioneOAnimal = new JLabel("Selecione ou busque o animal:");
		panel.add(lblSelecioneOAnimal);

		cbAnimais = new JComboBox<String>();
		cbAnimais.addItem("Selecione");
		cbAnimais.addItem("Todas");

		for (Animal animal : controller.listaAnimais()) {
			cbAnimais.addItem(animal.getNome());
		}
		cbAnimais.addActionListener(e -> preencheTabela());
		panel.add(cbAnimais);

		panel.add(cbAnimais);

		nomeAnimalField = new JTextField(20);
		nomeAnimalField.addActionListener(e -> buscaAnimal());
		panel.add(nomeAnimalField);

		JPanel panel_1 = new JPanel();

		contentPane.add(panel_1, BorderLayout.CENTER);

		panel_1.setLayout(new BorderLayout(0, 0));

		table = new JTable();
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Nome da Vacina", "Data da Aplicação" }));
		panel_1.add(table);

		JPanel botoesPanel = new JPanel();
		contentPane.add(botoesPanel, BorderLayout.SOUTH);

		
		JButton btnExcluir = new JButton("Excluir");
		
		/**
		 * Esse código adiciona uma ação ao botão "btnExcluir" para remover uma vacina de um animal específico. 
		 * Quando o botão é clicado, ele verifica se uma linha na tabela está selecionada. 
		 * Se sim, ele pega o nome do animal selecionado no combobox "cbAnimais" e o nome da vacina selecionada na 
		 * linha selecionada da tabela. Ele passa esses dois valores para o método "removerVacina"
		 * 
		 * 
		 * 
		 * 
		 * 
		 */	
		btnExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = table.getSelectedRow();
				if (linhaSelecionada != -1) {
					String nomeAnimal = (String) cbAnimais.getSelectedItem();
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					String nomeVacina = (String) model.getValueAt(linhaSelecionada, 0);
					
					controller.removerVacina(nomeAnimal, nomeVacina);
					model.removeRow(linhaSelecionada);
					JOptionPane.showMessageDialog(TelaVacina.this, "Vacina excluída com sucesso!");
				} else {
					JOptionPane.showMessageDialog(TelaVacina.this, "Selecione uma linha!");
				}
			}
		});

		botoesPanel.add(btnExcluir);

	}
	
	/**
	 * Este método preenche a tabela de vacinas de acordo com a seleção do animal no combobox. 
	 * Se "Todas" for selecionado, então todas as vacinas de todos os animais serão exibidas na tabela. 
	 * Caso contrário, somente as vacinas do animal selecionado serão exibidas. 
	 * Se o animal selecionado não for encontrado, a tabela será limpa.
	 * 
	 * 
	 * 
	 * 
	 * 
	 */	
	private void preencheTabela() {
		String nomeAnimal = (String) cbAnimais.getSelectedItem();
		if (nomeAnimal.equals("Todas")) {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);
			for (Animal animal : controller.listaAnimais()) {
				for (Vacina vacina : animal.getVacina()) {
					model.addRow(new Object[] { vacina.getNome(), vacina.getDataAplicacao() });
				}
			}
		} else {
			Animal animal = controller.buscarAnimal(nomeAnimal);
			if (animal != null) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				for (Vacina vacina : animal.getVacina()) {
					model.addRow(new Object[] { vacina.getNome(), vacina.getDataAplicacao() });
				}
			}
		}
	}
	
	/**
	 * Método buscaAnimals busca animais pelo nome digitado no campo nomeAnimalsField e seleciona o animal encontrado no combo box cbAnimais. 
	 * Se o animal não for encontrado, não faz nada.
	 * 
	 * 
	 * 
	 * 
	 * 
	 */	
	private void buscaAnimal() {
		String nomeAnimal = nomeAnimalField.getText();
		Animal animal = controller.buscarAnimal(nomeAnimal);
		if (animal != null) {
			cbAnimais.setSelectedItem(animal.getNome());
		}
	}
}