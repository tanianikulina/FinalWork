package tania;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyFirstServlet extends HttpServlet {

    ConcurrentHashMap <Instant, String> messages = new ConcurrentHashMap<>();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setStatus(200);
//        response.setHeader("Content-type", "application/json:charset=UTF-8");
        StringBuilder responseText = new StringBuilder();
        responseText.append("[");
        try {
            List<String> messagesList = new ArrayList<String>(messages.values());
            messagesList.stream().limit(messagesList.size() -1).forEach((message) ->
                responseText.append("\"" + message + "\",")
        );
            if (messagesList.size() > 0) {
                messagesList.stream().limit(messagesList.size()-1).forEach((message) -> {
                    responseText.append("\"" + message + "\",");
                });
                responseText.append("\"" + messagesList.get(messagesList.size() - 1) + "\"");
            }
            responseText.append("]");
            System.out.println(responseText.toString());
            response.getWriter().write(responseText.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                System.out.println(message);
                messages.put(Instant.now(), message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.setStatus(200);
    }
}