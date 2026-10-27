# PROJETO BUILDER PATTERN
## Swing + AWT

## Estrutura do Projeto

O projeto foi desenvolvido em Java, utilizando o padrão de projeto **Builder** para auxiliar na construção de objetos,
uso da biblioteca AWT combinado com Swing para a interface navegável e
a arquitetura **DAO (Data Access Object)** para organizar o acesso ao banco de dados.
A estrutura é dividida em pacotes que separam as responsabilidades da aplicação, facilitando a manutenção, a organização e a reutilização do código.

### Organização dos Pacotes

```text
src/
├── br.unip.builder/
│    ├── builder/
│    │   ├── ClienteBuilder.java
│    │   ├── EntregaBuilder.java
│    │   ├── PedidoBuilder.java
│    │   └── ProdutoBuilder.java
│    │
│    ├── dao/
│    │   ├── ClienteDAO.java
│    │   ├── Conexao.java
│    │   ├── EntregaDAO.java
│    │   ├── PedidoDAO.java
│    │   ├── ProdutoDAO.java
│    │   └── UsuarioDAO.java
│    │
│    ├── main/
│    │   └── Main.java
│    │
│    ├── model/
│    │   ├── Cliente.java
│    │   ├── Entrega.java
│    │   ├── ItemPedido.java
│    │   ├── Pedido.java
│    │   ├── Produto.java
│    │   └── Usuario.java
│    │
│    ├── util/
│    │   ├── Sessao.java
│    │   └── Tema.java
│    │
│    └── view/
│        ├── TelaCadastroCliente.java
│        ├── TelaCadastroPedido.java
│        ├── TelaClientesCadastrados.java
│        ├── TelaEntrega.java
│        ├── TelaEstoque.java
│        ├── TelaLogin.java
│        └── TelaPrincipal.java
│
└── resources/
    └── javalogo.png

backup_sistema.sql
.gitignore
BuilderTeste.iml
mysql-connector-j-9.0.0.jar
README.md
```

## Como rodar o projeto

### Banco de dados
1. Instale o MySQL
2. Execute o arquivo `banco.sql` no seu cliente MySQL
3. Ajuste as credenciais de acesso em `/br/unip/dao/Conexao.java`

### Projeto
1. Abra no seu IDE
2. Adicione o `mysql-connector-j-9.0.0.jar` nas dependências
3. Rode o `Main.java`