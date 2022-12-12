package modelos;

public class Vacina {
	
	private int quantidadeVacinaTot;
	private String nomeVacina;
	private double dosagem;
	private int quantUsada;
	
	
	
	@Override
	public String toString() {
		return "Quantidade = " + quantidadeVacinaTot + ", Nome = " + nomeVacina + ", Dosagem = " + dosagem +"\n";
	}
	
	public Vacina() {
		
	}
	
	public Vacina(String nomeVacina, int quantidadeVacinaTot, double dosagem, int quantUsada) {
		this.quantidadeVacinaTot =  quantidadeVacinaTot - quantUsada;
		this.nomeVacina  = nomeVacina;
		this.dosagem = dosagem;
		this.quantUsada = quantUsada;
	}
	
	
    
	
	public int getQuantUsada() {
		return quantUsada;
	}

	public void setQuantUsada(int quantUsada) {
		this.quantUsada = quantUsada;
	}

	public int getQuantidadeVacinaTot() {
		return quantidadeVacinaTot;
	}

	public void setQuantidadeVacinaTot(int quantidadeVacinaTot) {
		this.quantidadeVacinaTot = quantidadeVacinaTot;
	}

	public String getNomeVacina() {
		return nomeVacina;
	}
	public void setNomeVacina(String nomeVacina) {
		this.nomeVacina = nomeVacina;
	}
	public double getDosagem() {
		return dosagem;
	}
	public void setDosagem(double dosagem) {
		this.dosagem = dosagem;
	}
	
	
}
