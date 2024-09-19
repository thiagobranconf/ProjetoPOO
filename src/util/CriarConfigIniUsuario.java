package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CriarConfigIniUsuario {

    public static void criarArquivoIni(Scanner sc) {
        File configFile = new File("config.ini");

        if (configFile.exists()) {
            System.out.println("O arquivo config.ini já existe.");
        } else {
            try (FileWriter writer = new FileWriter(configFile)) {
                System.out.print("Digite o host: ");
                String host = sc.nextLine();
                System.out.print("Digite a porta: ");
                String port = sc.nextLine();
                System.out.print("Digite o nome do banco de dados: ");
                String database = sc.nextLine();
                System.out.print("Digite o usuário: ");
                String user = sc.nextLine();
                System.out.print("Digite a senha: ");
                String password = sc.nextLine();

                writer.write("host=" + host + "\n");
                writer.write("port=" + port + "\n");
                writer.write("database=" + database + "\n");
                writer.write("user=" + user + "\n");
                writer.write("password=" + password + "\n");

                System.out.println("Arquivo config.ini criado com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao criar arquivo config.ini");
                e.printStackTrace();
            }
        }
    }
}



