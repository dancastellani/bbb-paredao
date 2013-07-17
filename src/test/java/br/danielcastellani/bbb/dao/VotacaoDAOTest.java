/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.dao;

import br.danielcastellani.bbb.model.ResumoVotos;
import br.danielcastellani.bbb.model.SituacaoVotacao;
import br.danielcastellani.bbb.model.Votacao;
import br.danielcastellani.bbb.model.Votos;
import java.sql.SQLException;
import java.util.List;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author DanCastellani
 */
public class VotacaoDAOTest {

    VotacaoDAO votacaoDAO;

    @BeforeTest
    public void setUp() {
        votacaoDAO = new VotacaoDAOImpl();
//        votacaoDAO.salvar(new Votos(10, 10, System.currentTimeMillis(), 0));
    }

//    @AfterTest
//    public void setUp() {
//        votacaoDAO = new VotacaoDAOImpl();
//        votacaoDAO.removeVotos(0);
//    }
    @Test
    public void recuperaVotacaoCorrenteComSucesso() throws SQLException {
        Votacao votacaoCorrente = votacaoDAO.getVotacaoCorrente();
        assertNotNull(votacaoCorrente);
    }

    @Test
    public void quandoRecuperaVotosNaoDeveSerNull() throws SQLException {
        List<ResumoVotos> votosDeVotacaoAgrupadosHora = votacaoDAO.getVotosDeVotacaoAgrupadosHora(1);
        assertNotNull(votosDeVotacaoAgrupadosHora);
    }

    @Test
    public void salvaVotosComSucesso() throws SQLException {
        Votos votos = new Votos(1, 1, 0, 1);
        votacaoDAO.salvar(votos);
        SituacaoVotacao situacaoVotacao = votacaoDAO.getSituacaoVotacao(1);

        assertTrue(situacaoVotacao.getVotosDireita() >= 1);
        assertTrue(situacaoVotacao.getVotosEsquerda() >= 1);
    }
}
