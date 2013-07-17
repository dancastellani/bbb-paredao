/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.dao;

import br.danielcastellani.bbb.model.ResumoVotos;
import br.danielcastellani.bbb.model.Votos;
import br.danielcastellani.bbb.model.SituacaoVotacao;
import br.danielcastellani.bbb.model.Votacao;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author DanCastellani
 */
public interface VotacaoDAO {

    public Votacao getVotacaoCorrente() throws SQLException;

    public void salvar(Votos votos) throws SQLException;

    public SituacaoVotacao getSituacaoVotacao(int idVotacaoCorrente) throws SQLException;

    public List<ResumoVotos> getVotosDeVotacaoAgrupadosHora(int idVotacao) throws SQLException;
}
