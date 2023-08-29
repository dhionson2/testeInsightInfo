package testeInsightInfo.testeInsightInfo.model;

import java.time.LocalTime;

public class PeriodoTrabalho {
    private String data;
    private LocalTime entrada;
    private LocalTime saida;

    public PeriodoTrabalho(String data, LocalTime entrada, LocalTime saida) {
        this.data = data;
        this.entrada = entrada;
        this.saida = saida;
    }

    public void adicionarPeriodo(LocalTime entrada, LocalTime saida) {
        if (this.entrada.compareTo(entrada) > 0) {
            this.entrada = entrada;
        }

        if (this.saida.compareTo(saida) < 0) {
            this.saida = saida;
        }
    }
    
    
    // Métodos getters e setters para entrada, saída e data
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
