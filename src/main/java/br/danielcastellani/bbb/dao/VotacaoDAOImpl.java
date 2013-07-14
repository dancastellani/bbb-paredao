/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.dao;

import br.danielcastellani.bbb.model.Votacao;
import java.util.Date;

/**
 *
 * @author DanCastellani
 */
public class VotacaoDAOImpl implements VotacaoDAO {

    //TODO: refatorar para pegar do banco
    public Votacao getVotacaoCorrente() {
        Votacao votacao = new Votacao();
        votacao.setFim(new Date(System.currentTimeMillis() + 1000 * 60 * 10));
        votacao.setInicio(new Date(System.currentTimeMillis() - 1000 * 60 * 10));
        votacao.setNomeDireita("Participante 1");
        votacao.setNomeEsquerda("Participante 2");
        return votacao;
    }
}
