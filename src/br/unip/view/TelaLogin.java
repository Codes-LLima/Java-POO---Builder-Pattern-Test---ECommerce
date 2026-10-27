package br.unip.view;

import br.unip.dao.UsuarioDAO;
import br.unip.model.Usuario;
import br.unip.util.Sessao;
import br.unip.util.Tema;

import javax.swing.*;
import javax.swing.event.TreeExpansionListener;
import java.awt.*;
import java.sql.SQLException;

public class TelaLogin extends JFrame {
    private JLabel lblTitulo;
    private JLabel lblEmail;
    private JTextField txtEmail;
    private JLabel lblSenhaUsuario;
    private JPasswordField txtCampoSenha;

    public TelaLogin() {
        setTitle("Login - Sistema Cadastro ");
        setSize(300, 240);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        Tema.aplicarJanela(this);

        JLabel logo = Tema.logo(70,70);
        logo.setBounds(180, -1, 150, 80);
        add(logo);


        // Texto central Login
        lblTitulo = Tema.labelTitulo("Login");
        lblTitulo.setBounds(20, 15, 80, 40);

        add(lblTitulo);

        // Label do Email

        lblEmail = Tema.label("Email:");
        lblEmail.setBounds(20, 65, 80, 25);
        add(lblEmail);

        // Campo inserir e-mail
        txtEmail = Tema.campo();
        txtEmail.setBounds(80, 65, 150, 25);
        add(txtEmail);

        // Label da Senha
        lblSenhaUsuario = Tema.label("Senha:");
        lblSenhaUsuario.setBounds(20, 100, 80, 25);
        add(lblSenhaUsuario);

        // Campo inserir senha
        txtCampoSenha = Tema.campoDeSenha();
        txtCampoSenha.setBounds(80, 100, 150, 25);
        add(txtCampoSenha);


        // Botão login
        JButton botaoLogin = Tema.botaoPrimario("Entrar");
        botaoLogin.setBounds(90, 150, 100, 30);
        add(botaoLogin);

        // Validação Login
        botaoLogin.addActionListener(e -> login());
    }

    private void login(){
        String email = txtEmail.getText();
        String senha = new String(txtCampoSenha.getPassword());

        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario usuario = dao.validarLogin(email, senha);
            if (usuario != null){
                Sessao.setUsuarioLogado(usuario);
                new TelaPrincipal();

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Email ou senha incorretos!");
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(this, "Não foi possível conectar no Banco de Dados: " + erro.getMessage());
        }

    }
}

