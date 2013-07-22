/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.service;

import br.danielcastellani.bbb.dao.VotacaoDAO;
import br.danielcastellani.bbb.model.ResumoVotos;
import br.danielcastellani.bbb.model.Votos;
import br.danielcastellani.bbb.model.SituacaoVotacao;
import br.danielcastellani.bbb.model.Votacao;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Um contador thread safe para receber cada voto e armazenar ate que sejam
 * salvos.
 *
 * @author DanCastellani
 */
@Named
public class VotacaoService {

    private int votosDireita;
    private int votosEsquerda;
    private long finalDaVotacao; //para otimizar a comparacao a cada voto
    @Inject
    private VotacaoDAO votacaoDAO;
    private int idVotacaoCorrente;

    public void setVotacaoDAO(VotacaoDAO votacaoDAO) {
        this.votacaoDAO = votacaoDAO;
    }

    public void inicializaVotacao() {
        Votacao votacaoCorrente = votacaoDAO.getVotacaoCorrente();
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

    public SituacaoVotacao getSituacaoVotacao() {
        return votacaoDAO.getSituacaoVotacao(idVotacaoCorrente);
    }

    public Votacao getVotacaoCorrente() {
        return votacaoDAO.getVotacaoCorrente();
    }

    public List<ResumoVotos> getVotosDeVotacaoAgrupadosHora(int idVotacao) {
        return votacaoDAO.getVotosDeVotacaoAgrupadosHora(idVotacao);
    }

    public enum Participantes {

        esquerda, direita;
    }

    public synchronized void votarEm(Participantes participante) {
        if (finalDaVotacao == 0) {
            inicializaVotacao();
        }
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

    /**
     * Metodo chamado via Quartz para salvar os votos a cada segundo.
     * Veja o applicationContext.xml para mais detalhes.
     */
    public void salvaVotacaoAtual() {
        Votos votos = getVotosContabilizadosParaReiniciarContagem();
        if (votos.getVotosParticipanteEsquerda() != 0 || votos.getVotosParticipanteDireita() != 0) {
            votacaoDAO.salvar(votos);
        }

    }
}
