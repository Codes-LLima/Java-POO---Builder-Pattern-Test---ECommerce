package br.unip.dao;

import br.unip.builder.ProdutoBuilder;
import br.unip.model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    // salva um novo produto
    public void salvar(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos (nome, preco, quantidade_estoque) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidadeEstoque());
            stmt.executeUpdate();
        }
    }

    // atualiza um produto existente
    public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE produtos SET nome = ?, preco = ?, quantidade_estoque = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidadeEstoque());
            stmt.setInt(4, produto.getId());
            stmt.executeUpdate();
        }
    }

    // atualiza só o estoque
    public void atualizarEstoque(int idProduto, int novaQuantidade) throws SQLException {
        String sql = "UPDATE produtos SET quantidade_estoque = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, idProduto);
            stmt.executeUpdate();
        }
    }

    // deleta um produto
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }


    // lista todos os produtos
    public List<Produto> listarTodos() throws SQLException {
        String sql = "SELECT * FROM produtos";

        List<Produto> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }


    // converte ResultSet em objeto Produto
    private Produto mapear(ResultSet rs) throws SQLException {
        Produto produto = new ProdutoBuilder().
                id(rs.getInt("id")).
                nome(rs.getString("nome")).
                preco(rs.getDouble("preco")).
                quantidadeEstoque(rs.getInt("quantidade_estoque")).
                build();
        return produto;
    }
}
