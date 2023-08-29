package testeInsightInfo.testeInsightInfo.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MarcacaoFeita {
    public long id;
    public LocalDate data;
    public LocalTime entrada;
    public LocalTime saida;
    public String atraso;

    public MarcacaoFeita() {
        // Default constructor
    }

    public MarcacaoFeita(LocalTime entrada, LocalTime saida, LocalDate data) {
        this.entrada = entrada;
        this.saida =saida;
        this.data = data;
    }



    public MarcacaoFeita(long id, String entrada, String saida, String data) {
        this.id = id;
        this.entrada = LocalTime.parse(entrada);
        this.saida = LocalTime.parse(saida);
        this.data = LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    public boolean estaDentroDoPeriodo(HoraTrabalho horario) {
        return entrada.isAfter(horario.getEntrada()) && entrada.isBefore(horario.getSaida());
    }
    public LocalDateTime getEntradaDateTime() {
        return LocalDateTime.of(data, entrada);
    }

    public LocalDateTime getSaidaDateTime() {
        return LocalDateTime.of(data, saida);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalTime entrada) {
        this.entrada = entrada;
    }

    public LocalTime getSaida() {
        return saida;
    }

    public void setSaida(LocalTime saida) {
        this.saida = saida;
    }

    public String getAtraso() {
        return atraso;
    }

    public void setAtraso(String atraso) {
        this.atraso = atraso;
    }
}
