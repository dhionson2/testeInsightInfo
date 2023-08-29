package testeInsightInfo.testeInsightInfo.controle.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import testeInsightInfo.testeInsightInfo.model.HoraTrabalho;
import testeInsightInfo.testeInsightInfo.model.MarcacaoFeita;
import testeInsightInfo.testeInsightInfo.model.ResultadoDia;

public class CalculoAtrasoHoraExtra {

	  public static Map<LocalDate, ResultadoDia> calcularHorasExtrasEAtrasos(List<HoraTrabalho> horariosTrabalho, List<MarcacaoFeita> marcacoes) {
	        Map<LocalDate, ResultadoDia> resultado = new HashMap<>();

	        // Agrupar horariosTrabalho por data
	        Map<LocalDate, List<HoraTrabalho>> horariosPorData = horariosTrabalho.stream()
	                .collect(Collectors.groupingBy(HoraTrabalho::getData));

	        // Agrupar marcacoes por data
	        Map<LocalDate, List<MarcacaoFeita>> marcacoesPorData = marcacoes.stream()
	                .collect(Collectors.groupingBy(MarcacaoFeita::getData));

	        // Iterar sobre as datas para realizar os cálculos
	        for (LocalDate data : horariosPorData.keySet()) {
	            List<HoraTrabalho> horarios = horariosPorData.getOrDefault(data, new ArrayList<>());
	            List<MarcacaoFeita> marcacoesFeitas = marcacoesPorData.getOrDefault(data, new ArrayList<>());

	            int totalAtrasosMinutos = 0;
	            int totalHorasExtrasMinutos = 0;

	            // Seu código de cálculo de atrasos e horas extras aqui
	            for (HoraTrabalho horario : horarios) {
	                boolean trabalhou = false;

	                for (MarcacaoFeita marcacao : marcacoesFeitas) {
	                    if (marcacao.getEntrada().compareTo(horario.getSaida()) < 0 && marcacao.getSaida().compareTo(horario.getEntrada()) > 0) {
	                        // Atraso
	                        totalAtrasosMinutos += Math.max(0, calcularDiferencaMinutos(marcacao.getEntrada(), horario.getEntrada()));
	                        totalHorasExtrasMinutos += Math.max(0, calcularDiferencaMinutos(horario.getSaida(), marcacao.getSaida()));
	                        trabalhou = true;
	                    }
	                }

	                if (!trabalhou) {
	                    // Atraso devido a não ter trabalhado
	                    totalAtrasosMinutos += calcularDiferencaMinutos(horario.getEntrada(), horario.getSaida());
	                }
	            }

	            // Calcular horas extras devido a entrada antecipada e trabalho fora do período
	            for (MarcacaoFeita marcacao : marcacoesFeitas) {
	                for (HoraTrabalho horario : horarios) {
	                    if (marcacao.getEntrada().compareTo(horario.getEntrada()) < 0 && marcacao.getSaida().compareTo(horario.getEntrada()) > 0) {
	                        totalHorasExtrasMinutos += Math.max(0, calcularDiferencaMinutos(marcacao.getEntrada(), horario.getEntrada()));
	                    }
	                    if (marcacao.getEntrada().compareTo(horario.getSaida()) < 0 && marcacao.getSaida().compareTo(horario.getSaida()) > 0) {
	                        totalHorasExtrasMinutos += Math.max(0, calcularDiferencaMinutos(horario.getSaida(), marcacao.getSaida()));
	                    }

	                    // Considerar marcações que começam antes do horário de trabalho e terminam depois do horário de trabalho
	                    if (marcacao.getEntrada().compareTo(horario.getEntrada()) < 0 && marcacao.getSaida().compareTo(horario.getSaida()) > 0) {
	                        totalHorasExtrasMinutos += calcularDiferencaMinutos(horario.getEntrada(), horario.getSaida());
	                    }
	                }
	            }

	            ResultadoDia resultadoDia = new ResultadoDia();
	            resultadoDia.adicionarAtraso(totalAtrasosMinutos);
	            resultadoDia.adicionarHoraExtra(totalHorasExtrasMinutos);

	            // Associar o resultado à data correspondente no mapa
	            resultado.put(data, resultadoDia);
	        }

	        return resultado;
	    }

	    public static int calcularDiferencaMinutos(LocalTime hora1, LocalTime hora2) {
	        int hora1Minutos = hora1.getHour() * 60 + hora1.getMinute();
	        int hora2Minutos = hora2.getHour() * 60 + hora2.getMinute();

	        return hora2Minutos - hora1Minutos;
	    }
	}
