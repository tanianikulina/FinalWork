package tania;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.List;

public class MyFirstServlet extends HttpServlet {

    private DBStuff db = new DBStuff();
    UUID token;

    public MyFirstServlet() throws SQLException, ClassNotFoundException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Content-type", "application/json:charset=UTF-8");

        StringBuilder responseText = new StringBuilder();
        responseText.append("[");
        ResultSet resultSet;

        List<String> messagesList = new ArrayList<>();

        try {
            resultSet = db.getMessages();
            while (resultSet.next())
                messagesList.add(resultSet.getString("time") + " " + db.getAuthor(UUID.fromString(resultSet.getString("token"))) + ": " + resultSet.getString("message"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (messagesList.size() > 0) {
            messagesList.stream().limit(messagesList.size() - 1).forEach((message) -> responseText.append("\"").append(message).append("\","));
            responseText.append("\"").append(messagesList.get(messagesList.size() - 1)).append("\"");
        }
        responseText.append("]");
        response.getWriter().write(responseText.toString());
        response.setStatus(200);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        token = UUID.fromString(request.getParameter("token"));
        String message = request.getParameter("message");
        if (message.length() > 0) {
            try {
                db.putMessage(token, message, Instant.now());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.setStatus(200);
    }
}
