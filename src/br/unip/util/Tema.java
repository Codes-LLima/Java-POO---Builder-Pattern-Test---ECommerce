package br.unip.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Tema {
    // ─── CORES ───────────────────────────────────────
    public static final Color FUNDO         = new Color(245, 245, 245);
    public static final Color FUNDO_CARD    = Color.WHITE;
    public static final Color TEXTO_TITULO  = new Color(15, 15, 15);
    public static final Color TEXTO_NORMAL  = new Color(60, 60, 60);
    public static final Color TEXTO_FRACO   = new Color(80, 80, 80);
    public static final Color BORDA         = new Color(220, 220, 220);
    public static final Color BOTAO_TEXTO   = Color.WHITE;
    public static final Color VERDE         = new Color(34, 197, 94);
    public static final Color AMARELO       = new Color(234, 179, 8);
    public static final Color VERMELHO      = new Color(220, 38, 38);
    public static final Color AZUL          = new Color(59, 130, 246);

    // ─── FONTES ──────────────────────────────────────
    public static final Font FONTE_TITULO   = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONTE_SUBTITULO= new Font("Segoe UI", Font.PLAIN, 15);
    public static final Font FONTE_NORMAL   = new Font("Segoe UI", Font.PLAIN, 13);


    // ─── LOGO ──────────────────────────────────────
    public static JLabel logo(int largura, int altura) {
        ImageIcon original = new ImageIcon(Tema.class.getResource("/resources/javalogo.png"));
        Image redimensionada = original.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(redimensionada));
    }

    // ─── JANELA ──────────────────────────────────────
    public static void aplicarJanela(JFrame frame) {
        frame.getContentPane().setBackground(FUNDO);
        frame.setFont(FONTE_NORMAL);
    }

    // ─── LABEL TÍTULO ────────────────────────────────
    public static JLabel labelTitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FONTE_TITULO);
        label.setForeground(TEXTO_TITULO);
        return label;
    }

    // ─── LABEL SUBTÍTULO ─────────────────────────────
    public static JLabel labelSubtitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FONTE_SUBTITULO);
        label.setForeground(TEXTO_TITULO);
        return label;
    }

    // ─── LABEL NORMAL ────────────────────────────────
    public static JLabel label(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FONTE_NORMAL);
        label.setForeground(TEXTO_NORMAL);
        return label;
    }

    // ─── CAMPO DE TEXTO ──────────────────────────────
    public static JTextField campo() {
        JTextField campo = new JTextField();
        campo.setFont(FONTE_NORMAL);
        campo.setForeground(TEXTO_NORMAL);
        campo.setBackground(FUNDO_CARD);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA, 1),
                new EmptyBorder(4, 8, 4, 8)
        ));
        return campo;
    }

    // ─── CAMPO DE SENHA ──────────────────────────────
    public static JPasswordField campoDeSenha() {
        JPasswordField campo = new JPasswordField();
        campo.setFont(FONTE_NORMAL);
        campo.setBackground(FUNDO_CARD);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA, 1),
                new EmptyBorder(4, 8, 4, 8)
        ));
        return campo;
    }

    // ─── BOTÃO PRIMÁRIO ──────────────────────────────
    public static JButton botaoPrimario(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 13));
        botao.setForeground(BOTAO_TEXTO);
        botao.setBackground(TEXTO_TITULO);
        botao.setBorder(new EmptyBorder(10, 24, 10, 24));
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setOpaque(true);
        return botao;
    }


    // ─── BOTÃO SECUNDÁRIO (outline) ──────────────────
    public static JButton botaoSecundario(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(FONTE_NORMAL);
        botao.setForeground(TEXTO_TITULO);
        botao.setBackground(FUNDO_CARD);
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA, 1),
                new EmptyBorder(8, 20, 8, 20)
        ));
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setOpaque(true);
        return botao;
    }

    // ─── TABELA ──────────────────────────────────────
    public static void aplicarTabela(JTable tabela) {
        tabela.setFont(FONTE_NORMAL);
        tabela.setRowHeight(32);
        tabela.setShowGrid(false);
        tabela.setIntercellSpacing(new Dimension(0, 0));
        tabela.setBackground(FUNDO_CARD);
        tabela.setForeground(TEXTO_NORMAL);
        tabela.setSelectionBackground(new Color(220, 38, 38, 40));
        tabela.setSelectionForeground(TEXTO_TITULO);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabela.getTableHeader().setBackground(FUNDO);
        tabela.getTableHeader().setForeground(TEXTO_FRACO);
        tabela.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDA));
    }

    // ─── SEPARADOR ───────────────────────────────────
    public static JSeparator separador() {
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDA);
        return sep;
    }

    // ─── COMBOBOX ────────────────────────────────────
    public static void aplicarCombo(JComboBox<?> combo) {
        combo.setFont(FONTE_NORMAL);
        combo.setBackground(FUNDO_CARD);
        combo.setForeground(TEXTO_NORMAL);
    }
}

