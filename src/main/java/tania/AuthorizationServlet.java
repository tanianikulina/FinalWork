package tania;

import javax.servlet.http.*;

public class AuthorizationServlet extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
            DBStuff db = new DBStuff();
            String result = db.check(login, password);
            if (result != null) {
                response.setStatus(200);
                response.getWriter().write(result);
            } else {
                response.setStatus(403);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
