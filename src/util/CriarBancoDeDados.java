package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class CriarBancoDeDados {
    public static void criarBancoETabelas() {
        Connection connection = null;
        Statement stmt = null;

        try {
            Properties props = new Properties();
            FileInputStream input = new FileInputStream("config.ini");
            props.load(input);

            String host = props.getProperty("host");
            String port = props.getProperty("port");
            String database = props.getProperty("database");
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            String url = "jdbc:postgresql://" + host + ":" + port + "/postgres";

            connection = DriverManager.getConnection(url, user, password);
            stmt = connection.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + database + "'");
            if (rs.next()) {
                System.out.println("Este banco de dados já existe! \nContinuando com o programa...\n\n\n");
                return;
            }

            String sqlCreateDB = "CREATE DATABASE " + database;
            stmt.executeUpdate(sqlCreateDB);
            System.out.println("Banco de dados criado com sucesso!");

            connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, user, password);
            stmt = connection.createStatement();

            String sqlCreateCliente = "CREATE TABLE IF NOT EXISTS Cliente ("
                    + "idcliente SERIAL PRIMARY KEY, "
                    + "nome VARCHAR(100), "
                    + "cpf VARCHAR(11), "
                    + "dtnascimento DATE, "
                    + "endereco VARCHAR(255), "
                    + "telefone VARCHAR(15))";

            String sqlCreateProduto = "CREATE TABLE IF NOT EXISTS Produto ("
                    + "idproduto SERIAL PRIMARY KEY, "
                    + "descricao VARCHAR(100), "
                    + "vlcusto DECIMAL(10, 2), "
                    + "vlvenda DECIMAL(10, 2), "
                    + "categoria VARCHAR(50))";

            String sqlCreatePedido = "CREATE TABLE IF NOT EXISTS Pedido ("
                    + "idpedido SERIAL PRIMARY KEY, "
                    + "dtemissao DATE, "
                    + "dtentrega DATE, "
                    + "valortotal DECIMAL(10, 2), "
                    + "observacao TEXT, "
                    + "idcliente INT, "
                    + "FOREIGN KEY (idcliente) REFERENCES Cliente(idcliente))";

            String sqlCreatePedidoItens = "CREATE TABLE IF NOT EXISTS PedidoItens ("
                    + "idpedidoitem SERIAL PRIMARY KEY, "
                    + "idpedido INT, "
                    + "idproduto INT, "
                    + "vlunitario DECIMAL(10, 2), "
                    + "qtproduto INT, "
                    + "vldesconto DECIMAL(10, 2), "
                    + "FOREIGN KEY (idpedido) REFERENCES Pedido(idpedido), "
                    + "FOREIGN KEY (idproduto) REFERENCES Produto(idproduto))";

            stmt.executeUpdate(sqlCreateCliente);
            stmt.executeUpdate(sqlCreateProduto);
            stmt.executeUpdate(sqlCreatePedido);
            stmt.executeUpdate(sqlCreatePedidoItens);

            String inserirCliente = "INSERT INTO cliente (nome, cpf, dtnascimento, endereco, telefone) "
                    + "VALUES "
                    + "('João Silva', '12345678910', '1985-06-15', 'Rua A, 100', '11999999999'),"
                    + "('Maria Souza', '12345678911', '1990-07-16', 'Rua B, 200', '11999999988'),"
                    + "('Carlos Andrade', '12345678912', '1982-08-17', 'Rua C, 300', '11999999977'),"
                    + "('Fernanda Lima', '12345678913', '1988-09-18', 'Rua D, 400', '11999999966'),"
                    + "('Ricardo Nunes', '12345678914', '1975-10-19', 'Rua E, 500', '11999999955');";
            stmt.executeUpdate(inserirCliente);

    String inserirProduto = "INSERT INTO produto (descricao, vlcusto, vlvenda, categoria) "
                    + "VALUES "
                    + "('Notebook', 1500.00, 2000.00, 'Eletrônicos'),"
                    + "('Smartphone', 800.00, 1200.00, 'Eletrônicos'),"
                    + "('Tablet', 600.00, 900.00, 'Eletrônicos'),"
                    + "('Smartwatch', 200.00, 350.00, 'Acessórios'),"
                    + "('Headphone', 150.00, 250.00, 'Acessórios');";
    	stmt.executeUpdate(inserirProduto);


            System.out.println("Banco de dados e tabelas criados com sucesso!");

        } catch (SQLException | IOException e) {
            System.out.println("Erro ao criar o banco de dados ou tabelas.");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
