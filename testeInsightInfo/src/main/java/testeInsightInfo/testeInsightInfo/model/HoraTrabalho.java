package testeInsightInfo.testeInsightInfo.model;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;

public class HoraTrabalho {
    public long id;
    public LocalDate data;
    public LocalTime entrada;
    public LocalTime saida;
    public List<PeriodoTrabalho> periodos;

    public HoraTrabalho() {

    }

    public HoraTrabalho(LocalDate data, LocalTime entrada, LocalTime saida) {
        this.data = data;
        this.entrada = entrada;
        this.saida = saida;
    }

    public HoraTrabalho(LocalDate data, LocalTime entrada, LocalTime saida, List<PeriodoTrabalho> periodos) {
        this.data = data;
        this.entrada = entrada;
        this.saida = saida;
        this.periodos = periodos;
    }

    // Adicione os métodos getters e setters para todos os campos

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

    public List<PeriodoTrabalho> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<PeriodoTrabalho> periodos) {
        this.periodos = periodos;
    }
}
