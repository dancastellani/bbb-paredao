/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.controler;

import org.codehaus.jackson.map.ObjectMapper;
import br.danielcastellani.bbb.exception.ApplicationException;
import br.danielcastellani.bbb.model.SituacaoVotacao;
import br.danielcastellani.bbb.service.VotacaoService;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author DanCastellani
 */
// spring + jsf
@Component
@ManagedBean
@SessionScoped
public class VotacaoControler {

    @Autowired
    private VotacaoService votacaoService;

    public void votar(HttpServletRequest request) {
        String participanteVotado = (String) request.getParameter("part_id");

        if ("esquerda".equals(participanteVotado)) {
            getVotacaoService().votarEm(VotacaoService.Participantes.esquerda);
        } else if ("direita".equals(participanteVotado)) {
            getVotacaoService().votarEm(VotacaoService.Participantes.direita);
        }
    }

    public String getSituacaoVotacao() throws ApplicationException {
        SituacaoVotacao situacaoVotacao = votacaoService.getSituacaoVotacao();

        //transforma em JSON 
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(situacaoVotacao);
        } catch (IOException ex) {
            throw new ApplicationException(ex);
        }
    }

    /**
     * @return the votacaoService
     */
    public VotacaoService getVotacaoService() {
        return votacaoService;
    }

    /**
     * @param votacaoService the votacaoService to set
     */
    public void setVotacaoService(VotacaoService votacaoService) {
        this.votacaoService = votacaoService;
    }
}
