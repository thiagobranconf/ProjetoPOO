package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.PedidoItens;

public class PedidoItensDAO implements CRUD<PedidoItens> {
    private Connection connection;

    public PedidoItensDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void adicionar(PedidoItens item) throws SQLException {
        String sql = "INSERT INTO pedidoitens (idpedido, idproduto, vlunitario, qtproduto, vldesconto) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, item.getIdpedido());
            stmt.setInt(2, item.getIdproduto());
            stmt.setDouble(3, item.getVlunitario());
            stmt.setInt(4, item.getQtproduto());
            stmt.setDouble(5, item.getVldesconto());
            stmt.executeUpdate();
        }
    }

    @Override
    public PedidoItens buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM pedidoitens WHERE idpedidoitem = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PedidoItens(
                    rs.getInt("idpedidoitem"),
                    rs.getInt("idpedido"),
                    rs.getInt("idproduto"),
                    rs.getDouble("vlunitario"),
                    rs.getInt("qtproduto"),
                    rs.getDouble("vldesconto")
                );
            }
        }
        return null;
    }

    @Override
    public List<PedidoItens> listarTodos() throws SQLException {
        List<PedidoItens> itens = new ArrayList<>();
        String sql = "SELECT * FROM pedidoitens";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                itens.add(new PedidoItens(
                    rs.getInt("idpedidoitem"),
                    rs.getInt("idpedido"),
                    rs.getInt("idproduto"),
                    rs.getDouble("vlunitario"),
                    rs.getInt("qtproduto"),
                    rs.getDouble("vldesconto")
                ));
            }
        }
        return itens;
    }

    @Override
    public void atualizar(PedidoItens item) throws SQLException {
        String sql = "UPDATE pedidoitens SET idpedido = ?, idproduto = ?, vlunitario = ?, qtproduto = ?, vldesconto = ? WHERE idpedidoitem = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, item.getIdpedido());
            stmt.setInt(2, item.getIdproduto());
            stmt.setDouble(3, item.getVlunitario());
            stmt.setInt(4, item.getQtproduto());
            stmt.setDouble(5, item.getVldesconto());
            stmt.setInt(6, item.getIdpedidoitem());
            stmt.executeUpdate();
        }
    }

    @Override
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM pedidoitens WHERE idpedidoitem = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public PedidoItens buscarPorPedidoEProduto(int idPedido, int idProduto) throws SQLException {
        String sql = "SELECT * FROM pedidoitens WHERE idpedido = ? AND idproduto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.setInt(2, idProduto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PedidoItens(
                    rs.getInt("idpedidoitem"),
                    rs.getInt("idpedido"),
                    rs.getInt("idproduto"),
                    rs.getDouble("vlunitario"),
                    rs.getInt("qtproduto"),
                    rs.getDouble("vldesconto")
                );
            }
        }
        return null;
    }

    public void excluirTodos(int idPedido) throws SQLException {
        String sql = "DELETE FROM pedidoitens WHERE idpedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        }
    }
    
    public void excluirPorPedido(int idPedido) throws SQLException {
        String sql = "DELETE FROM pedidoitens WHERE idpedido = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir itens do pedido: " + e.getMessage());
            throw e;
        }
    }

    public List<PedidoItens> buscarPorPedido(int idPedido) throws SQLException {
        String sql = "SELECT * FROM pedidoitens WHERE idpedido = ?";
        List<PedidoItens> itens = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                PedidoItens item = new PedidoItens();
                item.setIdpedido(rs.getInt("idpedido"));
                item.setIdproduto(rs.getInt("idproduto"));
                item.setVlunitario(rs.getDouble("vlunitario"));
                item.setQtproduto(rs.getInt("qtproduto"));
                item.setVldesconto(rs.getDouble("vldesconto"));
                itens.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar itens do pedido: " + e.getMessage());
            throw e;
        }
        
        return itens;
    }
    
    public void excluirPorPedidoEProduto(int idPedido, int idProduto) throws SQLException {
        String sql = "DELETE FROM pedidoitens WHERE idpedido = ? AND idproduto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.setInt(2, idProduto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir item do pedido: " + e.getMessage());
            throw e;
        }
    }
}
