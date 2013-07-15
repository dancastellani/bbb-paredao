/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb;

import br.danielcastellani.bbb.ContextoAplicacao;
import java.sql.SQLException;
import org.testng.annotations.Test;

/**
 *
 * @author DanCastellani
 */
public class ContextoTest {

    @Test
    public void criaConexao() throws SQLException {
        ContextoAplicacao contextoAplicacao = new ContextoAplicacao();
        contextoAplicacao.contextInitialized(null);
    }
}
