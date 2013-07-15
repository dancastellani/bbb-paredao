/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.job;

import br.danielcastellani.bbb.ContextoAplicacao;
import br.danielcastellani.bbb.service.VotacaoService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Essa tarefa e executada com o Quartz de forma que periodicamente salva os votos.
 * 
 * @author DanCastellani
 */
public class SalvarVotosJob implements Job {

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        
        VotacaoService votacaoService = ContextoAplicacao.getContexto().getBean(VotacaoService.class);
        votacaoService.salvaVotacaoAtual();
    }
}
