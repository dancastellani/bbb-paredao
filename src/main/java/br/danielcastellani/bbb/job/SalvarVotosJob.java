/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.job;

import br.danielcastellani.bbb.ContextoAplicacao;
import br.danielcastellani.bbb.exception.ApplicationError;
import br.danielcastellani.bbb.exception.ApplicationException;
import br.danielcastellani.bbb.service.VotacaoService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Essa tarefa e executada com o Quartz de forma que periodicamente salva os
 * votos.
 *
 * @author DanCastellani
 */
public class SalvarVotosJob implements Job {

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        try {
            VotacaoService votacaoService = ContextoAplicacao.getContexto().getBean(VotacaoService.class);
            votacaoService.salvaVotacaoAtual();
        } catch (ApplicationException ex) {
            throw new ApplicationError("Erro ao salvar votos na tarefa assíncrona." , ex);
        }
    }
}
