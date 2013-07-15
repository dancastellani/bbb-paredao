/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.dao;

import static br.danielcastellani.bbb.dao.ConexaoBD.connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DanCastellani
 */
public class ConexaoBD {

    public static void executaInsertUpdateDelete(String query, String[] parametros) throws SQLException {
        Connection connection = null;
        try {
            connection = connect();
            PreparedStatement ps = connection.prepareStatement(query);
            for (int i = 0; i < parametros.length; i++) {
                //o indice das queries começa em 1 e nao em 0, como no java.
                ps.setString(i + 1, parametros[i]);
            }
            int i = ps.executeUpdate();
            connection.close();
            if (i == 0) {
                throw new SQLException("Não foram inseridos registros.");
            }
        } catch (SQLException ex) {
            throw new SQLException("Erro ao executar consulta <" + query + ">.", ex);
        }
    }

    public static ResultSet executaConsulta(String query, String[] parametros) throws SQLException {
        Connection connection = null;
        try {
            connection = connect();
            PreparedStatement ps = connection.prepareStatement(query);
            for (int i = 0; i < parametros.length; i++) {
                //o indice das queries comeca em 1 e nao em 0, como no java.
                ps.setString(i + 1, parametros[i]);
            }
            ResultSet rs = ps.executeQuery();
            connection.close();
            return rs;
        } catch (SQLException ex) {
            throw new SQLException("Erro ao executar consulta <" + query + ">.", ex);
        }
    }

    static Connection connect() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bbb", "postgres", "postgres");
            return connection;
        } catch (SQLException ex) {
            throw new SQLException("Erro ao conectar com credenciais.", ex);
        }
    }

    private static void commit(Connection connection) throws SQLException {
        try {
            connection.commit();
        } catch (SQLException ex) {
            throw new SQLException("Erro ao executar commit.", ex);
        } finally {
            connection.close();
        }
    }

    private static void rollback(Connection connection) throws SQLException {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new SQLException("Erro ao executar rollback.", ex);
        } finally {
            connection.close();
        }
    }
}