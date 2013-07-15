/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.controler;

import br.danielcastellani.bbb.ContextoAplicacao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DanCastellani
 */
public class FrontServlet extends HttpServlet {

    private VotacaoControler votacaoControler;

    public FrontServlet() {
        this.votacaoControler = ContextoAplicacao.getContexto().getBean(VotacaoControler.class);
    }

//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        try {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet VotacaoServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet VotacaoServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        } finally {
//            out.close();
//        }
//    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI().substring("/votacao".length());
        String retorno = "";
        if (requestURI.equals("/votar")) {
            votacaoControler.votar(request);
        } else if (requestURI.equals("/situacao")) {
            retorno = votacaoControler.getSituacaoVotacao();
        }
        
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print(retorno);
            writer.close();
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
