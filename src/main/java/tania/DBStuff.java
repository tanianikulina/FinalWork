package tania;

import java.sql.*;
import java.time.Instant;
import java.util.UUID;

public class DBStuff {
    private Connection connection;

    public DBStuff() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://194.87.187.238/nikulina", "nikulina", "nikulina");
    }

    public void close() throws SQLException { connection.close(); }

    public void updateToken(UUID token, String login) throws SQLException {
        PreparedStatement insert = connection.prepareStatement("UPDATE loginsPasswordsNew SET token = ? WHERE login = ?");
        insert.setObject(1, token);
        insert.setString(2, login);
        insert.execute();
    }

    public Boolean check(String  login, String password) throws SQLException {
        PreparedStatement select = connection.prepareStatement("SELECT * FROM loginsPasswordsNew WHERE login= ? AND password = ?");
        select.setString(1, login);
        select.setString(2, password);
        ResultSet resultSet = select.executeQuery();
        while (resultSet.next())
            if (login.equals(resultSet.getString("login")) && password.equals(resultSet.getString("password"))) {
                return true;
            }
        return false;
    }

    public ResultSet getMessages() throws SQLException {
        PreparedStatement select = connection.prepareStatement("SELECT login, message, time FROM messages");
        return select.executeQuery();
    }

    public ResultSet getMessages(String time) throws SQLException {
        PreparedStatement select = connection.prepareStatement("SELECT * FROM messages WHERE time > CAST(? AS TIMESTAMP)");
        select.setString(1, time);
        return select.executeQuery();
    }

    public String getAuthor(UUID token) throws SQLException {
        PreparedStatement select = connection.prepareStatement("SELECT login FROM  loginsPasswordsNew WHERE token = ?");
        select.setObject(1, token);
        ResultSet resultSet = select.executeQuery();
        resultSet.next();
        return resultSet.getString("login");
    }

    public void putMessage(String login, String message, Instant time) throws SQLException {
        PreparedStatement insert = connection.prepareStatement("INSERT INTO messages (login, message, time) VALUES (?, ?, ?)");
        insert.setObject(1, login);
        insert.setObject(2, message);
        insert.setObject(3, Timestamp.from(time));
        insert.execute();
    }
}
