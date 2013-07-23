/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.controler.ws;

import javax.servlet.ServletConfig;
import br.danielcastellani.bbb.controler.VotacaoControler;
import br.danielcastellani.bbb.exception.ApplicationException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author DanCastellani
 */
public class WSServlet extends HttpServlet {

    @Autowired
    private VotacaoControler votacaoControler;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestSemContexto = request.getRequestURI().substring(request.getContextPath().length() + "/votacao".length());
        String retorno = "";
        if (requestSemContexto.equals("/votar")) {
            votacaoControler.votar(request);
        } else if (requestSemContexto.equals("/situacao")) {
            try {
                retorno = votacaoControler.getSituacaoVotacao();
            } catch (ApplicationException ex) {
                throw new ServletException("Erro processando requisição: "+ requestSemContexto, ex);
            }
        }

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print(retorno);
        } finally {
            writer.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "BBB Servlet";
    }// </editor-fold>
}
