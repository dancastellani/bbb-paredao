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
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author DanCastellani
 */
public class VotacaoServiceTest {

    VotacaoService votacaoService;
    VotacaoDAO votacaoDAO;

    @Before
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

    @Test
    public void quandoInicializaAVotacaoMasOcorreSQLExceptionDeveLancarApplicationException() throws SQLException {
        when(votacaoDAO.getVotacaoCorrente()).thenThrow(new SQLException());
        try {
            votacaoService.inicializaVotacao();
            fail("Deveria ter lançado ApplicationException.");

        } catch (ApplicationException ex) {
            assertEquals(ex.getMessage(), "Erro ao inicializar a votação.");
        }
    }

    @Test
    public void quandoRecuperaASituacaoVotacaoMasOcorreSQLExceptionDeveLancarApplicationException() throws SQLException {
        when(votacaoDAO.getSituacaoVotacao(anyInt())).thenThrow(new SQLException());
        try {
            votacaoService.getSituacaoVotacao();
            fail("Deveria ter lançado ApplicationException.");

        } catch (ApplicationException ex) {
            assertEquals(ex.getMessage(), "Erro ao recuperar a situação atual da votação.");
        }
    }

    @Test
    public void quandoRecuperaAVotacaoCorrenteMasOcorreSQLExceptionDeveLancarApplicationException() throws SQLException {
        when(votacaoDAO.getVotacaoCorrente()).thenThrow(new SQLException());
        try {
            votacaoService.getVotacaoCorrente();
            fail("Deveria ter lançado ApplicationException.");

        } catch (ApplicationException ex) {
            assertEquals(ex.getMessage(), "Erro ao recuperar a votação corrente.");
        }
    }

    @Test
    public void quandoRecuperaVotosAgrupadosPorHoraMasOcorreSQLExceptionDeveLancarApplicationException() throws SQLException {
        when(votacaoDAO.getVotosDeVotacaoAgrupadosHora(anyInt())).thenThrow(new SQLException());
        try {
            votacaoService.getVotosDeVotacaoAgrupadosHora(anyInt());
            fail("Deveria ter lançado ApplicationException.");

        } catch (ApplicationException ex) {
            assertEquals(ex.getMessage(), "Erro ao recuperar os votos agrupados por hora.");
        }
    }

    @Test
    public void quandoSalvaVotacaoAtualMasOcorreSQLExceptionDeveLancarApplicationException() throws SQLException {
        doThrow(new SQLException()).when(votacaoDAO).salvar(any(Votos.class));
        votacaoService = spy(votacaoService);
        when(votacaoService.getVotosContabilizadosParaReiniciarContagem()).thenReturn(new Votos(1, 1, 1, 1));
        try {
            votacaoService.salvaVotacaoAtual();
            fail("Deveria ter lançado ApplicationException.");

        } catch (ApplicationException ex) {
            assertEquals(ex.getMessage(), "Erro ao salvar os votos.");
        }
    }
}
