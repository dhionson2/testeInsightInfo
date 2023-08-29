package testeInsightInfo.testeInsightInfo.servelet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import testeInsightInfo.testeInsightInfo.dao.MarcacoesFeitasDAO;
import testeInsightInfo.testeInsightInfo.model.MarcacaoFeita;

@WebServlet("/MarcacoesServlet")
public class MarcacoesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processarRequisicao(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processarRequisicao(request, response);
    }

    private void processarRequisicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");

        if ("adicionar".equals(acao)) {
            adicionarMarcacao(request, response);
        } else if ("marcacao".equals(acao)) {
            carregarMarcacao(request, response);
        } else if ("editar".equals(acao)) {
            editarMarcacao(request, response);
        } else if ("excluir".equals(acao)) {
            excluirMarcacao(request, response);
        }
    }

    private void adicionarMarcacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String entradaStr = request.getParameter("entrada");
    	String saidaStr = request.getParameter("saida");
    	String dataStr = request.getParameter("data");

    	// Converter strings para LocalTime
    	LocalTime entrada = LocalTime.parse(entradaStr);
    	LocalTime saida = LocalTime.parse(saidaStr);

    	// Converter string para LocalDate usando um DateTimeFormatter personalizado
    	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	LocalDate data = LocalDate.parse(dataStr, dateFormatter);

    	MarcacaoFeita novaMarcacao = new MarcacaoFeita(entrada, saida, data);

    	MarcacoesFeitasDAO marcacaoFeitaDAO = new MarcacoesFeitasDAO();
    	try {
    	    marcacaoFeitaDAO.adicionarMarcacaoFeita(novaMarcacao);
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}

    	response.sendRedirect(request.getContextPath() + "/MarcacoesServlet?acao=marcacao");
    }

    private void carregarMarcacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MarcacoesFeitasDAO marcacaoFeitaDAO = new MarcacoesFeitasDAO();
        List<MarcacaoFeita> marcacoes = new ArrayList<>();
        try {
            marcacoes = marcacaoFeitaDAO.listarMarcacoesFeitas();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("marcacoes", marcacoes);

        RequestDispatcher dispatcher = request.getRequestDispatcher("publica/marcacoes-feitas.jsp");
        dispatcher.forward(request, response);
    }

    private void editarMarcacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String novaDataStr = request.getParameter("data");
        String novaEntradaStr = request.getParameter("entrada");
        String novaSaidaStr = request.getParameter("saida");

        LocalDate novaData = null;
        LocalTime novaEntrada = null;
        LocalTime novaSaida = null;

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        try {
            novaData = LocalDate.parse(novaDataStr, dateFormatter);
            novaEntrada = LocalTime.parse(novaEntradaStr, timeFormatter);
            novaSaida = LocalTime.parse(novaSaidaStr, timeFormatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        MarcacoesFeitasDAO marcacaoFeitaDAO = new MarcacoesFeitasDAO();
        try {
            MarcacaoFeita marcacao = marcacaoFeitaDAO.obterMarcacaoFeitaPorID(id);
            marcacao.setData(novaData);
            marcacao.setEntrada(novaEntrada);
            marcacao.setSaida(novaSaida);
            marcacao.setId(id);
            marcacaoFeitaDAO.atualizarMarcacaoFeita(marcacao);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/MarcacoesServlet?acao=marcacao");
    }

    private void excluirMarcacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        long id = Long.parseLong(idStr);
        MarcacoesFeitasDAO marcacaoFeitaDAO = new MarcacoesFeitasDAO();
        try {
            marcacaoFeitaDAO.excluirMarcacaoFeita(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/MarcacoesServlet?acao=marcacao");
    }
}
