package testeInsightInfo.testeInsightInfo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import testeInsightInfo.testeInsightInfo.dao.util.Conexao;
import testeInsightInfo.testeInsightInfo.model.MarcacaoFeita;

public class MarcacoesFeitasDAO {
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

    public void adicionarMarcacaoFeita(MarcacaoFeita marcacaoFeita) throws SQLException {
        conectar();
        try {
            String sql = "INSERT INTO MarcacoesFeitas (entrada, saida, data) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, marcacaoFeita.getEntrada().toString());
                statement.setString(2, marcacaoFeita.getSaida().toString());
                statement.setString(3, marcacaoFeita.getData().toString()); 
                statement.executeUpdate();
            }
        } finally {
            desconectar();
        }
    }

    public void atualizarMarcacaoFeita(MarcacaoFeita marcacaoFeita) throws SQLException {
        conectar();
        try {
            String sql = "UPDATE MarcacoesFeitas SET entrada = ?, saida = ?, data = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, marcacaoFeita.getEntrada().toString());
                statement.setString(2, marcacaoFeita.getSaida().toString());
                statement.setString(3, marcacaoFeita.getData().toString()); 
                statement.setLong(4, marcacaoFeita.getId());
                statement.executeUpdate();
            }
        } finally {
            desconectar();
        }
    }

    public void excluirMarcacaoFeita(long id) throws SQLException {
        conectar();
        try {
            String sql = "DELETE FROM MarcacoesFeitas WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
        } finally {
            desconectar();
        }
    }

    public List<MarcacaoFeita> listarMarcacoesFeitas() throws SQLException {
        conectar();
        List<MarcacaoFeita> marcacoes = new ArrayList<>();
        try {
            String sql = "SELECT id, entrada, saida, data FROM MarcacoesFeitas";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String entrada = resultSet.getString("entrada");
                    String saida = resultSet.getString("saida");
                    String data = resultSet.getString("data"); 
                    MarcacaoFeita marcacaoFeita = new MarcacaoFeita(id, entrada, saida, data); 
                    marcacoes.add(marcacaoFeita);
                }
            }
        } finally {
            desconectar();
        }
        return marcacoes;
    }

    public MarcacaoFeita obterMarcacaoFeitaPorID(long id) throws SQLException {
        conectar();
        try {
            String sql = "SELECT entrada, saida, data FROM MarcacoesFeitas WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String entrada = resultSet.getString("entrada");
                        String saida = resultSet.getString("saida");
                        String data = resultSet.getString("data");
                        return new MarcacaoFeita(id, entrada, saida, data);
                    }
                }
            }
        } finally {
            desconectar();
        }
        return null;
    }
}
