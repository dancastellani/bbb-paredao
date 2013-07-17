/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.service;

import br.danielcastellani.bbb.dao.VotacaoDAO;
import br.danielcastellani.bbb.dao.VotacaoDAOImpl;
import br.danielcastellani.bbb.exception.ApplicationException;
import br.danielcastellani.bbb.model.Votos;
import br.danielcastellani.bbb.model.Votacao;
import java.sql.Date;
import java.sql.SQLException;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author DanCastellani
 */
public class VotacaoServiceTest {

    VotacaoService votacaoService;
    VotacaoDAO votacaoDAO;

    @BeforeMethod
    public void setUp() throws SQLException, ApplicationException {
        votacaoDAO = mock(VotacaoDAOImpl.class);
        when(votacaoDAO.getVotacaoCorrente()).thenReturn(new Votacao(1, "Esquerda", "Direita", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 1000)));

        votacaoService = new VotacaoService();
        votacaoService.setVotacaoDAO(votacaoDAO);
        votacaoService.inicializaVotacao();
    }

    @Test
    public void quandoVotarNaEsquerdaIncrementaVotosEsquerda() {
        votacaoService.votarEm(VotacaoService.Participantes.esquerda);
        int depois = votacaoService.getVotosEsquerda();
        assertEquals(depois, 1);
    }

    @Test
    public void quandoVotarNaDireitaIncrementaVotosDireita() {
        votacaoService.votarEm(VotacaoService.Participantes.direita);
        int depois = votacaoService.getVotosDireita();
        assertEquals(depois, 1);
    }

    @Test
    public void quandoSalvaVotacaoDeveReiniciarContagemCorrente() throws ApplicationException {
        votacaoService.salvaVotacaoAtual();
        assertEquals(votacaoService.getVotosDireita(), 0);
        assertEquals(votacaoService.getVotosEsquerda(), 0);
    }

    @Test
    public void aposVotacaoTerminarNaoDeveContabilizarVotosQuandoVotarNaEsquerda() throws SQLException, ApplicationException {
        when(votacaoDAO.getVotacaoCorrente()).thenReturn(new Votacao(1, "Esquerda", "Direita", new Date(System.currentTimeMillis() - 1000), new Date(System.currentTimeMillis() - 1000)));
        votacaoService.inicializaVotacao();

        votacaoService.votarEm(VotacaoService.Participantes.esquerda);
        int depois = votacaoService.getVotosEsquerda();
        assertEquals(depois, 0);
    }

    @Test
    public void aposVotacaoTerminarNaoDeveContabilizarVotosQuandoVotarNaDireita() throws SQLException, ApplicationException {
        when(votacaoDAO.getVotacaoCorrente()).thenReturn(new Votacao(1, "Esquerda", "Direita", new Date(System.currentTimeMillis() - 1000), new Date(System.currentTimeMillis() - 1000)));
        votacaoService.inicializaVotacao();

        votacaoService.votarEm(VotacaoService.Participantes.direita);
        int depois = votacaoService.getVotosDireita();
        assertEquals(depois, 0);
    }

    @Test
    public void quandoSalvarVotacaoMasNaoHouverNovosVotosNaoDeveSalvar() throws ApplicationException, SQLException {
        votacaoService.salvaVotacaoAtual();
        verify(votacaoDAO, times(0)).salvar(any(Votos.class));
    }

    @Test
    public void quandoSalvarVotacaoMasHouverNovosVotosParaDireitaDeveSalvar() throws ApplicationException, SQLException {
        votacaoService.votarEm(VotacaoService.Participantes.direita);
        votacaoService.salvaVotacaoAtual();
        verify(votacaoDAO).salvar(any(Votos.class));
    }

    @Test
    public void quandoSalvarVotacaoMasHouverNovosVotosParaEsquerdaDeveSalvar() throws SQLException, ApplicationException {
        votacaoService.votarEm(VotacaoService.Participantes.esquerda);
        votacaoService.salvaVotacaoAtual();
        verify(votacaoDAO).salvar(any(Votos.class));
    }
}
