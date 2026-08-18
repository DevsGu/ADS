package com.example.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {

    private static final String url = "jdbc:postgresql://localhost:5432/loja_jdbc";
    private static final String usuario = "postgres";
    private static final String senha = "1234";

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection( url , usuario , senha);
        }catch(SQLException e){
            throw new RuntimeException("Erro ao Obter conexão com Banco de Dados");
        }
    }

}
