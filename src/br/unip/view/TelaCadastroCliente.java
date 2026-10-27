package br.unip.view;

import br.unip.builder.ClienteBuilder;
import br.unip.model.Cliente;
import br.unip.dao.ClienteDAO;
import br.unip.util.Tema;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TelaCadastroCliente extends JFrame{
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JTextField txtEndereco;

    public TelaCadastroCliente() {
        setTitle("Cadastro de Cliente");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        Tema.aplicarJanela(this);

        // título
        JLabel lblTitulo = Tema.labelTitulo("Cadastro de Cliente");
        lblTitulo.setBounds(20, 20, 460, 25);
        add(lblTitulo);

        // nome
        JLabel lblNome = Tema.label("Nome:");
        lblNome.setBounds(20, 60, 80, 25);
        add(lblNome);

        txtNome = Tema.campo();
        txtNome.setBounds(120, 60, 230, 25);
        add(txtNome);

        // email
        JLabel lblEmail = Tema.label("Email:");
        lblEmail.setBounds(20, 100, 80, 25);
        add(lblEmail);

        txtEmail = Tema.campo();
        txtEmail.setBounds(120, 100, 230, 25);
        add(txtEmail);

        // telefone
        JLabel lblTelefone = Tema.label("Telefone:");
        lblTelefone.setBounds(20, 140, 80, 25);
        add(lblTelefone);

        txtTelefone = Tema.campo();
        txtTelefone.setBounds(120, 140, 230, 25);
        add(txtTelefone);

        // endereço
        JLabel lblEndereco = Tema.label("Endereço:");
        lblEndereco.setBounds(20, 180, 80, 25);
        add(lblEndereco);

        txtEndereco = Tema.campo();
        txtEndereco.setBounds(120, 180, 230, 25);
        add(txtEndereco);

        // botão salvar
        JButton btnSalvar = Tema.botaoPrimario("Salvar");
        btnSalvar.setBounds(120, 240, 100, 35);
        btnSalvar.addActionListener(e -> salvar());
        add(btnSalvar);

        // botão voltar
        JButton btnVoltar = Tema.botaoSecundario("Voltar");
        btnVoltar.setBounds(240, 240, 100, 35);
        btnVoltar.addActionListener(e -> {
            new TelaPrincipal();
            dispose();
        });
        add(btnVoltar);

        setVisible(true);
    }

    private void salvar() {
        String nome     = txtNome.getText().trim();
        String email    = txtEmail.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String endereco = txtEndereco.getText().trim();

        // validação dos campos
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || endereco.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        try {
            Cliente cliente = new ClienteBuilder()
                    .nome(nome)
                    .email(email)
                    .telefone(telefone)
                    .endereco(endereco)
                    .build();

            ClienteDAO dao = new ClienteDAO();
            dao.salvar(cliente);

            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            limparCampos();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }

    private void limparCampos(){
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtNome.requestFocus();
    }
}
