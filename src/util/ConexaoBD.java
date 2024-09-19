package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class ConexaoBD {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null || isConnectionClosed()) {
            try {
                Properties props = new Properties();
                FileInputStream input = new FileInputStream("config.ini");
                props.load(input);

                String host = props.getProperty("host");
                String port = props.getProperty("port");
                String database = props.getProperty("database");
                String user = props.getProperty("user");
                String password = props.getProperty("password");

                String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");

            } catch (SQLException | IOException e) {
                System.out.println("Erro ao conectar ao banco de dados.");
                e.printStackTrace();
            }
        }
        return connection;
    }

    private static boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão com o banco de dados fechada com sucesso!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão com o banco de dados.");
            e.printStackTrace();
        }
    }
}
