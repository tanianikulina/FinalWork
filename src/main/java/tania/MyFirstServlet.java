package tania;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.time.Instant;
import java.util.*;

public class MyFirstServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        StringBuilder responseText = new StringBuilder();

        response.setHeader("Content-type", "application/json:charset=UTF-8");
        responseText.append("[");
        try {
            ResultSet resultSet;
            List<String> messagesList = new ArrayList<>();
            DBStuff db = new DBStuff();
            if (request.getParameter("time") != null) {
                resultSet = db.getMessages(request.getParameter("time"));
            }
            else
                resultSet = db.getMessages();
            while (resultSet.next())
                messagesList.add(resultSet.getString("time") + " " + resultSet.getString("login") + ": " + resultSet.getString("message"));
            db.close();
            if (messagesList.size() > 0) {
                messagesList.stream().limit(messagesList.size() - 1).forEach((message) -> responseText.append("\"").append(message).append("\","));
                responseText.append("\"").append(messagesList.get(messagesList.size() - 1)).append("\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        responseText.append("]");
        response.getWriter().write(responseText.toString());
        response.setStatus(200);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UUID token = UUID.fromString(request.getParameter("token"));
            String message = request.getParameter("message");
            DBStuff db = new DBStuff();
            if (message.length() > 0)
                db.putMessage(db.getAuthor(token), message, Instant.now());
            db.close();
            response.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }
}
