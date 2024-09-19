package model;

public class PedidoItens {
    private int idpedidoitem;
    private int idpedido;
    private int idproduto;
    private double vlunitario;
    private int qtproduto;
    private double vldesconto;

    public PedidoItens(int idpedidoitem, int idpedido, int idproduto, double vlunitario, int qtproduto, double vldesconto) {
        this.idpedidoitem = idpedidoitem;
        this.idpedido = idpedido;
        this.idproduto = idproduto;
        this.vlunitario = vlunitario;
        this.qtproduto = qtproduto;
        this.vldesconto = vldesconto;
    }

    public PedidoItens() {
    }

    public int getIdpedidoitem() {
        return idpedidoitem;
    }

    public void setIdpedidoitem(int idpedidoitem) {
        this.idpedidoitem = idpedidoitem;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(int idproduto) {
        this.idproduto = idproduto;
    }

    public double getVlunitario() {
        return vlunitario;
    }

    public void setVlunitario(double vlunitario) {
        this.vlunitario = vlunitario;
    }

    public int getQtproduto() {
        return qtproduto;
    }

    public void setQtproduto(int qtproduto) {
        this.qtproduto = qtproduto;
    }

    public double getVldesconto() {
        return vldesconto;
    }

    public void setVldesconto(double vldesconto) {
        this.vldesconto = vldesconto;
    }

    @Override
    public String toString() {
        return "PedidoItens ID Pedido=" + idpedido + ", ID Produto=" + idproduto + ", Valor Unitário=" + vlunitario +
                ", Quantidade=" + qtproduto + ", Valor Desconto=" + vldesconto;
    }
}
