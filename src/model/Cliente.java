package model;

import java.util.Date;

public class Cliente extends Pessoa {
    private String endereco;
    private String telefone;

    public Cliente(int idcliente, String nome, String cpf, Date dtnascimento, String endereco, String telefone) {
        super(idcliente, nome, cpf, dtnascimento);
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public Cliente() {
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Cliente ID=" + getId() + ", Nome=" + getNome() + ", CPF=" + getCpf() +
                ", Data de Nascimento=" + getDtnascimento() + ", Endereço=" + endereco + ", Telefone=" + telefone;
    }
}
