/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.controler;

import br.danielcastellani.bbb.ContextoAplicacao;
import br.danielcastellani.bbb.exception.ApplicationError;
import br.danielcastellani.bbb.exception.ApplicationException;
import br.danielcastellani.bbb.model.ResumoVotos;
import br.danielcastellani.bbb.model.Votacao;
import br.danielcastellani.bbb.service.VotacaoService;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author DanCastellani
 */
@ManagedBean
@RequestScoped
public class ResumoMB {

    private Votacao votacao;
    private VotacaoService votacaoService;
    private List<ResumoVotos> votosPorHora;

    public ResumoMB() {
        try {
            votacaoService = ContextoAplicacao.getContexto().getBean(VotacaoService.class);
            votacao = votacaoService.getVotacaoCorrente();
            votosPorHora = votacaoService.getVotosDeVotacaoAgrupadosHora(votacao.getId());
        } catch (ApplicationException ex) {
            throw new ApplicationError("Erro ao construir:" + this.getClass().getCanonicalName(), ex);
        }
    }

    /**
     * @return the votacao
     */
    public Votacao getVotacao() {
        return votacao;
    }

    /**
     * @return the votacaoService
     */
    public VotacaoService getVotacaoService() {
        return votacaoService;
    }

    /**
     * @return the votosPorHora
     */
    public List<ResumoVotos> getVotosPorHora() {
        return votosPorHora;
    }

    public int getVotosTotais() {
        int total = 0;
        for (ResumoVotos resumoVotos : votosPorHora) {
            total += resumoVotos.getVotosDireita() + resumoVotos.getVotosEsquerda();
        }
        return total;
    }

    public int getVotosTotaisEsquerda() {
        int total = 0;
        for (ResumoVotos resumoVotos : votosPorHora) {
            total += resumoVotos.getVotosEsquerda();
        }
        return total;
    }

    public int getVotosTotaisDireita() {
        int total = 0;
        for (ResumoVotos resumoVotos : votosPorHora) {
            total += resumoVotos.getVotosDireita();
        }
        return total;
    }

    public long getPercentualVotosTotaisEsquerda() {
        double percentual = getVotosTotaisEsquerda() * 100;
        percentual /= getVotosTotais();
        return Math.round(percentual);
    }

    public long getPercentualVotosTotaisDireita() {
        double percentual = getVotosTotaisDireita() * 100;
        percentual /= getVotosTotais();
        return Math.round(percentual);
    }
}
