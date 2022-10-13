package it.unitn.marrocco.flaggame;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "admin", value = "/admin")
public class Admin extends HttpServlet {
    public List<UserSession> getLoggedUser() {
        synchronized (getServletContext()) {
            ServletContext context = getServletContext();
            Object loggedAttribute = context.getAttribute("logged");
            @SuppressWarnings("unchecked")
            List<UserSession> logged = (ArrayList<UserSession>) loggedAttribute;
            Collections.sort(logged);
            return logged;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        List<UserSession> logged = getLoggedUser();
        req.setAttribute("logged", logged);
        req.getRequestDispatcher("jsp/admin.jsp").forward(req, res);
    }
}
