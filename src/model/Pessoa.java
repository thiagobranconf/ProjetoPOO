package model;

import java.util.Date;

public abstract class Pessoa {
    private int id;
    private String nome;
    private String cpf;
    private Date dtnascimento;

    public Pessoa(int id, String nome, String cpf, Date dtnascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dtnascimento = dtnascimento;
    }

    public Pessoa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDtnascimento() {
        return dtnascimento;
    }

    public void setDtnascimento(Date dtnascimento) {
        this.dtnascimento = dtnascimento;
    }

    @Override
    public String toString() {
        return "Pessoa ID=" + id + ", Nome=" + nome + ", CPF=" + cpf + ", Data de Nascimento=" + dtnascimento;
    }
}
