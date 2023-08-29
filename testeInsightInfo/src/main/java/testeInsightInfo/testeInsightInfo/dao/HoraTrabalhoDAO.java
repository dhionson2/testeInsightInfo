package testeInsightInfo.testeInsightInfo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import testeInsightInfo.testeInsightInfo.dao.util.Conexao;
import testeInsightInfo.testeInsightInfo.model.HoraTrabalho;

public class HoraTrabalhoDAO {
    private Connection connection;

    private void conectar() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = Conexao.getConexao();
        }
    }

    private void desconectar() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void adicionarHoraTrabalho(HoraTrabalho horaTrabalho) throws SQLException {
        conectar();
        try {
            String sql = "INSERT INTO horario_trabalho (data, entrada, saida) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDate(1, java.sql.Date.valueOf(horaTrabalho.getData()));
                statement.setTime(2, java.sql.Time.valueOf(horaTrabalho.getEntrada()));
                statement.setTime(3, java.sql.Time.valueOf(horaTrabalho.getSaida()));
                statement.executeUpdate();
            }
        } finally {
            desconectar();
        }
    }

    public void atualizarHoraTrabalho(HoraTrabalho horaTrabalho) throws SQLException {
        conectar();
        try {
            String sql = "UPDATE horario_trabalho SET data = ?, entrada = ?, saida = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDate(1, java.sql.Date.valueOf(horaTrabalho.getData()));
                statement.setTime(2, java.sql.Time.valueOf(horaTrabalho.getEntrada()));
                statement.setTime(3, java.sql.Time.valueOf(horaTrabalho.getSaida()));
                statement.setLong(4, horaTrabalho.getId());
                statement.executeUpdate();
            }
        } finally {
            desconectar();
        }
    }

    public List<HoraTrabalho> listarHorariosTrabalho() throws SQLException {
        conectar();
        List<HoraTrabalho> horarios = new ArrayList<>();
        try {
            String sql = "SELECT id, data, entrada, saida FROM horario_trabalho";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    java.sql.Date data = resultSet.getDate("data");
                    java.sql.Time entrada = resultSet.getTime("entrada");
                    java.sql.Time saida = resultSet.getTime("saida");

                    HoraTrabalho horaTrabalho = new HoraTrabalho(data.toLocalDate(), entrada.toLocalTime(), saida.toLocalTime());
                    horaTrabalho.setId(id);
                    horarios.add(horaTrabalho);
                }
            }
        } finally {
            desconectar();
        }
        return horarios;
    }

    public HoraTrabalho obterHoraTrabalhoPorID(long id) throws SQLException {
        conectar();
        HoraTrabalho horaTrabalho = null;
        try {
            String sql = "SELECT data, entrada, saida FROM horario_trabalho WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        java.sql.Date data = resultSet.getDate("data");
                        java.sql.Time entrada = resultSet.getTime("entrada");
                        java.sql.Time saida = resultSet.getTime("saida");

                        horaTrabalho = new HoraTrabalho(data.toLocalDate(), entrada.toLocalTime(), saida.toLocalTime());
                        horaTrabalho.setId(id);
                    }
                }
            }
        } finally {
            desconectar();
        }
        return horaTrabalho;
    }

    public void excluirHoraTrabalho(long id) throws SQLException {
        conectar();
        try {
            String sql = "DELETE FROM horario_trabalho WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
        } finally {
            desconectar();
        }
    }
}
