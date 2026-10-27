package br.unip.view;

import br.unip.builder.ProdutoBuilder;
import br.unip.model.Produto;
import br.unip.dao.ProdutoDAO;
import br.unip.util.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TelaEstoque extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ProdutoDAO dao;

    public TelaEstoque() {
        setTitle("Estoque de Produtos");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        dao = new ProdutoDAO();

        // títuloJLabel(
        JLabel lblTitulo = Tema.labelTitulo("Estoque de Produtos");
        lblTitulo.setBounds(20, 15, 460, 25);
        add(lblTitulo);

        // tabela
        String[] colunas = {"ID", "Nome", "Preço", "Qtd. Estoque", "Situação"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                String situacao = (String) modeloTabela.getValueAt(row, 4);

                if (!isRowSelected(row)) {
                    switch (situacao) {
                        case "Normal"   -> comp.setBackground(new Color(200, 255, 200)); // verde
                        case "Baixo"    -> comp.setBackground(new Color(255, 255, 200)); // amarelo
                        case "Zerado"   -> comp.setBackground(new Color(255, 200, 200)); // vermelho
                        default         -> comp.setBackground(Color.WHITE);
                    }
                }
                return comp;
            }
        };

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(25);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(80);

        Tema.aplicarTabela(tabela);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 55, 540, 230);
        add(scroll);

        // legenda
        JLabel lblNormal = Tema.label("● Normal");
        lblNormal.setBounds(20, 295, 80, 20);
        lblNormal.setForeground(Tema.VERDE);
        add(lblNormal);

        JLabel lblBaixo = Tema.label("● Baixo");
        lblBaixo.setBounds(110, 295, 80, 20);
        lblBaixo.setForeground(Tema.AMARELO);
        add(lblBaixo);

        JLabel lblZerado = Tema.label("● Zerado");
        lblZerado.setBounds(200, 295, 80, 20);
        lblZerado.setForeground(Tema.VERMELHO);
        add(lblZerado);

        // botão adicionar produto
        JButton btnAdicionar = Tema.botaoPrimario("Novo Produto");
        btnAdicionar.setBounds(20, 340, 130, 35);
        btnAdicionar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnAdicionar.addActionListener(e -> abrirDialogoProduto(null));
        add(btnAdicionar);

        // botão editar estoque
        JButton btnEditar = Tema.botaoSecundario("Editar Estoque");
        btnEditar.setBounds(160, 340, 130, 35);

        btnEditar.addActionListener(e -> editarEstoque());
        add(btnEditar);

        // botão excluir
        JButton btnExcluir = Tema.botaoSecundario("Excluir");
        btnExcluir.setBounds(300, 340, 100, 35);
        btnExcluir.setForeground(Color.RED);
        btnExcluir.addActionListener(e -> excluir());
        add(btnExcluir);

        // botão voltar
        JButton btnVoltar = Tema.botaoSecundario("Voltar");
        btnVoltar.setBounds(460, 340, 100, 35);
        btnVoltar.addActionListener(e -> {
            new TelaPrincipal();
            dispose();
        });
        add(btnVoltar);

        carregarProdutos();
        setVisible(true);
    }

    private void carregarProdutos() {
        modeloTabela.setRowCount(0);

        try {
            List<Produto> lista = dao.listarTodos();

            for (Produto p : lista) {
                String situacao;
                if (p.getQuantidadeEstoque() == 0) {
                    situacao = "Zerado";
                }
                else if (p.getQuantidadeEstoque() <= 30) {
                    situacao = "Baixo";
                }
                else {
                    situacao = "Normal";
                }

                modeloTabela.addRow(new Object[]{
                        p.getId(),
                        p.getNome(),
                        String.format("R$ %.2f", p.getPreco()),
                        p.getQuantidadeEstoque(),
                        situacao
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
        }
    }

    private void editarEstoque() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!");
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nome = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        int qtdAtual = (int) modeloTabela.getValueAt(linhaSelecionada, 3);

        String input = JOptionPane.showInputDialog(
                this,
                "Produto: " + nome + "\nQuantidade atual: " + qtdAtual + "\nNova quantidade:",
                "Editar Estoque",
                JOptionPane.PLAIN_MESSAGE
        );

        if (input == null || input.trim().isEmpty()) return;

        try {
            int novaQtd = Integer.parseInt(input.trim());
            if (novaQtd < 0) throw new NumberFormatException();

            dao.atualizarEstoque(id, novaQtd);
            JOptionPane.showMessageDialog(this, "Estoque atualizado!");
            carregarProdutos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage());
        }
    }

    private void excluir() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!");
            return;
        }

        int id      = (int)    modeloTabela.getValueAt(linhaSelecionada, 0);
        String nome = (String) modeloTabela.getValueAt(linhaSelecionada, 1);

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja excluir o produto " + nome + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                dao.deletar(id);
                JOptionPane.showMessageDialog(this, "Produto excluído!");
                carregarProdutos();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
            }
        }
    }

    private void abrirDialogoProduto(Produto produto) {
        // Caixa flutuante para cadastrar novo produto

        JDialog dialog = new JDialog(this, "Novo Produto", true);
        dialog.setSize(300, 220);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(this);

        JLabel lblNome = Tema.label("Nome:");
        lblNome.setBounds(20, 20, 80, 25);
        dialog.add(lblNome);

        JTextField txtNome = Tema.campo();
        txtNome.setBounds(100, 20, 160, 25);
        dialog.add(txtNome);

        JLabel lblPreco = Tema.label("Preço:");
        lblPreco.setBounds(20, 60, 80, 25);
        dialog.add(lblPreco);

        JTextField txtPreco = Tema.campo();
        txtPreco.setBounds(100, 60, 160, 25);
        dialog.add(txtPreco);

        JLabel lblQtd = Tema.label("Estoque:");
        lblQtd.setBounds(20, 100, 80, 25);
        dialog.add(lblQtd);

        JTextField txtQtd = Tema.campo();
        txtQtd.setBounds(100, 100, 160, 25);
        dialog.add(txtQtd);

        JButton btnSalvar = Tema.botaoPrimario("Salvar");
        btnSalvar.setBounds(80, 150, 120, 30);
        btnSalvar.addActionListener(e -> {
            try {
                String nome  = txtNome.getText().trim();
                double preco = Double.parseDouble(txtPreco.getText().trim().replace(",", "."));
                int qtd = Integer.parseInt(txtQtd.getText().trim());

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Informe o nome!");
                    return;
                }

                Produto novo = new ProdutoBuilder().
                        nome(nome).
                        preco(preco).
                        quantidadeEstoque(qtd).
                        build();
                dao.salvar(novo);
                JOptionPane.showMessageDialog(dialog, "Produto salvo!");
                dialog.dispose();
                carregarProdutos();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Preço ou quantidade inválidos!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Erro ao salvar: " + ex.getMessage());
            }
        });
        dialog.add(btnSalvar);

        dialog.setVisible(true);
    }
}
