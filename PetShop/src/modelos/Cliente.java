package modelos;

/**
 * <p> A classe Cliente, que extende Pessoa, serve para generalizar todos os cliente.</p> 
 * @see Pessoa 
 * @author Yan Werlley
 *
 */

public class Cliente extends Pessoa {
	private int idCliente;
	private String nomepet;

	public String getNomepet() {
		return nomepet;
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

	@Override
	public String toString() {
		return "O animal " + nomepet + " tomou as seguintes vacinas:\n";
	}
}