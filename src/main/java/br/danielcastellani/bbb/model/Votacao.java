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
public class Votacao {
    
    private int id;
    private String nomeEsquerda;
    private String nomeDireita;
    private Date inicio;
    private Date fim;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the inicio
     */
    public Date getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the fim
     */
    public Date getFim() {
        return fim;
    }

    /**
     * @param fim the fim to set
     */
    public void setFim(Date fim) {
        this.fim = fim;
    }

    /**
     * @return the nomeEsquerda
     */
    public String getNomeEsquerda() {
        return nomeEsquerda;
    }

    /**
     * @param nomeEsquerda the nomeEsquerda to set
     */
    public void setNomeEsquerda(String nomeEsquerda) {
        this.nomeEsquerda = nomeEsquerda;
    }

    /**
     * @return the nomeDireita
     */
    public String getNomeDireita() {
        return nomeDireita;
    }

    /**
     * @param nomeDireita the nomeDireita to set
     */
    public void setNomeDireita(String nomeDireita) {
        this.nomeDireita = nomeDireita;
    }
}
