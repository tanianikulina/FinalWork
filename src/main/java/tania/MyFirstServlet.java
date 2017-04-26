//сдеелать форму, куда вписывается текст и потом выводится на новой стр

package tania;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class MyFirstServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        String writtentext = (String) request.getParameter("writtentext");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<!DOCTYPE HTML>");
        response.getWriter().println("<html><body><p>" + writtentext + "</p></body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}