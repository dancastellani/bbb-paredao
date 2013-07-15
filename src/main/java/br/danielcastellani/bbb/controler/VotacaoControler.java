/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.controler;

import org.codehaus.jackson.map.ObjectMapper;
import br.danielcastellani.bbb.ContextoAplicacao;
import br.danielcastellani.bbb.model.SituacaoVotacao;
import br.danielcastellani.bbb.service.VotacaoService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author DanCastellani
 */
public class VotacaoControler {

    private VotacaoService votacaoService;

    public VotacaoControler() {
        this.votacaoService = ContextoAplicacao.getContexto().getBean(VotacaoService.class);
    }

    public void votar(HttpServletRequest request) {
        String participanteVotado = (String) request.getParameter("part_id");
        System.out.println("participanteVotado = " + participanteVotado);

        if ("esquerda".equals(participanteVotado)) {
            votacaoService.votarEm(VotacaoService.Participantes.esquerda);
        } else if ("direita".equals(participanteVotado)) {
            votacaoService.votarEm(VotacaoService.Participantes.direita);
        }

        //remover para deixar a cada espaço de tempo
        votacaoService.salvaVotacaoAtual();
    }

    public String getSituacaoVotacao() {
        SituacaoVotacao situacaoVotacao = votacaoService.getSituacaoVotacao();

        //transforma em JSON 
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(situacaoVotacao);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
