/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb;

import br.danielcastellani.bbb.dao.VotacaoDAO;
import br.danielcastellani.bbb.dao.VotacaoDAOImpl;
import br.danielcastellani.bbb.model.Votacao;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author DanCastellani
 */
public class ContextoAplicacao implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        //inicializa o ControladorDeVotos com a votacao atual
        ControladorDeVotos.getInstance().setVotacaoDAO(new VotacaoDAOImpl());
        ControladorDeVotos.getInstance().atualizaVotacaoAtual();
        
        //inicia o salvamento de votos
        

    }

    public void contextDestroyed(ServletContextEvent sce) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
