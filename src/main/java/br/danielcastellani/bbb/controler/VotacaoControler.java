/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.controler;

import org.codehaus.jackson.map.ObjectMapper;
import br.danielcastellani.bbb.ContextoAplicacao;
import br.danielcastellani.bbb.exception.ApplicationError;
import br.danielcastellani.bbb.exception.ApplicationException;
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
        try {
            this.votacaoService = ContextoAplicacao.getContexto().getBean(VotacaoService.class);
        } catch (ApplicationException ex) {
            throw new ApplicationError("Erro ao construir:" + this.getClass().getCanonicalName(), ex);
        }
    }

    public void votar(HttpServletRequest request) {
        String participanteVotado = (String) request.getParameter("part_id");
//        System.out.println("participanteVotado = " + participanteVotado);

        if ("esquerda".equals(participanteVotado)) {
            votacaoService.votarEm(VotacaoService.Participantes.esquerda);
        } else if ("direita".equals(participanteVotado)) {
            votacaoService.votarEm(VotacaoService.Participantes.direita);
        }
    }

    public String getSituacaoVotacao() throws ApplicationException {
        SituacaoVotacao situacaoVotacao = votacaoService.getSituacaoVotacao();

        //transforma em JSON 
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(situacaoVotacao);
        } catch (IOException ex) {
            throw new ApplicationException("Erro ao recuperar a situação da votação.", ex);
        }
    }
}
