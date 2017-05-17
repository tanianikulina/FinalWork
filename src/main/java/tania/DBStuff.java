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

    public String check(String  login, String password) throws SQLException {
        PreparedStatement select = connection.prepareStatement("SELECT * FROM loginsPasswordsNew WHERE login= ? AND password = ?");
        select.setString(1, login);
        select.setString(2, password);
        ResultSet resultSet = select.executeQuery();
        while (resultSet.next())
            return resultSet.getString("token");
        return null;
    }

    public ResultSet getMessages() throws SQLException {
        PreparedStatement select = connection.prepareStatement("SELECT token, message, time FROM messageHistory");
        return select.executeQuery();
    }

    ResultSet getMessages(String timestamp) throws SQLException {
        PreparedStatement select = connection.prepareStatement("SELECT * FROM messageHistory WHERE time > CAST(? AS TIMESTAMP)");
        select.setString(1, timestamp);
        System.out.println(select.toString());
        return select.executeQuery();
    }

    public String getAuthor(UUID token) throws SQLException {
        PreparedStatement select = connection.prepareStatement("SELECT * FROM  loginsPasswordsNew WHERE token = ?");
        select.setObject(1, token);
        ResultSet resultSet = select.executeQuery();
        resultSet.next();
        return resultSet.getString("login");
    }

    public void putMessage(UUID token, String message, Instant time) throws SQLException {
        PreparedStatement insert = connection.prepareStatement("INSERT INTO messageHistory (token, message, time) VALUES (?, ?, ?)");
        insert.setObject(1, token);
        insert.setObject(2, message);
        insert.setObject(3, Timestamp.from(time));
        insert.execute();
    }
}
