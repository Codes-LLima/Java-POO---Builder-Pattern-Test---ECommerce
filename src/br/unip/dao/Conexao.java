package br.unip.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    // Isso aqui deve ser alterado para ser aplicado em cada servidor sql
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_pedidos";
    private static final String USUARIO = "root";
    private static final String SENHA = "Lucas@123";

    public static Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL,USUARIO,SENHA);
        } catch (ClassNotFoundException e){
            throw new SQLException("Driver não encontrado: " + e.getMessage());
        }
    }
}
