/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb;

import br.danielcastellani.bbb.dao.VotacaoDAO;
import br.danielcastellani.bbb.dao.VotacaoDAOImpl;
import br.danielcastellani.bbb.job.SalvarVotosJob;
import br.danielcastellani.bbb.service.VotacaoService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author DanCastellani
 */
public class ContextoAplicacao implements ServletContextListener {

    private static ContextoAplicacao contexto = null;
    private Map<String, Object> map;

    public static ContextoAplicacao getContexto() {
        return contexto;
    }

    public Object getBean(String key) {
        return map.get(key);
    }

    public <T> T getBean(Class classe) {
        String key = classe.getCanonicalName();
        if (!map.containsKey(key)) {
            try {
                map.put(key, (T) classe.newInstance());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        T retorno = (T) map.get(key);
        return retorno;
    }

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("===================================");
        System.out.println("inicializando BBB");

        carregarDriverJDBC();

        ContextoAplicacao.contexto = this;
        map = new HashMap<String, Object>();
        VotacaoDAO votacaoDAO = getBean(VotacaoDAOImpl.class);
        VotacaoService votacaoService = getBean(VotacaoService.class);
        votacaoService.setVotacaoDAO(votacaoDAO);
        votacaoService.inicializaVotacao();

        inicializaTarefaSalvarVotos();

        System.out.println("ok");
        System.out.println("===================================");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            // Grab the Scheduler instance from the Factory 
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            scheduler.shutdown();

        } catch (SchedulerException se) {
            throw new RuntimeException(se);
        }
    }

    public static void carregarDriverJDBC() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Erro ao conectar com o banco.", ex);
        }
    }

    private void inicializaTarefaSalvarVotos() {
        try {
            JobDetail job = new JobDetail();
            job.setName("SalvarVotos");
            job.setJobClass(SalvarVotosJob.class);

            Trigger trigger = TriggerUtils.makeSecondlyTrigger(1);
            trigger.setName("cadaUmSegundo");

            //schedule it
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException ex) {
            throw new RuntimeException(ex);
        }
    }
}
