package br.unip.view;

import br.unip.dao.EntregaDAO;
import br.unip.model.Entrega;

import br.unip.util.Tema;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TelaEntrega extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private EntregaDAO dao;

    public TelaEntrega() {
        setTitle("Validar Entregas");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        Tema.aplicarJanela(this);
        dao = new EntregaDAO();

        // título
        JLabel lblTitulo = Tema.labelTitulo("Gerenciar Entregas");
        lblTitulo.setBounds(20, 15, 250, 25);
        add(lblTitulo);

        // tabela
        String[] colunas = {"ID", "Pedido", "Status", "Previsão", "Data Entrega"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela) {
            // colore a linha de acordo com o status
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                String status = (String) modeloTabela.getValueAt(row, 2);

                if (!isRowSelected(row)) {
                    switch (status) {
                        case Entrega.PENDENTE  -> comp.setBackground(new Color(255, 255, 200)); // amarelo
                        case Entrega.EM_ROTA   -> comp.setBackground(new Color(200, 220, 255)); // azul
                        case Entrega.ENTREGUE  -> comp.setBackground(new Color(200, 255, 200)); // verde
                        case Entrega.CANCELADO -> comp.setBackground(new Color(255, 200, 200)); // vermelho
                        default                -> comp.setBackground(Color.WHITE);
                    }
                }
                return comp;
            }
        };

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(25);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(30);  // ID
        tabela.getColumnModel().getColumn(1).setPreferredWidth(80);  // Pedido
        tabela.getColumnModel().getColumn(2).setPreferredWidth(100); // Status
        tabela.getColumnModel().getColumn(3).setPreferredWidth(100); // Previsão
        tabela.getColumnModel().getColumn(4).setPreferredWidth(120); // Data Entrega

        Tema.aplicarTabela(tabela);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 55, 640, 250);
        add(scroll);

        // legenda
        JLabel lblPendente = Tema.label("● Pendente");
        lblPendente.setBounds(20, 315, 90, 20);
        lblPendente.setForeground(new Color(180, 150, 0));
        add(lblPendente);

        JLabel lblEmRota = Tema.label("● Em Rota");
        lblEmRota.setBounds(115, 315, 90, 20);
        lblEmRota.setForeground(new Color(0, 80, 180));
        add(lblEmRota);

        JLabel lblEntregue = Tema.label("● Entregue");
        lblEntregue.setBounds(210, 315, 90, 20);
        lblEntregue.setForeground(new Color(0, 140, 0));
        add(lblEntregue);

        JLabel lblCancelado = Tema.label("● Cancelado");
        lblCancelado.setBounds(305, 315, 90, 20);
        lblCancelado.setForeground(new Color(180, 0, 0));
        add(lblCancelado);

        // botão Em Rota
        JButton btnEmRota = Tema.botaoSecundario("Marcar Em Rota");
        btnEmRota.setBounds(20, 360, 150, 35);
        btnEmRota.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnEmRota.setForeground(Tema.AZUL);
        btnEmRota.addActionListener(e -> atualizarStatus(Entrega.EM_ROTA));
        add(btnEmRota);

        // botão Confirmar Entrega
        JButton btnConfirmar = Tema.botaoSecundario("Confirmar Entrega");
        btnConfirmar.setBounds(180, 360, 160, 35);
        btnConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnConfirmar.setForeground(Tema.VERDE);
        btnConfirmar.addActionListener(e -> confirmarEntrega());
        add(btnConfirmar);

        // botão Cancelar
        JButton btnCancelar = Tema.botaoSecundario("Cancelar Entrega");
        btnCancelar.setBounds(350, 360, 150, 35);
        btnCancelar.setForeground(Tema.VERMELHO);
        btnCancelar.addActionListener(e -> atualizarStatus(Entrega.CANCELADO));
        add(btnCancelar);

        // botão Voltar
        JButton btnVoltar = Tema.botaoSecundario("Voltar");
        btnVoltar.setBounds(570, 360, 90, 35);
        btnVoltar.addActionListener(e -> {
            new TelaPrincipal();
            dispose();
        });
        add(btnVoltar);

        carregarEntregas();
        setVisible(true);
    }

    private void carregarEntregas() {
        modeloTabela.setRowCount(0);

        try {
            List<Entrega> lista = dao.listarTodas();

            for (Entrega e : lista) {
                modeloTabela.addRow(new Object[]{
                        e.getId(),
                        "Pedido #" + e.getPedido().getId(),
                        e.getStatus(),
                        e.getDataPrevisao() != null ? e.getDataPrevisao().toString() : "-",
                        e.getDataEntrega()  != null ? e.getDataEntrega().toString()  : "-"
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar entregas: " + e.getMessage());
        }
    }

    private void atualizarStatus(String novoStatus) {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma entrega!");
            return;
        }

        int id            = (int)    modeloTabela.getValueAt(linhaSelecionada, 0);
        String statusAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 2);

        if (statusAtual.equals(Entrega.ENTREGUE)) {
            JOptionPane.showMessageDialog(this, "Essa entrega já foi concluída!");
            return;
        }

        if (statusAtual.equals(Entrega.CANCELADO)) {
            JOptionPane.showMessageDialog(this, "Essa entrega foi cancelada!");
            return;
        }

        try {
            dao.atualizarStatus(id, novoStatus);
            JOptionPane.showMessageDialog(this, "Status atualizado!");
            carregarEntregas();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + e.getMessage());
        }
    }

    private void confirmarEntrega() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma entrega!");
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        String statusAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 2);

        if (statusAtual.equals(Entrega.ENTREGUE)) {
            JOptionPane.showMessageDialog(this, "Essa entrega já foi concluída!");
            return;
        }

        if (statusAtual.equals(Entrega.CANCELADO)) {
            JOptionPane.showMessageDialog(this, "Não é possível confirmar uma entrega cancelada!");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Confirmar entrega do Pedido #" + id + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                dao.confirmarEntrega(id);
                JOptionPane.showMessageDialog(this, "Entrega confirmada!");
                carregarEntregas();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao confirmar: " + e.getMessage());
            }
        }
    }
}
