package br.unip.dao;

import br.unip.builder.ClienteBuilder;
import br.unip.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public void salvar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nome, email, telefone, endereco) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEndereco());
            stmt.executeUpdate();
        }
    }
    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nome = ?, email = ?, telefone = ?, endereco = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEndereco());
            stmt.setInt(5, cliente.getId());
            stmt.executeUpdate();
        }
    }
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Cliente buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE email = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }
        }
        return null;
    }
    public Cliente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }
        }
        return null;
    }

    public List<Cliente> listarTodos() throws SQLException {
        String sql = "SELECT * FROM clientes ORDER BY id";

        List<Cliente> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente cliente = new ClienteBuilder().
                id(rs.getInt("id")).
                nome(rs.getString("nome")).
                email(rs.getString("email")).
                telefone(rs.getString("telefone")).
                endereco(rs.getString("endereco")).
                build();
        return cliente;
    }
}
