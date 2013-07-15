/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.dao;

import static br.danielcastellani.bbb.dao.ConexaoBD.connect;
import br.danielcastellani.bbb.model.PacoteDeVotos;
import br.danielcastellani.bbb.model.Votacao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DanCastellani
 */
public class VotacaoDAOImpl implements VotacaoDAO {

    public Votacao getVotacaoCorrente() {
        String query = "select * from votacao where id = ?";
        Connection connection = null;
        try {
            connection = connect();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            rs.next();
            
            Votacao votacao = new Votacao();
            votacao.setId(rs.getInt("id"));
            votacao.setFim(rs.getDate("fim"));
            votacao.setInicio(rs.getDate("inicio"));
            votacao.setNomeDireita(rs.getString("nomeDireita"));
            votacao.setNomeEsquerda(rs.getString("nomeEsquerda"));

            connection.close();

            return votacao;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao executar consulta <" + query + ">.", ex);
        }
    }

//    public void salvar(PacoteDeVotos votos) {
//                String query = "select * from votacao where id = ?";
//        Connection connection = null;
//        try {
//            connection = connect();
//            PreparedStatement ps = connection.prepareStatement(query);
//            ps.setInt(1, 1);
//            ResultSet rs = ps.executeQuery();
//            rs.next();
//            
//            Votacao votacao = new Votacao();
//            votacao.setId(rs.getInt("id"));
//            votacao.setFim(rs.getDate("fim"));
//            votacao.setInicio(rs.getDate("inicio"));
//            votacao.setNomeDireita(rs.getString("nomeDireita"));
//            votacao.setNomeEsquerda(rs.getString("nomeEsquerda"));
//
//            connection.close();
//
//            return votacao;
//        } catch (SQLException ex) {
//            throw new RuntimeException("Erro ao executar consulta <" + query + ">.", ex);
//        }
//    }
}
