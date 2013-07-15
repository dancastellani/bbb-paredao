/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.model;

/**
 *
 * @author DanCastellani
 */
public class ResumoVotos {

    private int votosEsquerda;
    private int votosDireita;
    private String data;
    private String hora;

    public int getTotal() {
        return votosEsquerda + votosDireita;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
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
}
