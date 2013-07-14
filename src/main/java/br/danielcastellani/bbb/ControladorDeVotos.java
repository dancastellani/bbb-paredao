/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb;

import br.danielcastellani.bbb.dao.VotacaoDAO;
import br.danielcastellani.bbb.dao.VotacaoDAOImpl;
import br.danielcastellani.bbb.model.PacoteDeVotos;
import br.danielcastellani.bbb.model.Votacao;

/**
 * Um contador thread safe para receber cada voto e armazenar até que sejam
 * salvos.
 *
 * @author DanCastellani
 */
public class ControladorDeVotos {

    private int votosDireita;
    private int votosEsquerda;
    private long finalDaVotacao; //para otimizar a comparacao a cada voto

    public enum Participantes {

        esquerda, direita;
    }

    public synchronized void votarEm(Participantes participante) {
        if (System.currentTimeMillis() < finalDaVotacao) {
            if (Participantes.direita == participante) {
                votosDireita++;
            } else if (Participantes.esquerda == participante) {
                votosEsquerda++;
            }
        }
        System.out.println("Votos atuais:");
        System.out.println("votosEsquerda = " + votosEsquerda);
        System.out.println("votosDireita = " + votosDireita);
        System.out.println("Tempo restante: " + (finalDaVotacao - System.currentTimeMillis()) + " milissegundos");
    }

    public synchronized PacoteDeVotos getVotosContabilizadosParaReiniciarContagem() {
        PacoteDeVotos pacoteDeVotos = new PacoteDeVotos(votosEsquerda, votosDireita, System.currentTimeMillis());
        this.votosDireita = 0;
        this.votosEsquerda = 0;
        return pacoteDeVotos;
    }
    private static ControladorDeVotos controladorDeVotos;

    //TODO: rever essa inicializacao
    private ControladorDeVotos() {
        VotacaoDAO votacaoDAO = new VotacaoDAOImpl();
        Votacao votacaoCorrente = votacaoDAO.getVotacaoCorrente();
        finalDaVotacao = votacaoCorrente.getFim().getTime();
    }

    public synchronized static ControladorDeVotos getInstance() {
        if (controladorDeVotos == null) {
            controladorDeVotos = new ControladorDeVotos();
        }
        return controladorDeVotos;
    }
}
