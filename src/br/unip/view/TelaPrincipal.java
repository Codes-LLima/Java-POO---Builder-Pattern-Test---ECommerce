package br.unip.view;

import br.unip.util.Tema;
import br.unip.util.Sessao;
import javax.swing.*;
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema de Cadastro de Pedidos");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null); // centraliza na tela
        Tema.aplicarJanela(this);

        // logo
        JLabel logo = Tema.logo(104,104);
        logo.setBounds(380, 10, 150, 80);
        add(logo);

        // boas vindas
        JLabel lblBemVindo = Tema.labelTitulo("Bem vindo, " + Sessao.getUsuario().getNome() + "!");
        lblBemVindo.setBounds(20, 20, 460, 25);
        add(lblBemVindo);

        JLabel lblSubtitulo = Tema.labelSubtitulo("O que deseja fazer?");
        lblSubtitulo.setBounds(20, 50, 460, 20);
        add(lblSubtitulo);

        // botão Cadastrar Pedido
        JButton btnPedido = Tema.botaoPrimario("Cadastrar Pedido");
        btnPedido.setBounds(150, 100, 200, 40);
        btnPedido.addActionListener(e -> {
            new TelaCadastroPedido();
            dispose();
        });
        add(btnPedido);

        // botão Cadastrar Cliente
        JButton btnCliente = Tema.botaoPrimario("Cadastrar Cliente");
        btnCliente.setBounds(150, 155, 200, 40);
        btnCliente.addActionListener(e -> {
            new TelaCadastroCliente();
            dispose();
        });
        add(btnCliente);

        // botão Clientes Cadastrados
        JButton btnClientesCAD = Tema.botaoPrimario("Clientes Cadastrados");
        btnClientesCAD.setBounds(150,210, 200,40);
        btnClientesCAD.addActionListener(e ->{
            new TelaClientesCadastrados();
            dispose();
        });
        add(btnClientesCAD);

        // botão Estoque
        JButton btnEstoque =Tema.botaoPrimario("Verificar Estoque");
        btnEstoque.setBounds(150, 265, 200, 40);
        btnEstoque.addActionListener(e -> {
            new TelaEstoque();
            dispose();
        });
        add(btnEstoque);

        // botão Entregas
        JButton btnEntrega = Tema.botaoPrimario("Validar Entregas");
        btnEntrega.setBounds(150, 320, 200, 40);
        btnEntrega.addActionListener(e -> {
            new TelaEntrega();
            dispose();
        });
        add(btnEntrega);

        // botão Sair
        JButton btnSair = Tema.botaoSecundario("Sair");
        btnSair.setBounds(150, 375, 200, 30);
        btnSair.setForeground(Color.RED);
        btnSair.addActionListener(e -> {
            Sessao.encerrar();
            new TelaLogin();
            dispose();
        });
        add(btnSair);

        setVisible(true);
    }
}
