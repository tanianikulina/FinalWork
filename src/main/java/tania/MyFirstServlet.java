package tania;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyFirstServlet extends HttpServlet {

    ConcurrentHashMap <Instant, String> messages = new ConcurrentHashMap<>();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setHeader("Content-type", "application/json:charset=UTF-8");
        StringBuilder responseText = new StringBuilder();
        responseText.append("[");
        try {
            List<String> messagesList = new ArrayList<String>(messages.values());
            messagesList.stream().limit(messagesList.size() -1).forEach((message) ->
            responseText.append("\"" + message + "\",")
        );
            if (messagesList.size() > 0)
                responseText.append("\"" + messagesList.get(messagesList.size() - 1) + "\"]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}