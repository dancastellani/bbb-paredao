/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.dao;

import br.danielcastellani.bbb.dao.VotacaoDAOImpl;
import br.danielcastellani.bbb.model.Votacao;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author DanCastellani
 */
public class VotacaoDAOTest {

//    @Test
    public void recuperaVotacaoCorrente() {
        VotacaoDAOImpl votacaoDAOImpl = new VotacaoDAOImpl();
        Votacao votacaoCorrente = votacaoDAOImpl.getVotacaoCorrente();

        assertNotNull(votacaoCorrente);
    }
}
