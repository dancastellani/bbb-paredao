/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author DanCastellani
 */
public class ConexaoBD {

    static Connection connect() throws SQLException {
        Connection connection = null;
        try {
            Properties prop = new Properties();
            //load a properties file

            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db/database.properties"));
            String databaseUrl = prop.getProperty("database.url");
            String databaseUser = prop.getProperty("database.user");
            String databasePassword = prop.getProperty("database.password");

            connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            return connection;
        } catch (IOException ex) {
            throw new SQLException(ex);
        } catch (SQLException ex) {
            throw new SQLException("Erro ao conectar com credenciais.", ex);
        }
    }
}