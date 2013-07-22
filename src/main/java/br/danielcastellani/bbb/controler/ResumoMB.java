/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.controler;

import br.danielcastellani.bbb.model.ResumoVotos;
import br.danielcastellani.bbb.model.Votacao;
import br.danielcastellani.bbb.service.VotacaoService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;
import static org.springframework.web.context.WebApplicationContext.*;

/**
 *
 * @author DanCastellani
 */
@Named
@Scope(SCOPE_REQUEST)
public class ResumoMB {

    private Votacao votacao;
    @Inject
    private VotacaoService votacaoService;
    private List<ResumoVotos> votosPorHora;

    public ResumoMB() {
//        votacaoService = ContextoAplicacao.getContexto().getBean(VotacaoService.class);
    }

    @PostConstruct
    public void init() {
        votacao = votacaoService.getVotacaoCorrente();
        votosPorHora = votacaoService.getVotosDeVotacaoAgrupadosHora(votacao.getId());
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
