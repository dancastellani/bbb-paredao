/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.dao;

import br.danielcastellani.bbb.model.PacoteDeVotos;
import br.danielcastellani.bbb.model.Votacao;

/**
 *
 * @author DanCastellani
 */
public interface VotacaoDAO {
    
    public Votacao getVotacaoCorrente();

    public void salvar(PacoteDeVotos votos);
}
