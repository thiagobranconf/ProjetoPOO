package model;

public class Produto {
    private int idproduto;
    private String descricao;
    private double vlcusto;
    private double vlvenda;
    private String categoria;

    public Produto(int idproduto, String descricao, double vlcusto, double vlvenda, String categoria) {
        this.idproduto = idproduto;
        this.descricao = descricao;
        this.vlcusto = vlcusto;
        this.vlvenda = vlvenda;
        this.categoria = categoria;
    }

    public Produto() {
    }

    public int getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(int idproduto) {
        this.idproduto = idproduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getVlcusto() {
        return vlcusto;
    }

    public void setVlcusto(double vlcusto) {
        this.vlcusto = vlcusto;
    }

    public double getVlvenda() {
        return vlvenda;
    }

    public void setVlvenda(double vlvenda) {
        this.vlvenda = vlvenda;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Produto ID=" + idproduto + ", Descrição=" + descricao + ", Valor Custo=" + vlcusto +
                ", Valor Venda=" + vlvenda + ", Categoria=" + categoria;
    }
}
