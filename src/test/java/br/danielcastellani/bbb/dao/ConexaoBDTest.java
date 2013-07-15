/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.dao;

import java.sql.SQLException;
import org.testng.annotations.Test;

/**
 *
 * @author DanCastellani
 */
public class ConexaoBDTest {

//    @Test
    public void criaConexao() throws SQLException {
        ConexaoBD.connect();
    }
}
