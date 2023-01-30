package modelos;

import java.time.LocalDate;

/**  
 * 
 * <p>A classe Vacina serve para modelar as vacinas</p> 
 * @author Yan Werlley
 * 
 */

public class Vacina {
    private String nome;
    private String dataValidade;

    public Vacina(String nome, String dataValidade) {
        this.nome = nome;
        this.dataValidade = dataValidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataAplicacao() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }

    @Override
    public String toString() {
        return "Vacina: " + this.nome + " | Data de validade: " + this.dataValidade;
    }
}