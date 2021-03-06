/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.dao;

import br.danielcastellani.bbb.model.ResumoVotos;
import br.danielcastellani.bbb.model.Votos;
import br.danielcastellani.bbb.model.SituacaoVotacao;
import br.danielcastellani.bbb.model.Votacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 *
 * @author DanCastellani
 */
@Named(value = "VotacaoDAO")
public class VotacaoDAOImpl implements VotacaoDAO {

    @Inject
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Votacao getVotacaoCorrente() throws SQLException {
        final String query = "select * from votacao order by inicio desc, fim desc";

//        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
//            ps.setInt(1, 1);
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
//        } catch (SQLException ex) {
//            throw new SQLException("Erro ao executar consulta <" + query + ">.", ex);
//        }
    }

    @Override
    public void salvar(Votos votos) throws SQLException{
        final String query = "insert into votos (idvotacao, votosesquerda, votosdireita, horarecebimento) values (?, ?, ?, ?)";

//        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, votos.getIdVotacao());
            ps.setInt(2, votos.getVotosParticipanteEsquerda());
            ps.setInt(3, votos.getVotosParticipanteDireita());
            ps.setTimestamp(4, new Timestamp(votos.getHoraRecebimento().getTime()));

            ps.executeUpdate();

            connection.close();

//        } catch (SQLException ex) {
//            throw new SQLException("Erro ao salvar votos .", ex);
//        }
    }

    @Override
    public SituacaoVotacao getSituacaoVotacao(int idVotacao) throws SQLException{
        final String query = "select sum(votosEsquerda) as votosEsquerda, sum(votosDireita) as votosDireita, fim "
                + " from votos left outer join votacao on votos.idvotacao=votacao.id "
                + " where idvotacao = ? "
                + " group by fim";

//        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idVotacao);
            ResultSet rs = ps.executeQuery();
            rs.next();

            SituacaoVotacao situacaoVotacao = new SituacaoVotacao();
            situacaoVotacao.setVotosEsquerda(rs.getInt("votosEsquerda"));
            situacaoVotacao.setVotosDireita(rs.getInt("votosDireita"));
            long tempoRestante = rs.getTimestamp("fim").getTime() - System.currentTimeMillis();
            if (tempoRestante < 0) {
                tempoRestante = 0;
            }
            situacaoVotacao.setTempoRestante(tempoRestante);

            connection.close();

            return situacaoVotacao;
//        } catch (SQLException ex) {
//            throw new SQLException("Erro ao executar consulta <" + query + ">.", ex);
//        }
    }

    @Override
    public List<ResumoVotos> getVotosDeVotacaoAgrupadosHora(int idVotacao) throws SQLException{
        final String query = "select HOUR(horaRecebimento) as hora,"
                + "	sum(votosEsquerda) as votosEsquerda,"
                + " 	sum(votosDireita) as votosDireita "
                + "from votos "
                + "where idvotacao = ? "
                + "group by hora "
                + "order by hora";

//        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idVotacao);
            ResultSet rs = ps.executeQuery();

            List<ResumoVotos> votosPorHora = new LinkedList<ResumoVotos>();
            ResumoVotos votos;
            while (rs.next()) {
                votos = new ResumoVotos();
                votos.setVotosEsquerda(rs.getInt("votosEsquerda"));
                votos.setVotosDireita(rs.getInt("votosDireita"));
                votos.setHora(rs.getString("hora"));
                votosPorHora.add(votos);
            }

            connection.close();

            return votosPorHora;
//        } catch (SQLException ex) {
//            throw new  SQLException("Erro ao executar consulta <" + query + ">.", ex);
//        }
    }
}
