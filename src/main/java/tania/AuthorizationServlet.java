package tania;

import javax.servlet.http.*;
import java.util.UUID;

public class AuthorizationServlet extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
            UUID token = UUID.randomUUID();
            DBStuff db = new DBStuff();
            Boolean isAuthorized = db.check(login, password);
            if (isAuthorized) {
                response.setStatus(200);
                response.getWriter().write(String.valueOf(token));
            } else {
                response.setStatus(403);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
