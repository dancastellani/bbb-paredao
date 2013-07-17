/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.controler.ws;

import br.danielcastellani.bbb.ContextoAplicacao;
import br.danielcastellani.bbb.controler.VotacaoControler;
import br.danielcastellani.bbb.exception.ApplicationError;
import br.danielcastellani.bbb.exception.ApplicationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DanCastellani
 */
public class WSServlet extends HttpServlet {

    private VotacaoControler votacaoControler;

    public WSServlet() {
        try {
            this.votacaoControler = ContextoAplicacao.getContexto().getBean(VotacaoControler.class);
        } catch (ApplicationException ex) {
            throw new ApplicationError("Erro ao criar o WS:" + this.getClass().getCanonicalName(), ex);
        }
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
                throw new ServerException("Erro processando requisição: "+ requestSemContexto, ex);
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
