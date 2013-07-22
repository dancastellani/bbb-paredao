/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb;

import br.danielcastellani.bbb.dao.VotacaoDAO;
import br.danielcastellani.bbb.dao.VotacaoDAOImpl;
import br.danielcastellani.bbb.service.VotacaoService;
import com.googlecode.flyway.core.Flyway;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
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

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("===================================");
        System.out.println("inicializando BBB");

        executaMigracao();

        carregarDriverJDBC();

        ContextoAplicacao.contexto = this;
        map = new HashMap<String, Object>();
        VotacaoDAO votacaoDAO = getBean(VotacaoDAOImpl.class);
        VotacaoService votacaoService = getBean(VotacaoService.class);
        votacaoService.setVotacaoDAO(votacaoDAO);
        votacaoService.inicializaVotacao();

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
            Properties prop = new Properties();
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db/database.properties"));
            Class.forName(prop.getProperty("database.driver"));

        } catch (IOException ex) {
            throw new RuntimeException("Erro ao conectar com o banco.", ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Erro ao conectar com o banco.", ex);
        }
    }

    private void executaMigracao() {
        try {
            Properties prop = new Properties();
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db/database.properties"));

            Flyway flyway = new Flyway();
            flyway.setDataSource(prop.getProperty("database.url"), prop.getProperty("database.user"), prop.getProperty("database.password"));
            flyway.migrate();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
