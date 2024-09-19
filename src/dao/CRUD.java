package dao;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {
    void adicionar(T objeto) throws SQLException;
    T buscarPorId(int id) throws SQLException;
    List<T> listarTodos() throws SQLException;
    void atualizar(T objeto) throws SQLException;
    void excluir(int id) throws SQLException;
}
