package testeInsightInfo.testeInsightInfo.servelet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import testeInsightInfo.testeInsightInfo.controle.util.CalculoAtrasoHoraExtra;
import testeInsightInfo.testeInsightInfo.dao.HoraTrabalhoDAO;
import testeInsightInfo.testeInsightInfo.dao.MarcacoesFeitasDAO;
import testeInsightInfo.testeInsightInfo.model.HoraTrabalho;
import testeInsightInfo.testeInsightInfo.model.MarcacaoFeita;
import testeInsightInfo.testeInsightInfo.model.ResultadoDia;

/**
 * Servlet implementation class CalcularHoraExtra
 */
@WebServlet("/CalcularHoraExtra")
public class CalcularHoraExtra extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalcularHoraExtra() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 try {
			processarRequisicao(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			processarRequisicao(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void processarRequisicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String acao = request.getParameter("acao");
        System.out.println("acao"+acao);
        if ("hextra".equals(acao)) {
        	carregaListaCalculada(request, response);
        }
    }

    private void carregaListaCalculada(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        MarcacoesFeitasDAO marcacaoFeitaDAO = new MarcacoesFeitasDAO();
        HoraTrabalhoDAO horaTrabalhoDAO = new HoraTrabalhoDAO();
        List<MarcacaoFeita> marcacoes = marcacaoFeitaDAO.listarMarcacoesFeitas();
        List<HoraTrabalho> horariosTrabalho = horaTrabalhoDAO.listarHorariosTrabalho();

        Map<LocalDate, ResultadoDia> atrasosPorData = null;
        try {
            // Calcula as horas extras e atrasos para o dia inteiro
            Map<LocalDate, ResultadoDia> resultado = CalculoAtrasoHoraExtra.calcularHorasExtrasEAtrasos(horariosTrabalho, marcacoes);

            // Agrupa o resultado por data
            Map<LocalDate, ResultadoDia> resultadoAgrupado = resultado;

            // Agora você pode atribuir o resultado agrupado a atrasosPorData
            atrasosPorData = resultadoAgrupado;

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("atrasosPorData", atrasosPorData);


        RequestDispatcher dispatcher = request.getRequestDispatcher("publica/calculoHoraExtra.jsp");
        dispatcher.forward(request, response);
    }

}
