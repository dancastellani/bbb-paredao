/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.dao;

import br.danielcastellani.bbb.model.ResumoVotos;
import br.danielcastellani.bbb.model.SituacaoVotacao;
import br.danielcastellani.bbb.model.Votacao;
import br.danielcastellani.bbb.model.Votos;
import com.googlecode.flyway.core.Flyway;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author DanCastellani
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class VotacaoDAOTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private VotacaoDAO votacaoDAO;

//    @Before
//    public void setUp() {
//    }

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
    public void recuperaSituacaoVotacaoComSucesso() throws SQLException {
        SituacaoVotacao situacaoVotacao = votacaoDAO.getSituacaoVotacao(1);

        assertEquals(situacaoVotacao.getVotosEsquerda(), 300);
        assertEquals(situacaoVotacao.getVotosDireita(), 231);
    }

    @Test
    public void salvaVotosComSucesso() throws SQLException {
        Votos votos = new Votos(1, 1, 0, 1);
        votacaoDAO.salvar(votos);
        SituacaoVotacao situacaoVotacao = votacaoDAO.getSituacaoVotacao(1);

        assertTrue(situacaoVotacao.getVotosDireita() >= 1);
        assertTrue(situacaoVotacao.getVotosEsquerda() >= 1);
    }

    /**
     * @param votacaoDAO the votacaoDAO to set
     */
    public void setVotacaoDAO(VotacaoDAO votacaoDAO) {
        this.votacaoDAO = votacaoDAO;
    }

    /**
     * @param applicationContext the applicationContext to set
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
