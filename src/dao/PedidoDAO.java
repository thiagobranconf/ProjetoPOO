package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Pedido;

public class PedidoDAO implements CRUD<Pedido> {
    private Connection connection;

    public PedidoDAO(Connection connection) {
        this.connection = connection;
    }

    public void adicionar(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedido (dtemissao, dtentrega, valortotal, observacao, idcliente) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(pedido.getDtemissao().getTime()));
            stmt.setDate(2, new java.sql.Date(pedido.getDtentrega().getTime()));
            stmt.setDouble(3, pedido.getValortotal());
            stmt.setString(4, pedido.getObservacao());
            stmt.setInt(5, pedido.getIdcliente());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                pedido.setIdpedido(id); 
            }
        }
    }

    @Override
    public Pedido buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE idpedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Pedido(
                    rs.getInt("idpedido"),
                    rs.getDate("dtemissao"),
                    rs.getDate("dtentrega"),
                    rs.getDouble("valortotal"),
                    rs.getString("observacao"),
                    rs.getInt("idcliente")
                );
            }
        }
        return null;
    }

    @Override
    public List<Pedido> listarTodos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pedidos.add(new Pedido(
                    rs.getInt("idpedido"),
                    rs.getDate("dtemissao"),
                    rs.getDate("dtentrega"),
                    rs.getDouble("valortotal"),
                    rs.getString("observacao"),
                    rs.getInt("idcliente")
                ));
            }
        }
        return pedidos;
    }

    @Override
    public void atualizar(Pedido pedido) throws SQLException {
        String sql = "UPDATE pedido SET dtemissao = ?, dtentrega = ?, valortotal = ?, observacao = ?, idcliente = ? WHERE idpedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(pedido.getDtemissao().getTime()));
            stmt.setDate(2, new java.sql.Date(pedido.getDtentrega().getTime()));
            stmt.setDouble(3, pedido.getValortotal());
            stmt.setString(4, pedido.getObservacao());
            stmt.setInt(5, pedido.getIdcliente());
            stmt.setInt(6, pedido.getIdpedido());
            stmt.executeUpdate();
        }
    }

    @Override
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM pedido WHERE idpedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public void imprimirPedido(int idPedido, boolean incluirProdutos) throws SQLException {
        
        String sqlPedido = "SELECT * FROM pedido WHERE idpedido = ?";
        
        try (PreparedStatement stmtPedido = connection.prepareStatement(sqlPedido)) {
            stmtPedido.setInt(1, idPedido);
            ResultSet rsPedido = stmtPedido.executeQuery();
            
            if (rsPedido.next()) {
                System.out.println("===== Detalhes do Pedido =====");
                System.out.println("Pedido ID: " + rsPedido.getInt("idpedido"));
                System.out.println("Data Emissão: " + rsPedido.getDate("dtemissao"));
                System.out.println("Data Entrega: " + rsPedido.getDate("dtentrega"));
                System.out.println("Valor Total: " + rsPedido.getDouble("valortotal"));
                System.out.println("Observação: " + rsPedido.getString("observacao"));

                if (incluirProdutos) {
                    
                    String sqlProdutos = "SELECT * FROM pedidoitens WHERE idpedido = ?";
                    
                    try (PreparedStatement stmtProdutos = connection.prepareStatement(sqlProdutos)) {
                        stmtProdutos.setInt(1, idPedido);
                        ResultSet rsProdutos = stmtProdutos.executeQuery();
                        
                        System.out.println("===== Produtos do Pedido =====");
                        
                        while (rsProdutos.next()) {
                            System.out.println("Produto ID: " + rsProdutos.getInt("idproduto"));
                            System.out.println("Quantidade: " + rsProdutos.getInt("qtproduto"));
                            System.out.println("Valor Unitário: " + rsProdutos.getDouble("vlunitario"));
                            System.out.println("Desconto: " + rsProdutos.getDouble("vldesconto"));
                            System.out.println("----------------------------");
                        }
                    }
                }
            } else {
                System.out.println("Pedido não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao imprimir o pedido: " + e.getMessage());
            throw e;
        }
    }

}
