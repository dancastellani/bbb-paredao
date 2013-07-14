/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.model;

import java.util.Date;

/**
 *
 * @author DanCastellani
 */
public class PacoteDeVotos {

    private int votosParticipanteEsquerda = 0;
    private int votosParticipanteDireita = 0;
    private Date horaRecebimento;
    private int idVotacao;

    public PacoteDeVotos(int votosEsquerda, int votosDireita, long currentTimeMillis) {
        this.votosParticipanteEsquerda = votosEsquerda;
        this.votosParticipanteDireita = votosDireita;
        this.horaRecebimento = new Date(currentTimeMillis);
    }

    /**
     * @return the horaContagem
     */
    public Date getHoraRecebimento() {
        return horaRecebimento;
    }

    /**
     * @param horaContagem the horaContagem to set
     */
    public void setHoraRecebimento(Date horaContagem) {
        this.horaRecebimento = horaContagem;
    }
    /**
     * @return the idVotacao
     */
    public int getIdVotacao() {
        return idVotacao;
    }

    /**
     * @param idVotacao the idVotacao to set
     */
    public void setIdVotacao(int idVotacao) {
        this.idVotacao = idVotacao;
    }

    /**
     * @return the votosParticipanteEsquerda
     */
    public int getVotosParticipanteEsquerda() {
        return votosParticipanteEsquerda;
    }

    /**
     * @param votosParticipanteEsquerda the votosParticipanteEsquerda to set
     */
    public void setVotosParticipanteEsquerda(int votosParticipanteEsquerda) {
        this.votosParticipanteEsquerda = votosParticipanteEsquerda;
    }

    /**
     * @return the votosParticipanteDireita
     */
    public int getVotosParticipanteDireita() {
        return votosParticipanteDireita;
    }

    /**
     * @param votosParticipanteDireita the votosParticipanteDireita to set
     */
    public void setVotosParticipanteDireita(int votosParticipanteDireita) {
        this.votosParticipanteDireita = votosParticipanteDireita;
    }
}
