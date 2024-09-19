package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Produto;

public class ProdutoDAO implements CRUD<Produto> {
    
    private Connection connection;

    public ProdutoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Produto buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM produto WHERE idproduto = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Produto(
                    rs.getInt("idproduto"),
                    rs.getString("descricao"),
                    rs.getDouble("vlcusto"),
                    rs.getDouble("vlvenda"),
                    rs.getString("categoria")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao buscar o produto.", e);
        }
        return null;
    }

    @Override
    public void adicionar(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (descricao, vlcusto, vlvenda, categoria) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produto.getDescricao());
            stmt.setDouble(2, produto.getVlcusto());
            stmt.setDouble(3, produto.getVlvenda());
            stmt.setString(4, produto.getCategoria());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao adicionar o produto.", e);
        }
    }

    @Override
    public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE produto SET descricao = ?, vlcusto = ?, vlvenda = ?, categoria = ? WHERE idproduto = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, produto.getDescricao());
            stmt.setDouble(2, produto.getVlcusto());
            stmt.setDouble(3, produto.getVlvenda());
            stmt.setString(4, produto.getCategoria());
            stmt.setInt(5, produto.getIdproduto());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao atualizar o produto.", e);
        }
    }

    @Override
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM produto WHERE idproduto = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao excluir o produto.", e);
        }
    }

    @Override
    public List<Produto> listarTodos() throws SQLException {
        String sql = "SELECT * FROM produto";
        List<Produto> produtos = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Produto produto = new Produto(
                    rs.getInt("idproduto"),
                    rs.getString("descricao"),
                    rs.getDouble("vlcusto"),
                    rs.getDouble("vlvenda"),
                    rs.getString("categoria")
                );
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao listar os produtos.", e);
        }
        return produtos;
    }
}
