/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb;

import br.danielcastellani.bbb.dao.VotacaoDAO;
import br.danielcastellani.bbb.dao.VotacaoDAOImpl;
import br.danielcastellani.bbb.exception.ApplicationError;
import br.danielcastellani.bbb.exception.ApplicationException;
import br.danielcastellani.bbb.job.SalvarVotosJob;
import br.danielcastellani.bbb.service.VotacaoService;
import com.googlecode.flyway.core.Flyway;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
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

    public ContextoAplicacao() {
        map = new HashMap<String, Object>();
    }

    public static ContextoAplicacao getContexto() {
        return contexto;
    }

    public Object getBean(String key) {
        return map.get(key);
    }

    public <T> T getBean(Class classe) throws ApplicationException {
        String key = classe.getCanonicalName();
        if (!map.containsKey(key)) {
            try {
                map.put(key, (T) classe.newInstance());
            } catch (Exception ex) {
                throw new ApplicationException("Erro ao inicializar classe: " + classe.getCanonicalName(), ex);
            }
        }
        T retorno = (T) map.get(key);
        return retorno;
    }

    public void contextInitialized(ServletContextEvent sce) {
        try {
            System.out.println("===================================");
            System.out.println("inicializando BBB");

            ContextoAplicacao.contexto = this;

            executaMigracao();
            carregarDriverJDBC();
            inicializarVotacao();
            inicializaTarefaSalvarVotos();

            System.out.println("ok");
            System.out.println("===================================");
        } catch (ApplicationException ex) {
            throw new ApplicationError("Erro ao inicializar o serviço de votação.", ex);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            desligarAgendamentoDeTarefas();
        } catch (ApplicationException ex) {
            throw new ApplicationError("Erro ao destruir o ContextoAplicação:" + this.getClass().getCanonicalName(), ex);
        }
    }

    public static void carregarDriverJDBC() throws ApplicationException {
        try {
            Properties prop = new Properties();
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db/database.properties"));
            Class.forName(prop.getProperty("database.driver"));

        } catch (ClassNotFoundException ex) {
            throw new ApplicationException("Erro ao executar a migração dos dados.", ex);
        } catch (IOException ex) {
            throw new ApplicationException("Erro ao executar a migração dos dados.", ex);
        }
    }

    private void inicializaTarefaSalvarVotos() throws ApplicationException {
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
            throw new ApplicationException("Erro ao executar o agendamento da tarefa de salvamento assíncrono dos votos.", ex);
        }
    }

    private void executaMigracao() throws ApplicationException {
        try {
            Properties prop = new Properties();
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db/database.properties"));

            Flyway flyway = new Flyway();
            flyway.setDataSource(prop.getProperty("database.url"), prop.getProperty("database.user"), prop.getProperty("database.password"));
            flyway.migrate();
        } catch (IOException ex) {
            throw new ApplicationException("Erro ao executar a migração dos dados.", ex);
        }
    }

    private void desligarAgendamentoDeTarefas() throws ApplicationException {
        try {
            // Grab the Scheduler instance from the Factory 
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            scheduler.shutdown();

        } catch (SchedulerException ex) {
            throw new ApplicationException("Erro ao finalizar as tarefas com Quartz.", ex);
        }
    }

    private void inicializarVotacao() throws ApplicationException {
        VotacaoDAO votacaoDAO = getBean(VotacaoDAOImpl.class);
        VotacaoService votacaoService = getBean(VotacaoService.class);
        votacaoService.setVotacaoDAO(votacaoDAO);
        votacaoService.inicializaVotacao();
    }
}
