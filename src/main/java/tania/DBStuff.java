package tania;

import java.sql.*;
import java.time.Instant;

public class DBStuff {
    private Connection connection;

    public DBStuff() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://194.87.187.238/nikulina", "nikulina", "nikulina");
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

    public void putMessage(String login, String message, Instant time) throws SQLException {
        PreparedStatement insert = connection.prepareStatement("INSERT INTO messages (login, message, time) VALUES (?, ?, ?)");
        insert.setObject(1, login);
        insert.setObject(2, message);
        insert.setObject(3, Timestamp.from(time));
        insert.execute();
    }
}
