package testeInsightInfo.testeInsightInfo.servelet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import testeInsightInfo.testeInsightInfo.dao.HoraTrabalhoDAO;
import testeInsightInfo.testeInsightInfo.model.HoraTrabalho;

@WebServlet("/HorarioTrabalhoServlet")
public class HorarioTrabalhoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public HorarioTrabalhoServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            adicionarHorario(request, response);
        } else if ("editar".equals(acao)) {
            editarHorario(request, response);
        } else if ("excluir".equals(acao)) {
            excluirHorario(request, response);
        } else if ("horarioTrabalho".equals(acao)) {
            listarHorarios(request, response);
        } else {
            // Trate outros casos, se necessário
        }
    }

    private void adicionarHorario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String entrada = request.getParameter("entrada");
        String saida = request.getParameter("saida");
        String dataStr = request.getParameter("data");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataStr, formatter);

        HoraTrabalhoDAO horaTrabalhoDAO = new HoraTrabalhoDAO();
        HoraTrabalho novoHorario = new HoraTrabalho(data, LocalTime.parse(entrada), LocalTime.parse(saida));

        try {
            horaTrabalhoDAO.adicionarHoraTrabalho(novoHorario);
        } catch (SQLException e) {
            // Lidar com erros de banco de dados
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/HorarioTrabalhoServlet?acao=horarioTrabalho");
    }

    private void listarHorarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HoraTrabalhoDAO horaTrabalhoDAO = new HoraTrabalhoDAO();
        List<HoraTrabalho> horarios = new ArrayList<>();

        try {
            horarios = horaTrabalhoDAO.listarHorariosTrabalho();
        } catch (SQLException e) {
            // Lidar com erros de banco de dados
            e.printStackTrace();
        }

        request.setAttribute("horarios", horarios);

        RequestDispatcher dispatcher = request.getRequestDispatcher("publica/horario-trabalho.jsp");
        dispatcher.forward(request, response);
    }

    private void editarHorario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        long id = Long.parseLong(idStr);

        String novaData = request.getParameter("data");
        String novaEntrada = request.getParameter("entrada");
        String novaSaida = request.getParameter("saida");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataFormatada = LocalDate.parse(novaData, formatter);

        HoraTrabalhoDAO horaTrabalhoDAO = new HoraTrabalhoDAO();
        try {
            HoraTrabalho horario = horaTrabalhoDAO.obterHoraTrabalhoPorID(id);

            horario.setData(dataFormatada);
            horario.setEntrada(LocalTime.parse(novaEntrada));
            horario.setSaida(LocalTime.parse(novaSaida));

            horaTrabalhoDAO.atualizarHoraTrabalho(horario);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/HorarioTrabalhoServlet?acao=horarioTrabalho");
    }


    private void excluirHorario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        long id = Long.parseLong(idStr);

        HoraTrabalhoDAO horaTrabalhoDAO = new HoraTrabalhoDAO();
        try {
            horaTrabalhoDAO.excluirHoraTrabalho(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/HorarioTrabalhoServlet?acao=horarioTrabalho");
    }
}
