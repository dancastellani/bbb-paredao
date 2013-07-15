/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.model;

/**
 *
 * @author DanCastellani
 */
public class SituacaoVotacao {
    private int votosDireita;
    private int votosEsquerda;
    private long tempoRestante;

    /**
     * @return the votosDireita
     */
    public int getVotosDireita() {
        return votosDireita;
    }

    /**
     * @param votosDireita the votosDireita to set
     */
    public void setVotosDireita(int votosDireita) {
        this.votosDireita = votosDireita;
    }

    /**
     * @return the votosEsquerda
     */
    public int getVotosEsquerda() {
        return votosEsquerda;
    }

    /**
     * @param votosEsquerda the votosEsquerda to set
     */
    public void setVotosEsquerda(int votosEsquerda) {
        this.votosEsquerda = votosEsquerda;
    }

    /**
     * @return the tempoRestante
     */
    public long getTempoRestante() {
        return tempoRestante;
    }

    /**
     * @param tempoRestante the tempoRestante to set
     */
    public void setTempoRestante(long tempoRestante) {
        this.tempoRestante = tempoRestante;
    }
}
