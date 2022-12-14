package it.unitn.marrocco.flaggame;

import it.unitn.marrocco.flaggame.beans.UserSession;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "main", value = "")
public class Main extends HttpServlet {
    public static synchronized UserSession getUserSession(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        if(username == null) {
            res.sendRedirect("login");
            return null;
        }
        int points = (int) session.getAttribute("points");
        return new UserSession(username, points);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        UserSession user = getUserSession(req, res);
        if (user == null) return;
        if (user.getUsername().equals(Admin.ADMIN_USERNAME)) {
            res.sendRedirect("admin");
            return;
        }

        req.getRequestDispatcher("jsp/main.jsp").forward(req, res);
    }
}