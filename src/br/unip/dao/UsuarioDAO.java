package br.unip.dao;
import br.unip.model.Usuario;
import java.sql.*;

public class UsuarioDAO {

    public Usuario validarLogin(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                return usuario; // retorna o usuário se encontrou
            }
        }
        return null; // retorna null se não encontrou
    }
}
