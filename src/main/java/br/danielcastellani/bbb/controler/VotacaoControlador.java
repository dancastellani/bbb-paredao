/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.bbb.controler;

import br.danielcastellani.bbb.ContextoAplicacao;
import br.danielcastellani.bbb.service.VotacaoService;
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
public class VotacaoControlador extends HttpServlet {

    private VotacaoService votacaoService;

    public VotacaoControlador() {
        this.votacaoService = ContextoAplicacao.getContexto().getBean(VotacaoService.class);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VotacaoServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VotacaoServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
        String participanteVotado = (String) request.getParameter("part_id");
        System.out.println("participanteVotado = " + participanteVotado);

        if ("esquerda".equals(participanteVotado)) {
            votacaoService.votarEm(VotacaoService.Participantes.esquerda);
        } else if ("direita".equals(participanteVotado)) {
            votacaoService.votarEm(VotacaoService.Participantes.direita);
        }
    }

    @Override
    public String getServletInfo() {
        return "BBB Servlet";
    }// </editor-fold>

    /**
     * @param votacaoService the votacaoService to set
     */
    public void setVotacaoService(VotacaoService votacaoService) {
        this.votacaoService = votacaoService;
    }
}
