# Sistema de Gestão de Pedidos - Trabalho Final de POO

## Descrição

Este sistema foi desenvolvido como parte do trabalho final da disciplina de Programação Orientada a Objetos (POO) SERRATEC 2024. O objetivo do sistema é permitir a criação e gestão de pedidos para clientes com um ou mais produtos, com funcionalidades de inclusão, alteração, exclusão e consulta.

## Funcionalidades

1. **Cadastro de Clientes**
   - Os clientes já estão cadastrados previamente na tabela, não sendo necessário criar um cadastro de clientes no sistema.

2. **Cadastro de Produtos**
   - Os produtos já estão cadastrados previamente na tabela, não sendo necessário criar um cadastro de produtos no sistema.

3. **Gestão de Pedidos**
   - Incluir, alterar e excluir pedidos.
   - Localizar pedidos por código, cliente ou data.
   - Incluir um ou mais produtos ao pedido.
   - Alterar ou excluir produtos no pedido.
   - Alterar o cliente do pedido.
   - Não permitir salvar pedidos sem cliente ou sem produtos.

4. **Impressão de Pedidos**
   - Imprimir pedido com os dados do cliente (sem produtos).
   - Imprimir pedido com os dados do cliente e dos produtos.

## Estrutura de Classes

- **Pessoa (abstract)**: Classe base para representar uma pessoa.
- **Cliente**: Extende Pessoa e contém as informações do cliente.
- **Produto**: Representa um produto disponível para venda.
- **Pedido**: Representa um pedido feito por um cliente.
- **PedidoItens**: Representa os itens do pedido, contendo detalhes dos produtos.
- **CRUD Interface**: Interface para operações de inclusão, consulta, atualização e exclusão (Create, Read, Update, Delete).
- **DAO Classes**: Classes responsáveis pela comunicação com o banco de dados para persistência de dados.

## Requisitos Técnicos

- **Banco de Dados**: PostgreSQL.
- **Arquivo de Configuração**: Um arquivo `.ini` é utilizado para configurar a conexão com o banco de dados.
- **Persistência de Dados**: O sistema salva e recupera dados do banco de dados PostgreSQL.
  
## Estrutura do Banco de Dados

- **Cliente**: `idcliente`, `nome`, `cpf`, `dtnascimento`, `endereco`, `telefone`
- **Produto**: `idproduto`, `descricao`, `vlcusto`, `vlvenda`, `categoria`
- **Pedido**: `idpedido`, `dtemissao`, `dtentrega`, `valortotal`, `observacao`
- **PedidoItens**: `idpedidoitem`, `vlunitario`, `qtproduto`, `vldesconto`

## Como Executar o Projeto

Utilize a interface do sistema para gerar e gerenciar pedidos conforme as funcionalidades descritas.
