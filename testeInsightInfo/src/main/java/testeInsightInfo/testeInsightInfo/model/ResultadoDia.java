package testeInsightInfo.testeInsightInfo.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ResultadoDia {
    private LocalDate data;
    private int horasExtras;
    private int atrasos;
    private double atraso;


    public ResultadoDia(LocalDate data) {
        this.data = data;
        this.horasExtras = 0;
        this.atrasos = 0;

    }

    public ResultadoDia(int horasExtras, int atrasos) {
        this.horasExtras = horasExtras;
        this.atrasos = atrasos;
    }
    public ResultadoDia() {
        this.horasExtras = 0;
        this.atrasos = 0;
    }
    public ResultadoDia(LocalDate data, Duration horasTrabalhadas, Duration horasExtras, Duration atrasos) {
        this.data = data;
        this.horasExtras = (int) horasExtras.toMinutes();
        this.atrasos = (int) atrasos.toMinutes();

    }

    public void adicionarHoraExtra(int horasExtras) {
        this.horasExtras += horasExtras;
    }

    public void adicionarAtraso(int atrasos) {
        this.atrasos += atrasos;
    }
    
    public LocalDate getData() {
        return data;
    }

    public int getHorasExtras() {
        return this.horasExtras;
    }

    public int getAtrasos() {
        return this.atrasos;
    }

    public double getAtraso() {
        return atraso;
    }

    public void setAtraso(double atraso) {
        this.atraso = atraso;
    }
    public void setData(LocalDate data) {
		this.data = data;
	}

}
