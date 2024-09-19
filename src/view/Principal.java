package view;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.ClienteDAO;
import dao.PedidoDAO;
import dao.PedidoItensDAO;
import dao.ProdutoDAO;
import model.Cliente;
import model.Pedido;
import model.PedidoItens;
import model.Produto;
import util.ConexaoBD;
import util.CriarBancoDeDados;
import util.CriarConfigIniUsuario;


public class Principal {

    private static PedidoDAO pedidoDAO;
    private static PedidoItensDAO pedidoItensDAO;
    private static ClienteDAO clienteDAO;
    private static ProdutoDAO produtoDAO;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
    	
          CriarConfigIniUsuario.criarArquivoIni(scanner);
          CriarBancoDeDados.criarBancoETabelas();
    	
          pedidoDAO = new PedidoDAO(ConexaoBD.getConnection());
          pedidoItensDAO = new PedidoItensDAO(ConexaoBD.getConnection());
          clienteDAO = new ClienteDAO(ConexaoBD.getConnection());
          produtoDAO = new ProdutoDAO(ConexaoBD.getConnection());
          
            int opcao;
            do {
                System.out.println("\n====== Sistema de Pedidos ======");
                System.out.println("1. Criar Pedido");
                System.out.println("2. Alterar Pedido");
                System.out.println("3. Excluir Pedido");
                System.out.println("4. Imprimir Pedido (sem produtos)");
                System.out.println("5. Imprimir Pedido (com produtos)");
                System.out.println("6. Localizar Pedido por Código");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");
                opcao = lerInteiroComTratamento();

                switch (opcao) {
                    case 1:
                        criarPedido();
                        break;
                    case 2:
                        alterarPedido();
                        break;
                    case 3:
                        excluirPedido();
                        break;
                    case 4:
                        imprimirPedido(false);
                        break;
                    case 5:
                        imprimirPedido(true);
                        break;
                    case 6:
                        localizarPedidoPorCodigo();
                        break;
                    case 0:
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            } while (opcao != 0);
    }

    private static void criarPedido() throws SQLException {
        Pedido pedido = new Pedido();
        System.out.println("\n===== Criar Pedido =====");
        System.out.print("Digite o ID do Cliente: ");
        int idCliente = lerInteiroComTratamento();
        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if (cliente == null) {
            System.out.println("Cliente não encontrado. Operação cancelada.");
            return;
        }
        pedido.setIdcliente(idCliente);
        System.out.print("Data de Emissão (YYYY-MM-DD): ");
        pedido.setDtemissao(java.sql.Date.valueOf(scanner.nextLine()));
        System.out.print("Data de Entrega (YYYY-MM-DD): ");
        pedido.setDtentrega(java.sql.Date.valueOf(scanner.nextLine()));
        System.out.print("Observação: ");
        pedido.setObservacao(scanner.nextLine());

        pedidoDAO.adicionar(pedido);
        System.out.println("Pedido criado com sucesso!");

        incluirProdutosNoPedido(pedido.getIdpedido());
    }

    private static void alterarPedido() throws SQLException {
        System.out.println("\n===== Alterar Pedido =====");
        System.out.print("Digite o ID do Pedido a ser alterado: ");
        int idPedido = lerInteiroComTratamento();
        Pedido pedido = pedidoDAO.buscarPorId(idPedido);
        if (pedido == null) {
            System.out.println("Pedido não encontrado.");
            return;
        }

        System.out.println("Deseja alterar o cliente? (1-Sim / 0-Não)");
        if (lerInteiroComTratamento() == 1) {
            alterarClienteDoPedido(pedido);
        }

        System.out.println("Deseja alterar os produtos? (1-Sim / 0-Não)");
        if (lerInteiroComTratamento() == 1) {
            alterarProdutosNoPedido(idPedido);
        }
    }

    private static void excluirPedido() throws SQLException {
        System.out.println("\n===== Excluir Pedido =====");
        System.out.print("Digite o ID do Pedido a ser excluído: ");
        int idPedido = lerInteiroComTratamento();
        pedidoItensDAO.excluirPorPedido(idPedido);
        pedidoDAO.excluir(idPedido);
        System.out.println("Pedido excluído com sucesso.");
    }

    private static void imprimirPedido(boolean incluirProdutos) throws SQLException {
        System.out.println("\n===== Imprimir Pedido =====");
        System.out.print("Digite o ID do Pedido: ");
        int idPedido = lerInteiroComTratamento();
        pedidoDAO.imprimirPedido(idPedido, incluirProdutos);
    }

    private static void localizarPedidoPorCodigo() throws SQLException {
        System.out.println("\n===== Localizar Pedido por Código =====");
        System.out.print("Digite o ID do Pedido: ");
        int idPedido = lerInteiroComTratamento();
        Pedido pedido = pedidoDAO.buscarPorId(idPedido);
        if (pedido != null) {
            System.out.println(pedido);
        } else {
            System.out.println("Pedido não encontrado.");
        }
    }

    private static void incluirProdutosNoPedido(int idPedido) throws SQLException {
        System.out.print("Quantos produtos deseja adicionar ao pedido? ");
        int numProdutos = lerInteiroComTratamento();
        double valorTotal = 0;

        for (int i = 0; i < numProdutos; i++) {
            PedidoItens item = new PedidoItens();
            item.setIdpedido(idPedido);
            System.out.print("Digite o ID do Produto: ");
            int idProduto = lerInteiroComTratamento();
            Produto produto = produtoDAO.buscarPorId(idProduto);
            if (produto == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }
            item.setIdproduto(idProduto);
            item.setVlunitario(produto.getVlvenda());
            System.out.print("Digite a quantidade: ");
            item.setQtproduto(lerInteiroComTratamento());
            System.out.print("Digite o valor de desconto: ");
            item.setVldesconto(lerDoubleComTratamento());

            valorTotal += item.getQtproduto() * item.getVlunitario() - item.getVldesconto();

            pedidoItensDAO.adicionar(item);
            System.out.println("Produto adicionado ao pedido.");
        }

        Pedido pedido = pedidoDAO.buscarPorId(idPedido);
        pedido.setValortotal(valorTotal);
        pedidoDAO.atualizar(pedido);
    }

    private static void alterarClienteDoPedido(Pedido pedido) throws SQLException {
        System.out.print("Digite o ID do novo cliente: ");
        int novoIdCliente = lerInteiroComTratamento();
        Cliente novoCliente = clienteDAO.buscarPorId(novoIdCliente);
        if (novoCliente != null) {
            pedido.setIdcliente(novoIdCliente);
            pedidoDAO.atualizar(pedido);
            System.out.println("Cliente do pedido alterado com sucesso.");
        } else {
            System.out.println("Cliente não encontrado. Operação cancelada.");
        }
    }

    private static void alterarProdutosNoPedido(int idPedido) throws SQLException {
        System.out.println("===== Produtos no Pedido =====");
        List<PedidoItens> itens = pedidoItensDAO.buscarPorPedido(idPedido);
        for (PedidoItens item : itens) {
            System.out.println(item);
        }

        System.out.println("Deseja alterar algum produto? (1-Sim / 0-Não)");
        if (lerInteiroComTratamento() == 1) {
            incluirProdutosNoPedido(idPedido);
        }

        System.out.println("Deseja excluir algum produto? (1-Sim / 0-Não)");
        if (lerInteiroComTratamento() == 1) {
            System.out.print("Digite o ID do Produto a ser excluído: ");
            int idProduto = lerInteiroComTratamento();
            pedidoItensDAO.excluirPorPedidoEProduto(idPedido, idProduto);
            System.out.println("Produto excluído com sucesso.");

            List<PedidoItens> itens1 = pedidoItensDAO.buscarPorPedido(idPedido);
            double valorTotal = 0;
            for (PedidoItens item : itens1) {
                valorTotal += item.getQtproduto() * item.getVlunitario() - item.getVldesconto();
            }
            Pedido pedido = pedidoDAO.buscarPorId(idPedido);
            pedido.setValortotal(valorTotal);
            pedidoDAO.atualizar(pedido);
        }
    }

    private static int lerInteiroComTratamento() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida! Digite um número: ");
            }
        }
    }

    private static double lerDoubleComTratamento() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida! Digite um valor numérico: ");
            }
        }
    }
}
