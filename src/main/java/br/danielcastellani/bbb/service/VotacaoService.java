/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.service;

import br.danielcastellani.bbb.dao.VotacaoDAO;
import br.danielcastellani.bbb.exception.ApplicationException;
import br.danielcastellani.bbb.model.ResumoVotos;
import br.danielcastellani.bbb.model.Votos;
import br.danielcastellani.bbb.model.SituacaoVotacao;
import br.danielcastellani.bbb.model.Votacao;
import java.sql.SQLException;
import java.util.List;

/**
 * Um contador thread safe para receber cada voto e armazenar ate que sejam
 * salvos.
 *
 * @author DanCastellani
 */
public class VotacaoService {

    private int votosDireita;
    private int votosEsquerda;
    private long finalDaVotacao; //para otimizar a comparacao a cada voto
    private VotacaoDAO votacaoDAO;
    private int idVotacaoCorrente;

    public void setVotacaoDAO(VotacaoDAO votacaoDAO) {
        this.votacaoDAO = votacaoDAO;
    }

    public void inicializaVotacao() throws ApplicationException {
        Votacao votacaoCorrente;
        try {
            votacaoCorrente = votacaoDAO.getVotacaoCorrente();
        } catch (SQLException ex) {
            throw new ApplicationException("Erro ao inicializar a vota��o.", ex);
        }
        this.finalDaVotacao = votacaoCorrente.getFim().getTime();
        this.idVotacaoCorrente = votacaoCorrente.getId();
        this.votosDireita = 0;
        this.votosEsquerda = 0;
    }

    /**
     * @return the votosDireita
     */
    public int getVotosDireita() {
        return votosDireita;
    }

    /**
     * @return the votosEsquerda
     */
    public int getVotosEsquerda() {
        return votosEsquerda;
    }

    public SituacaoVotacao getSituacaoVotacao() throws ApplicationException {
        try {
            return votacaoDAO.getSituacaoVotacao(idVotacaoCorrente);
        } catch (SQLException ex) {
            throw new ApplicationException("Erro ao recuperar a situa��o atual da vota��o.", ex);
        }
    }

    public Votacao getVotacaoCorrente() throws ApplicationException {
        try {
            return votacaoDAO.getVotacaoCorrente();
        } catch (SQLException ex) {
            throw new ApplicationException("Erro ao recuperar a vota��o corrente.", ex);
        }
    }

    public List<ResumoVotos> getVotosDeVotacaoAgrupadosHora(int idVotacao) throws ApplicationException {
        try {
            return votacaoDAO.getVotosDeVotacaoAgrupadosHora(idVotacao);
        } catch (SQLException ex) {
            throw new ApplicationException("Erro ao recuperar os votos agrupados por hora.", ex);
        }
    }

    public enum Participantes {

        esquerda, direita;
    }

    public synchronized void votarEm(Participantes participante) {
        if (System.currentTimeMillis() < finalDaVotacao) {
            if (Participantes.direita == participante) {
                votosDireita++;
            } else if (Participantes.esquerda == participante) {
                votosEsquerda++;
            }
//            System.out.println("====> Voto contabilizado!");
        }

//        System.out.print("Votos atuais:");
//        System.out.print("  Esquerda = " + votosEsquerda);
//        System.out.print("  Direita = " + votosDireita);
//        System.out.println("    Tempo restante: " + ((finalDaVotacao - System.currentTimeMillis()) / 1000) + " segundos");

    }

    public synchronized Votos getVotosContabilizadosParaReiniciarContagem() {
        Votos pacoteDeVotos = new Votos(votosEsquerda, votosDireita, System.currentTimeMillis(), idVotacaoCorrente);
        this.votosDireita = 0;
        this.votosEsquerda = 0;

        return pacoteDeVotos;
    }

    public void salvaVotacaoAtual() throws ApplicationException {
        Votos votos = getVotosContabilizadosParaReiniciarContagem();
        if (votos.getVotosParticipanteEsquerda() != 0 || votos.getVotosParticipanteDireita() != 0) {
            try {
                votacaoDAO.salvar(votos);
            } catch (SQLException ex) {
                throw new ApplicationException("Erro ao salvar os votos.", ex);
            }
        }
    }
}
