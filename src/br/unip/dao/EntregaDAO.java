package br.unip.dao;

import br.unip.builder.EntregaBuilder;
import br.unip.model.Entrega;
import br.unip.model.Pedido;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {
    public void salvar(Entrega entrega) throws SQLException {
        String sql = "INSERT INTO entregas (id_pedido, status, data_previsao) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entrega.getPedido().getId());
            stmt.setString(2, entrega.getStatus());
            stmt.setDate(3, Date.valueOf(entrega.getDataPrevisao()));
            stmt.executeUpdate();
        }
    }
    public void atualizarStatus(int idEntrega, String novoStatus) throws SQLException {
        String sql = "UPDATE entregas SET status = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setInt(2, idEntrega);
            stmt.executeUpdate();
        }
    }

    public void confirmarEntrega(int idEntrega) throws SQLException {
        String sql = "UPDATE entregas SET status = ?, data_entrega = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,Entrega.ENTREGUE);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setInt(3, idEntrega);
            stmt.executeUpdate();
        }
    }

    public List<Entrega> listarTodas() throws SQLException {
        String sql = "SELECT * FROM entregas";

        List<Entrega> lista = new ArrayList<>();

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                // Uso do Builder
                Entrega entrega = new EntregaBuilder().
                        id(rs.getInt("id")).
                        pedidoId(rs.getInt("id_pedido")).
                        status(rs.getString("status")).
                        // Verifica se as colunas de data estão vazias, caso ss preenche como null
                        dataPrevisao(rs.getDate("data_previsao") != null ?
                                rs.getDate("data_previsao").toLocalDate() : null).
                        dataEntrega(rs.getDate("data_entrega") != null ?
                                rs.getDate("data_entrega").toLocalDate() : null).
                        build();

                lista.add(entrega);
            }
        }
        return lista;
    }
}
