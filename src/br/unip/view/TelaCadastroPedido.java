package br.unip.view;

import br.unip.builder.PedidoBuilder;
import br.unip.dao.ClienteDAO;
import br.unip.dao.PedidoDAO;
import br.unip.dao.ProdutoDAO;
import br.unip.model.Cliente;
import br.unip.model.Pedido;
import br.unip.model.Produto;
import br.unip.util.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TelaCadastroPedido extends JFrame {
    private JComboBox<Cliente> cmbCliente;
    private JComboBox<Produto> cmbProduto;
    private JTextField txtQuantidade;
    private JTable tabelaItens;
    private DefaultTableModel modeloTabela;
    private PedidoBuilder pedidoBuilder;
    private double total = 0;
    JLabel lblTotal;
    public TelaCadastroPedido() {
        setTitle("Cadastro de Pedido");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        Tema.aplicarJanela(this);

        pedidoBuilder = new PedidoBuilder();

        // título
        JLabel lblTitulo = Tema.labelTitulo("Cadastro de Pedido");
        lblTitulo.setBounds(20, 20, 460, 25);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 25));
        add(lblTitulo);

        // cliente
        JLabel lblCliente = Tema.label("Cliente:");
        lblCliente.setBounds(20, 60, 80, 25);
        add(lblCliente);

        cmbCliente = new JComboBox<>();
        cmbCliente.setBounds(100, 60, 460, 25);
        Tema.aplicarCombo(cmbCliente);
        add(cmbCliente);

        // separador
        JSeparator separador = Tema.separador();
        separador.setBounds(20, 100, 540, 10);
        add(separador);

        // produto
        JLabel lblProduto = Tema.label("Produto:");
        lblProduto.setBounds(20, 120, 80, 25);
        add(lblProduto);

        cmbProduto = new JComboBox<>();
        cmbProduto.setBounds(100, 120, 300, 25);
        Tema.aplicarCombo(cmbProduto);
        add(cmbProduto);

        // quantidade
        JLabel lblQuantidade = Tema.label("Qtd:");
        lblQuantidade.setBounds(415, 120, 40, 25);
        add(lblQuantidade);

        txtQuantidade = Tema.campo();
        txtQuantidade.setBounds(450, 120, 60, 25);
        add(txtQuantidade);

        // botão adicionar item
        JButton btnAdicionarItem = Tema.botaoSecundario("+ Adicionar");
        btnAdicionarItem.setBounds(200, 160, 150, 30);
        btnAdicionarItem.addActionListener(e -> adicionarItem());
        add(btnAdicionarItem);

        // Soma de totais
        lblTotal = Tema.labelTitulo(String.format("R$ %.2f", total));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setBounds(410, 205, 150, 20);
        add(lblTotal);

        // tabela de itens
        JLabel lblItens = Tema.label("Itens do Pedido:");
        lblItens.setBounds(20, 205, 150, 20);
        add(lblItens);

        String[] colunas = {"Produto", "Quantidade", "Preço Unit.", "Subtotal"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaItens = new JTable(modeloTabela);
        tabelaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Tema.aplicarTabela(tabelaItens);

        JScrollPane scroll = new JScrollPane(tabelaItens);
        scroll.setBounds(20, 230, 540, 150);
        add(scroll);

        // botão remover item
        JButton btnRemover = Tema.botaoSecundario("Remover Item");
        btnRemover.setBounds(20, 390, 130, 30);
        btnRemover.setForeground(Color.RED);
        btnRemover.addActionListener(e -> removerItem());
        add(btnRemover);

        // botão salvar pedido
        JButton btnSalvar = Tema.botaoPrimario("Salvar Pedido");
        btnSalvar.setBounds(300, 390, 150, 30);
        btnSalvar.addActionListener(e -> salvarPedido());
        add(btnSalvar);

        // botão voltar
        JButton btnVoltar = Tema.botaoSecundario("Voltar");
        btnVoltar.setBounds(470, 390, 90, 30);
        btnVoltar.addActionListener(e -> {
            new TelaPrincipal();
            dispose();
        });
        add(btnVoltar);

        carregarClientes();
        carregarProdutos();
        setVisible(true);
    }

    private void carregarClientes() {
        try {
            ClienteDAO dao = new ClienteDAO();
            List<Cliente> lista = dao.listarTodos();
            for (Cliente c : lista) {
                cmbCliente.addItem(c);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage());
        }
    }

    private void carregarProdutos() {
        try {
            ProdutoDAO dao = new ProdutoDAO();
            List<Produto> lista = dao.listarTodos();
            for (Produto p : lista) {
                cmbProduto.addItem(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
        }
    }

    private void adicionarItem() {
        Produto produto = (Produto) cmbProduto.getSelectedItem();
        String qtdTexto = txtQuantidade.getText().trim();

        if (produto == null) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!");
            return;
        }

        if (qtdTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a quantidade!");
            return;
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(qtdTexto);
            if (quantidade <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida!");
            return;
        }

        if (quantidade > produto.getQuantidadeEstoque()) {
            JOptionPane.showMessageDialog(this,
                    "Estoque insuficiente! Disponível: " + produto.getQuantidadeEstoque());
            return;
        }

        // adiciona no builder
        pedidoBuilder.adicionarItem(produto, quantidade);

        // Multiplica o valor do produto pela quantidade e altera no lblTotal
        double subtotal = produto.getPreco() * quantidade;
        total += subtotal;
        lblTotal.setText(String.format("R$ %.2f", total).replace('.',','));

        // adiciona na tabela visual
        modeloTabela.addRow(new Object[]{
                produto.getNome(),
                quantidade,
                String.format("R$ %.2f", produto.getPreco()),
                String.format("R$ %.2f", subtotal)
        });

        txtQuantidade.setText("");
    }

    private void removerItem() {
        int linhaSelecionada = tabelaItens.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para remover!");
            return;
        }

        // Multiplica o valor do produto pela quantidade e altera no lblTotal
        double subtotalRemovido = pedidoBuilder.buscarItem(linhaSelecionada).getProduto().getPreco() * pedidoBuilder.buscarItem(linhaSelecionada).getQuantidade();
        total -= subtotalRemovido;

        modeloTabela.removeRow(linhaSelecionada);
        pedidoBuilder.removerItem(linhaSelecionada);
        lblTotal.setText(String.format("R$ %.2f", total).replace('.',','));
    }

    private void salvarPedido() {
        if (modeloTabela.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Adicione pelo menos um item!");
            return;
        }

        Cliente clienteSelecionado = (Cliente) cmbCliente.getSelectedItem();

        if (clienteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!");
            return;
        }

        try {
            // Uso do Builder Pattern para a criação do Objeto Pedido
            Pedido pedido = pedidoBuilder
                    .cliente(clienteSelecionado)
                    .status(Pedido.PENDENTE)
                    .build();

            PedidoDAO dao = new PedidoDAO();
            dao.salvar(pedido);

            JOptionPane.showMessageDialog(this, "Pedido cadastrado com sucesso!");
            new TelaPrincipal();
            dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar pedido: " + e.getMessage());
        }
    }
}
