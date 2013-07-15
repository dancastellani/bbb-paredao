/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb;

import br.danielcastellani.bbb.dao.VotacaoDAO;
import br.danielcastellani.bbb.dao.VotacaoDAOImpl;
import br.danielcastellani.bbb.service.VotacaoService;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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


        System.out.println("ok");
        System.out.println("===================================");
    }

    public void contextDestroyed(ServletContextEvent sce) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void carregarDriverJDBC() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Erro ao conectar com o banco.", ex);
        }
    }
}
