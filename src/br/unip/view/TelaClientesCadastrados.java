package br.unip.view;



import br.unip.dao.ClienteDAO;
import br.unip.model.Cliente;
import br.unip.util.Tema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TelaClientesCadastrados extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaClientesCadastrados() {
        setTitle("Clientes Cadastrados");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        Tema.aplicarJanela(this);

        // título
        JLabel lblTitulo = Tema.labelTitulo("Clientes Cadastrados");
        lblTitulo.setBounds(20, 20, 460, 25);
        add(lblTitulo);

        // colunas da tabela
        String[] colunas = {"ID", "Nome", "Email", "Telefone", "Endereço"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede edição direta na tabela
            }
        };

        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(30);  // ID pequeno
        tabela.getColumnModel().getColumn(1).setPreferredWidth(120); // Nome
        tabela.getColumnModel().getColumn(2).setPreferredWidth(150); // Email
        tabela.getColumnModel().getColumn(3).setPreferredWidth(80);  // Telefone
        tabela.getColumnModel().getColumn(4).setPreferredWidth(200); // Endereço

        Tema.aplicarTabela(tabela);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 55, 550, 250);
        add(scroll);

        // botão excluir
        JButton btnExcluir = Tema.botaoSecundario("Excluir");
        btnExcluir.setBounds(20, 320, 100, 35);
        btnExcluir.setForeground(Color.RED);
        btnExcluir.addActionListener(e -> excluir());
        add(btnExcluir);

        // botão atualizar lista
        JButton btnAtualizar = Tema.botaoPrimario("Atualizar");
        btnAtualizar.setBounds(220, 320, 130, 35);
        btnAtualizar.addActionListener(e -> carregarClientes());
        add(btnAtualizar);

        // botão voltar
        JButton btnVoltar = Tema.botaoSecundario("Voltar");
        btnVoltar.setBounds(460, 320, 100, 35);
        btnVoltar.addActionListener(e -> {
            new TelaPrincipal();
            dispose();
        });
        add(btnVoltar);

        carregarClientes(); // carrega ao abrir a tela
        setVisible(true);
    }

    private void carregarClientes() {
        modelo.setRowCount(0); // limpa a tabela antes de carregar

        try {
            ClienteDAO dao = new ClienteDAO();
            List<Cliente> lista = dao.listarTodos();

            for (Cliente c : lista) {
                modelo.addRow(new Object[]{
                        c.getId(),
                        c.getNome(),
                        c.getEmail(),
                        c.getTelefone(),
                        c.getEndereco()
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage());
        }
    }
    private void excluir() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir!");
            return;
        }

        int id = (int) modelo.getValueAt(linhaSelecionada, 0);
        String nome = (String) modelo.getValueAt(linhaSelecionada, 1);

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja excluir o cliente " + nome + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                ClienteDAO dao = new ClienteDAO();
                dao.deletar(id);
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                carregarClientes(); // atualiza a tabela

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
            }
        }
    }

}
