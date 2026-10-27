package br.unip.dao;

import br.unip.model.Entrega;
import br.unip.model.Pedido;
import br.unip.model.ItemPedido;
import java.sql.*;
import java.time.LocalDate;

public class PedidoDAO {
    public void salvar(Pedido pedido) throws SQLException {
        String sqlPedido = "INSERT INTO pedidos (id_cliente, status, data_pedido) VALUES (?, ?, ?)";
        String sqlItem   = "INSERT INTO itens_pedido (id_pedido, id_produto, quantidade) VALUES (?, ?, ?)";
        String sqlEstoque = "UPDATE produtos SET quantidade_estoque = quantidade_estoque - ? WHERE id = ?";
        String sqlEntrega = "INSERT INTO entregas (id_pedido, status, data_previsao) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false); // tudo ou nada
            try {
                // salva o pedido
                PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
                stmtPedido.setInt(1, pedido.getCliente().getId());
                stmtPedido.setString(2, pedido.getStatus());
                stmtPedido.setDate(3, Date.valueOf(LocalDate.now()));
                stmtPedido.executeUpdate();

                // pega o ID gerado do pedido
                ResultSet rs = stmtPedido.getGeneratedKeys();
                int idPedido = rs.next() ? rs.getInt(1) : 0;

                // salva cada item
                PreparedStatement stmtItem = conn.prepareStatement(sqlItem);
                PreparedStatement stmtEstoque = conn.prepareStatement(sqlEstoque);
                for (ItemPedido item : pedido.getItens()) {
                    stmtItem.setInt(1, idPedido);
                    stmtItem.setInt(2, item.getProduto().getId());
                    stmtItem.setInt(3, item.getQuantidade());
                    stmtItem.executeUpdate();

                    // abate do estoque
                    stmtEstoque.setInt(1, item.getQuantidade());
                    stmtEstoque.setInt(2, item.getProduto().getId());
                    stmtEstoque.executeUpdate();
                }
                // cria a entrega automaticamente
                PreparedStatement stmtEntrega = conn.prepareStatement(sqlEntrega);
                stmtEntrega.setInt(1, idPedido);
                stmtEntrega.setString(2, Entrega.PENDENTE);
                stmtEntrega.setDate(3, Date.valueOf(LocalDate.now().plusDays(5))); // Adiciona 5 dias de previsão de entrega.
                stmtEntrega.executeUpdate();

                conn.commit(); // confirma tudo junto
            } catch (SQLException e) {
                conn.rollback(); // desfaz tudo se qualquer coisa falhar
                throw e;
            }
        }
    }
}
