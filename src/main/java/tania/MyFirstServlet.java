package tania;

import javax.servlet.http.*;

public class MyFirstServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(200);
        try {
            response.getWriter().write("Hello, Servlet!");
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }
}