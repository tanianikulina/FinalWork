package tania;

import javax.servlet.*;
import javax.servlet.http.*;
import java.awt.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MyFirstServlet extends HttpServlet {

    ConcurrentHashMap <Instant, String> messages = new ConcurrentHashMap<>();
    DBStuff db;
    UUID token;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setStatus(200);
//        response.setHeader("Content-type", "application/json:charset=UTF-8");

        StringBuilder responseText = new StringBuilder();
        responseText.append("[");
        ResultSet resultSet = null;

        List<String> asList = Arrays.asList(request.getQueryString().split("&"));
        String [] tokens;
        List<String> messagesList = new ArrayList<>();
        tokens = asList.get(1).split("=");
        token = UUID.fromString(tokens[1]);
        try {
            resultSet = db.getMessages();
            while (resultSet.next())
                messagesList.add(db.getAuthor(UUID.fromString(resultSet.getString("token"))));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (messagesList.size() > 0) {
            messagesList.stream().limit(messagesList.size()-1).forEach((message) -> {
                responseText.append("\"" + message + "\",");
            });
            responseText.append("\"" + messagesList.get(messagesList.size() - 1) + "\"");
        }
        responseText.append("]");
        response.getWriter().write(responseText.toString());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "";
        try {
            message = request.getReader().readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
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